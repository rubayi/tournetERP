<template>
  <div id="empform-drawer">
    <drawer-comp
        :openDrawer="openDrawer"
        :drawerWidth="drawerWidth"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content>

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

const openDrawer = ref([]);
const drawerWidth = ref([]);
const dataVal = ref([]);

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
  },
  setup(props, { emit }) {

  },

  mounted() {
    console.log('Received openDrawer:', this.openDrawer);
    console.log('Received drawerWidth:', this.drawerWidth);
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
