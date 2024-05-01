<template>
  <q-scroll-area style="height: calc(100%); border-right: 1px solid #ddd">
    <list-comp id="navigation-menu" padding>
      <div v-for="item in menuOptionsWithPathHighlighted" :key="item.menuUuid">
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
            :external-u-r-l="child.exUrl"
            :focused="child?.focused"
            :hide="child.hide"
            :icon="child.icon"
            :label="child.label"
            :menu-options="child.children"
            :open-menu-on-hover="child.children?.length > 0"
            :to="child.link"
            :key="child.menuUuid"
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
          :to="item.link"
          :key="item.menuUuid"
        />
      </div>
    </list-comp>
  </q-scroll-area>
</template>

<script lang="ts">
import { ref, Ref, computed, defineComponent, watch } from "vue";
import { useRoute } from "vue-router";
import store from "src/store";
import ItemComp from "src/components/list/ItemComp.vue";
import ListComp from "src/components/list/ListComp.vue";
import ExpansionItemComp from "src/components/list/ExpansionItemComp.vue";
import {MenuService} from "src/services/MenuService";
import {MenuForm} from "src/types/MenuForm";
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
      link: string;
      upperMenuUuid: number;
      menuUuid: number;
      caption: string;
    }

    const menuOptions: Ref<MenuForm[]> = ref([]);

    function buildComMenuTree(menuItems: MenuForm[], parentUuid: number): any[] {
      const filteredComMenus = menuItems.filter(
        (menu) => menu.upperMenuUuid === parentUuid
      );
      return filteredComMenus.map((menu) => ({
        menuUuid: menu.menuUuid,
        expandChildren: true,
        upperMenuUuid: menu.upperMenuUuid,
        icon: menu.menuIcon ? menu.menuIcon : "label",
        label: menu.menuKor + (menu.menuUrl ? ` :${menu.menuUrl}` : ""),
        link: menu.menuUrl,
        caption: menu.menuDesc,
        children: buildComMenuTree(menuItems, menu.menuUuid),
      }));
    };

    function setMenuOptions() {

      let menuReq = {
        role: "ROLE_USER",
      };
      if(store.getters.currentUserHasApplicationRole("ROLE_ADMIN")){

        menuReq.role = "ROLE_ADMIN";
      }
      MenuService.getAll(menuReq).then((response: MenuForm[]) => {
        let rawMenuOption:  MenuForm[];
        if (response) {
          rawMenuOption = response;
          menuOptions.value = buildComMenuTree(rawMenuOption, 0);

        }
        // if (gridOptions.value.columnApi) {
        //   gridOptions.value.columnApi.applyColumnState({
        //     state: CodeFormTableConfig.defaultSortModel,
        //   });
        // }
      });

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
      const optionsWithPath = menuOptions.value;

      //setFocusForOptionWithPath(optionsWithPath, path);
      return path !== "/not-found" ? optionsWithPath : menuOptions;
    });

    function setFocusForOptionWithPath(
      options: Array<MenuOption>,
      path: string
    ): boolean {
      let foundOption = false;
      console.log(options);
      for (let option of options) {
        if (option.link === path) {
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
