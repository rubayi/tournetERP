<template>
  <div id="emp-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content
          :data-val="edited"/>

        <emp-form-drawer-menu-auth
          :data-val="empMunuAuthList"
          :option-list="munuAuthList"/>
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { empTn  } from "src/store/emp.module";
import { auth  } from "src/store/auth.module";
import { empMenuAuth  } from "src/store/empmenuauth.module";
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
  emits: ["update:dataVal", "update:openDrawer"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const updateEdited = {};
    const initEdited = {};

    const munuAuthList = ref([]);
    const empMunuAuthList = ref([]);

    watch(() => props.dataVal, (newVal) => {
      edited.value = { ...newVal };
      console.log(edited.value);
    }, { deep: true });


    function handleSaveData(data) {

      if (edited.value.empUuid != 0) {
        empTn.actions.updateEmp({ commit: () => {}, state: {} }, edited.value).then(
          (response) => {
            alert(response.data.message);
            emitCloseDrawer();

          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      } else {
        auth.actions.register({ commit: () => {}, state: {} },edited.value).then(
          (response) => {
            alert(response.message);
            emitCloseDrawer();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      }
    }

    function getAuthList() {
      empMenuAuth.actions.searchAuthList({ commit: () => {}, state: {} }).then(
        (response) => {
          munuAuthList.value = response.menuAuths;
        },
        (error) => {
          console.log("saveEmp failed", error);
        }
      );
    }

    function getAuthListByEmp() {
      empMenuAuth.actions.searchAuthListByEmpId({ commit: () => {}, state: {} }, edited.value).then(
        (response) => {

          empMunuAuthList.value = response.data.menuAuths;

        },
        (error) => {
          console.log("saveEmp failed", error);
        }
      );
    }

    function emitCloseDrawer() {
      console.log("close");
      emit("update:openDrawer", false);
      emit("drawer-closed");
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

    onMounted(() => {
      getAuthList();
      getAuthListByEmp();
    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      empMunuAuthList,
      munuAuthList
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
