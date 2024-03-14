<template>
  <q-drawer id="navigation-drawer" overlay :width="240">
    <div class="navigation-menu-container">
      <navigation-menu />
    </div>
    <div>
      <user-info />
    </div>
  </q-drawer>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavigationMenu from "./NavigationMenu.vue";
import UserInfo from "./UserInfo.vue";

export default defineComponent({
  name: "NavigationDrawer",
  components: { NavigationMenu, UserInfo },
});
</script>

<style type="text/scss" lang="scss">
#navigation-drawer {
  overflow: hidden;
  .navigation-menu-container {
    overflow: "hidden";
    height: calc(100% - 70px);
  }
}
</style>
