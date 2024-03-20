<template>
  <div id="item-comp">
    <q-item
      v-if="!hide"
      v-ripple
      :class="{ focused: focused }"
      clickable
      :replace="replace"
      :to="to"
      @click="goToExternalLink"
      @mouseout="setListMouseOver(false)"
      @mouseover="setListMouseOver(true)"
    >
      <q-item-section v-if="icon" avatar>
        <q-icon :name="icon" size="sm" />
      </q-item-section>
      <q-item-section>{{ label }}</q-item-section>
      <q-item-section v-if="openMenuOnHover" avatar>
        <q-icon name="chevron_right" />
      </q-item-section>
      <menu-comp
        v-if="openMenuOnHover"
        v-model="openMenu"
        anchor="top right"
        self="top left"
        @mouseout="setMenuMouseOver(false)"
        @mouseover="setMenuMouseOver(true)"
      >
        <list-comp style="min-width: 125px">
          <item-comp
            v-for="item in showMenuOptions"
            v-close-popup
            :class="{
              focused: focusOnItem(item),
              'not-focused': !focusOnItem(item),
            }"
            clickable
            :icon="item.icon"
            :label="item.label"
            :key="item.label"
            :open-menu-on-hover="item.openMenuOnHover"
            :replace="item.replace"
            :to="item.to"
          >
          </item-comp>
        </list-comp>
      </menu-comp>
    </q-item>
  </div>
</template>

<script>
import { ref, watch, computed } from "vue";
import ListComp from "./ListComp.vue";
import MenuComp from "./MenuComp.vue";

export default {
  name: "ItemComp",
  components: { ListComp, MenuComp },
  props: {
    focused: {
      type: Boolean,
      default: false,
    },
    hide: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: "",
    },
    label: {
      type: String,
      default: "",
    },
    menuLvl: {
      type: Number,
      default: 0,
    },
    menuOptions: {
      type: Array,
      default: () => [],
    },
    openMenuOnHover: {
      type: Boolean,
      default: false,
    },
    to: {
      type: String,
      default: "",
    },
    replace: {
      type: Boolean,
      default: false,
    },
    externalURL: {
      type: String,
      default: "",
    },
  },
  setup(props) {
    let openMenu = ref(false);
    let menuMouseOver = ref(false);
    let listMouseOver = ref(false);
    let timeout = 0;
    let leftDrawerOpen = ref(true);
    const showMenuOptions = computed(() => {
      if (props.menuOptions) {
        return props.menuOptions.filter((m) => !m.hide);
      } else {
        return props.menuOptions;
      }
    });

    function setMenuOpen() {
      openMenu.value = menuMouseOver.value || listMouseOver.value;
    }

    function setListMouseOver(value) {
      if (value) {
        menuMouseOver.value = false;
      }
      listMouseOver.value = value;
    }

    function setMenuMouseOver(value) {
      menuMouseOver.value = value;
    }

    function focusOnItem(item) {
      return item.to && item.focused;
    }

    function goToExternalLink() {
      if (props.link) {
        window.location.href = props.link;
      }
    }

    watch(
      () => [menuMouseOver.value, listMouseOver.value],
      () => {
        setTimeout(setMenuOpen, 150);
      }
    );

    return {
      showMenuOptions,
      openMenu,
      timeout,
      leftDrawerOpen,
      setMenuMouseOver,
      setListMouseOver,
      focusOnItem,
      goToExternalLink,
    };
  },
};
</script>

<style type="text/scss" lang="scss">
#item-comp {
  // Adds the focus highlight to item components outside of a list
  .focused .q-focus-helper {
    background: currentColor;
    opacity: 0.15;
  }

  // Adds the focus highlight to item components inside of a menu list
  &.focused > a.q-item.q-router-link--exact-active > .q-focus-helper {
    background: currentColor;
    opacity: 0.15;
  }

  // Remove the focus coloring to item components inside of a menu list (used for ?mode=add items)
  &.not-focused > a.q-item.q-router-link--exact-active {
    color: black;
  }
  // Add the focus coloring to item components inside of a menu list (used for ?mode=add items)
  &.not-focused > a.q-item.q-router-link--exact-active:hover {
    background: var(--q-primary-hover);
    color: var(--q-primary);
  }
}
</style>
