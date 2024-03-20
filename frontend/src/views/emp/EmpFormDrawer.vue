<template>
  <div id="empform-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content
          :drawer-width="800"
          :dataVal="edited"
          :on-close-click="onCloseClick"/>

        <emp-form-drawer-menu-auth>

        </emp-form-drawer-menu-auth>
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { empTn  } from "src/store/emp.module";
import { auth  } from "src/store/auth.module";

// Layout
import EmpFormDrawerContent from "src/components/member/EmpFormDrawerContent.vue";
import EmpFormDrawerMenuAuth from "src/components/member/EmpFormDrawerMenuAuth.vue";

export default defineComponent({
  name: "EmpFormDrawer",
  components: {
    DrawerComp,
    EmpFormDrawerContent,
    EmpFormDrawerMenuAuth
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
    handlePageChange: Function,
  },
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const openDrawer = ref(props.openDrawer);
    const updateEdited = {};
    const initEdited = {};

    watch(() => props.dataVal, (newVal) => {
      edited.value = { ...newVal };
    });

    function handleSaveData(data) {
      if (edited.value.empUuid != 0) {
        empTn.actions.updateEmp({ commit: () => {}, state: {} }, edited.value).then(
          (response) => {
            alert(response.data.message);
            emitCloseDrawer();
            resetForm();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      } else {
        auth.actions.register({ commit: () => {}, state: {} },edited.value).then(
          (response) => {
            alert(response.message);
            resetForm();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      }
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
    }

    function resetForm() {
      if (edited.value && edited.value.empUuid != 0) {
        edited.value = updateEdited;
      } else {
        edited.value = { ...initEdited };
      }
    }
    function handleDeleteData(data) {
      emit("delete", edited.value.empUuid);
    }

    return {
      edited,
      handleSaveData,
      handleDeleteData,
    };
  },

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
