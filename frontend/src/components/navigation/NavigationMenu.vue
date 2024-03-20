<template>
  <q-scroll-area style="height: calc(100%)">
    <list-comp id="navigation-menu" padding>
      <div v-for="item in menuOptionsWithPathHighlighted" :key="item.label">
        <item-comp
          v-if="item.children?.length <= 0"
          :focused="item.focused"
          :hide="item.hide"
          :icon="item.icon"
          :label="item.label"
          :menuLvl="item.menuLvl"
          :menu-options="item.children"
          :open-menu-on-hover="item.children?.length > 0"
          :to="item.link"
          :key="item.label"
        />
        <expansion-item-comp
          v-else-if="item.children && !item.hide"
          :content-inset-level="0"
          :focused="item.focused"
          :icon="item.icon"
          :label="item.label"
        >
          <item-comp
            v-for="child in item.children"
            :external-u-r-l="child.link"
            :focused="child?.focused"
            :hide="child.hide"
            :icon="child.icon"
            :label="child.label"
            :menuLvl="item.menuLvl"
            :menu-options="child.children"
            :open-menu-on-hover="child.children?.length > 0"
            :to="child.link"
            :key="child.label"
          />
        </expansion-item-comp>
        <item-comp
          v-else-if="!item.children && !item.hide"
          :focused="item.focused"
          :hide="item.hide"
          :icon="item.icon"
          :label="item.label"
          :menuLvl="item.menuLvl"
          :menu-options="item.children"
          :open-menu-on-hover="item.children?.length > 0"
          :to="item.link"
          :key="item.label + '-if'"
        />
      </div>
    </list-comp>
  </q-scroll-area>
</template>

<script>
import { computed, defineComponent, watch } from "vue";
import { useRoute } from "vue-router";
import ItemComp from "../list/ItemComp.vue";
import ListComp from "../list/ListComp.vue";
import ExpansionItemComp from "../list/ExpansionItemComp.vue";

export default defineComponent({
  name: "NavigationMenu",
  components: { ExpansionItemComp, ListComp, ItemComp },
  props: {
    menuOptions: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const route = useRoute();

    const menuOptionsWithPathHighlighted = computed(() => {
      const path = route.fullPath;
      const optionsWithPath = JSON.parse(JSON.stringify(props.menuOptions));
      setFocusForOptionWithPath(optionsWithPath, path);
      return path !== "/not-found" ? optionsWithPath : props.menuOptions;
    });

    function setFocusForOptionWithPath(options, path) {
      let foundOption = false;
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
      menuOptionsWithPathHighlighted,
    };
  },
});
</script>

<style type="text/scss" lang="scss"></style>
