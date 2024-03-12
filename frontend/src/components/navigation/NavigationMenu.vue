<template>
  <q-scroll-area style="height: calc(100%); border-right: 1px solid #ddd">
    <list-comp id="navigation-menu" padding>
      <div v-for="item in menuOptionsWithPathHighlighted" :key="item.label">
        <q-item-label v-if="item.headerLabel" header>
          {{ item.headerLabel }}
        </q-item-label>
        <expansion-item-comp
          v-else-if="item.expandChildren && !item.hide"
          :content-inset-level="0"
          :focused="item.focused"
          :icon="item.icon"
          :label="item.label"
        >
          <item-comp
            v-for="child in item.children"
            :external-u-r-l="child.externalURL"
            :focused="child?.focused"
            :hide="child.hide"
            :icon="child.icon"
            :label="child.label"
            :menu-options="child.children"
            :open-menu-on-hover="child.children?.length > 0"
            :to="child.to"
            :key="child.label"
          />
        </expansion-item-comp>
        <item-comp
          v-else-if="!item.expandChildren && !item.hide"
          :focused="item.focused"
          :hide="item.hide"
          :icon="item.icon"
          :label="item.label"
          :menu-options="item.children"
          :open-menu-on-hover="item.children?.length > 0"
          :to="item.to"
          :key="item.label"
        />
      </div>
    </list-comp>
  </q-scroll-area>
</template>

<script lang="ts">
import { computed, defineComponent, watch } from "vue";
import { useRoute } from "vue-router";
import store from "@/store";
import ItemComp from "@/components/list/ItemComp.vue";
import ListComp from "@/components/list/ListComp.vue";
import ExpansionItemComp from "@/components/list/ExpansionItemComp.vue";
export default defineComponent({
  name: "NavigationMenu",
  components: { ExpansionItemComp, ListComp, ItemComp },
  setup() {
    const route = useRoute();
    interface MenuOption {
      label: string;
      expandChildren: boolean;
      icon: string;
      children: Array<MenuOption>;
      focused: boolean;
      to: string;
    }

    let menuOptions: any[] = [];
    function setMenuOptions() {
      menuOptions = [
        {
          label: "Work Order",
          icon: "fas fa-inbox",
          to: "/hawaiisampledemo",
          hide: !store.getters.currentUserHasApplicationPermission("LOG_R"),
        },

        {
          label: "Setting",
          expandChildren: true,
          icon: "fas fa-tools",
          hide:
            !store.getters.currentUserHasApplicationPermission("USER_R") &&
            !store.getters.currentUserHasApplicationPermission("OFFICE_R") &&
            !store.getters.currentUserHasApplicationPermission("REP_R") &&
            !store.getters.currentUserHasApplicationPermission("COD_R"),
          children: [
            {
              label: "Manage Users",
              icon: "fas fa-users",
              to: "/members",
              hide: !store.getters.currentUserHasApplicationPermission(
                "USER_R"
              ),
            },
            {
              label: "Manage Offices",
              icon: "far fa-building",
              to: "/office",
              hide: !store.getters.currentUserHasApplicationPermission(
                "OFFICE_R"
              ),
            },
            {
              label: "Manage Reports",
              icon: "fas fa-print",
              to: "/report",
              hide: !store.getters.currentUserHasApplicationPermission("REP_R"),
            },
            {
              label: "Manage Codes",
              icon: "fas fa-cogs",
              to: "/code",
              hide: !store.getters.currentUserHasApplicationPermission("COD_R"),
            },
          ],
        },
      ];
    }

    watch(
      () => store.state.currentUser,
      () => {
        setMenuOptions();
      },
      { deep: true, immediate: true }
    );

    const menuOptionsWithPathHighlighted = computed(() => {
      const path = route.fullPath;
      // TODO: Replace with lodash _.cloneDeep(menuOptions)
      const optionsWithPath = JSON.parse(JSON.stringify(menuOptions));
      setFocusForOptionWithPath(optionsWithPath, path);
      return path !== "/not-found" ? optionsWithPath : menuOptions;
    });

    function setFocusForOptionWithPath(
      options: Array<MenuOption>,
      path: string
    ): boolean {
      let foundOption = false;
      for (let option of options) {
        if (option.to === path) {
          foundOption = true;
          option.focused = true;
          break;
        } else if (
          option?.children?.length > 0 &&
          setFocusForOptionWithPath(option.children, path)
        ) {
          foundOption = true;
          option.focused = true;
        }
      }
      return foundOption;
    }

    return {
      menuOptions,
      menuOptionsWithPathHighlighted,
    };
  },
});
</script>

<style type="text/scss" lang="scss"></style>
