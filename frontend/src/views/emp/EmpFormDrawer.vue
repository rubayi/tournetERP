<template>
  <div id="emp-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
      :iconTitle="'person'"
      :title="edited.empUuid ? '직원 수정' : '직원 등록'"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content :data-val="edited" />

        <emp-form-drawer-menu-auth
          v-if="edited.empUuid"
          :data-val="empMunuAuthList"
          :option-list="munuAuthList"
          :req-list="checkedAuthUuids"
          :menu-max="munuMax"
          @update:checkedAuthUuids="munuAuthList = $event"
        />
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { empTn } from "src/store/emp.module";
import { auth } from "src/store/auth.module";
import { empMenuAuth } from "src/store/empmenuauth.module";
// Layout
import EmpFormDrawerContent from "src/components/emp/EmpFormDrawerContent.vue";
import EmpFormDrawerMenuAuth from "src/components/emp/EmpFormDrawerMenuAuth.vue";

export default defineComponent({
  name: "EmpFormDrawer",
  components: {
    DrawerComp,
    EmpFormDrawerContent,
    EmpFormDrawerMenuAuth,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
  },
  emits: ["update:dataVal", "update:openDrawer", "update:changeFlag"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const updateEdited = {};
    const initEdited = {};

    const munuAuthList = ref([]);
    const munuMax = ref([]);
    const empMunuAuthList = ref([]);

    const checkedAuthUuids = ref([]);

    const initialData = ref(null);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
        initialData.value = { ...newVal };
        getAuthListByEmp();
      },
      { deep: true }
    );

    watch(
      () => props.checkedAuthUuids,
      (newVal) => {
        checkedAuthUuids.value = newVal;
      },
      { deep: true }
    );

    async function handleSaveData(data) {
      //사용자 정보 관련 req 데이터
      const dataChanged =
        JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      //메뉴 권한 관련 req 데이터
      const menuAuthUuids = new Set();
      const deleteMenuAuthUuids = new Set();

      // Iterate through checkedAuthUuids.value and add unique menuAuthUuids to menuAuthUuids Set
      for (let makeReq of checkedAuthUuids.value) {
        if (makeReq.deleteFlag && makeReq.deleteFlag === "Y") {
          deleteMenuAuthUuids.add(makeReq.menuAuthUuid);
        } else {
          menuAuthUuids.add(makeReq.menuAuthUuid);
        }
      }

      // Create menuAuthReq object with menuAuthUuids Set and empUuid
      const menuAuthReq = {
        menuAuthUuids: Array.from(menuAuthUuids),
        empUuid: edited.value.empUuid,
      };
      const deleteMenuAuthReq = {
        menuAuthUuids: Array.from(deleteMenuAuthUuids),
        empUuid: edited.value.empUuid,
      };

      const promises = [];
      if (
        dataChanged ||
        menuAuthReq.menuAuthUuids.length > 0 ||
        deleteMenuAuthReq.menuAuthUuids.length > 0
      ) {
        if (edited.value.empUuid != 0) {
          //사용자 수정
          if (dataChanged) {
            promises.push(
              empTn.actions
                .updateEmp(
                  {
                    commit: () => {},
                    state: {},
                  },
                  edited.value
                )
                .then(
                  (response) => {
                    alert(response.data.message);
                  },
                  (error) => {
                    console.log("saveEmp failed", error);
                  }
                )
            );
          }
        } else {
          //사용자 등록
          promises.push(
            auth.actions
              .register(
                {
                  commit: () => {},
                  state: {},
                },
                edited.value
              )
              .then(
                (response) => {
                  alert(response.message);
                },
                (error) => {
                  console.log("saveEmp failed", error);
                }
              )
          );
        }

        if (menuAuthReq.menuAuthUuids.length > 0) {
          // Call updateEmpAuth method
          promises.push(
            empMenuAuth.actions
              .updateEmpAuth(
                {
                  commit: () => {},
                  state: {},
                },
                menuAuthReq
              )
              .then(
                (response) => {
                  alert(response.data.message);
                },
                (error) => {
                  console.log("saveEmp failed", error);
                }
              )
          );
        }

        if (deleteMenuAuthReq.menuAuthUuids.length > 0) {
          promises.push(
            empMenuAuth.actions
              .deleteEmpAuth(
                {
                  commit: () => {},
                  state: {},
                },
                deleteMenuAuthReq
              )
              .then(
                (response) => {
                  alert(response.data.message);
                },
                (error) => {
                  console.log("saveEmp failed", error);
                }
              )
          );
        }
        //Action after update,delete
        try {
          await Promise.all(promises);
          // Emit the event after all promises have resolved
          emitCloseDrawer();
        } catch (error) {
          console.error("One or more promises failed:", error);
          // Handle error if necessary
        }
      } else {
        alert("변경할 데이터가 없습니다.");
      }
    }
    function getAuthList() {
      empMenuAuth.actions.searchAuthList({ commit: () => {}, state: {} }).then(
        (response) => {
          munuAuthList.value = response.menuAuths;
          munuMax.value = response.maxNumber;
        },
        (error) => {
          console.log("saveEmp failed", error);
        }
      );
    }

    function getAuthListByEmp() {
      empMenuAuth.actions
        .searchAuthListByEmpId({ commit: () => {}, state: {} }, edited.value)
        .then(
          (response) => {
            empMunuAuthList.value = response.menuAuths;

            for (let menuAuth of munuAuthList.value) {
              // Check if the menuAuth's menuAuthUuid exists in empMunuAuthList
              const found = empMunuAuthList.value.some(
                (empMenuAuth) =>
                  empMenuAuth.menuAuthUuid === menuAuth.menuAuthUuid
              );

              // If found, set authYn to true, otherwise set it to false
              menuAuth.authYn = found;
            }
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("dataSaved");
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
    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      empMunuAuthList,
      munuAuthList,
      munuMax,
      checkedAuthUuids,
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
