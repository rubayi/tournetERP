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
        <q-icon :name="icon" size="xs" />
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
            :external-u-r-l="item.menuUrl"
            as
            string
            :icon="item.menuIcon"
            :label="item.menuKor"
            :open-menu-on-hover="item.openMenuOnHover"
            :replace="item.replace"
            :to="item.menuUrl"
            :key="item.menuKor"
          >
          </item-comp>
        </list-comp>
      </menu-comp>
    </q-item>
  </div>
</template>

<script lang="ts">
import DebounceHelper from 'src/utils/helpers/DebounceHelper';
import URLHelper from 'src/utils/helpers/URLHelper';
import { defineComponent, computed, ref, watch } from 'vue';
import ListComp from 'src/components/list/ListComp.vue';
import MenuComp from 'src/components/list/MenuComp.vue';
import router from 'src/router';

export default defineComponent({
  name: 'ItemComp',
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
      default: '',
    },
    label: {
      type: String,
      default: '',
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
      default: '',
    },
    replace: {
      type: Boolean,
      default: false,
    },
    externalURL: {
      type: String,
      default: '',
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
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        return props.menuOptions.filter((m: any) => !m.hide);
      } else {
        return props.menuOptions;
      }
    });

    function setMenuOpen() {
      openMenu.value = menuMouseOver.value || listMouseOver.value;
    }

    function setListMouseOver(value: boolean) {
      if (value) {
        menuMouseOver.value = false;
      }
      listMouseOver.value = value;
    }

    function setMenuMouseOver(value: boolean) {
      menuMouseOver.value = value;
    }

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    function focusOnItem(item: any) {
      return item.to && item.focused;
    }

    function goToExternalLink() {
      if (props.externalURL) {
        URLHelper.goToURLInNewTab(props.externalURL);
      }
      // Reloads the page again if on an Add page without adding a router entry
      else if (
        props.to &&
        props.to.includes('?mode=') &&
        router.currentRoute.value.fullPath === props.to
      ) {
        router.go(0);
      }
    }

    watch(
      () => [menuMouseOver.value, listMouseOver.value],
      () => {
        DebounceHelper.debounce(setMenuOpen, 150, this);
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
});
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
