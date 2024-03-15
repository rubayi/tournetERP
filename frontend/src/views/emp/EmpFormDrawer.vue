<template>
  <div id="empform-drawer">
    <drawer-comp
        :open-drawer="openDrawer"
        :drawerWidth="drawerWidth"
        :on-close-click="onCloseClick"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content
            :dataVal="dataVal">

        </emp-form-drawer-content>

      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";

// Components
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import EmpFormDrawerContent from "src/views/emp/EmpFormDrawerContent.vue";

export default defineComponent({
  name: "EmpFormDrawer",
  components: {
    DrawerComp,
    EmpFormDrawerContent
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Array,
    onCloseClick:Function,
  },
  setup(props, { emit }) {

  },
  mounted() {
    console.log('Received dataVal:', this.dataVal);
    console.log('Received openDrawer:', this.openDrawer);
  }
});
</script>

<style lang="scss">
#officeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
