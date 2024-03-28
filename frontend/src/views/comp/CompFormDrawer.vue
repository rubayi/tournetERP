<template>
  <div id="comp-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <comp-form-drawer-content
          :data-val="edited"
          />

<!--        <comp-form-drawer-contact-->
<!--          v-if="edited.compUuid"-->
<!--          :data-val="contactList"-->
<!--          />-->
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { compTn  } from "src/store/comp.module";
import { contactTn  } from "src/store/contact.module";
// Layout
import CompFormDrawerContent from "src/components/company/CompFormDrawerContent.vue";
// import CompFormDrawerContact from "src/components/company/CompFormDrawerContact.vue";

export default defineComponent({
  name: "CompFormDrawer",
  components: {
    DrawerComp,
    CompFormDrawerContent,
    // CompFormDrawerContact
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

    const contactList = ref([]);

    const checkedContactUuids = ref([]);

    const initialData = ref(null);

    watch(() => props.dataVal, (newVal) => {
      edited.value = { ...newVal };
      initialData.value = { ...newVal };
      //getContactList();
    }, { deep: true });

    watch(() => props.checkedContactUuids, (newVal) => {
      checkedContactUuids.value = newVal;
    }, { deep: true });

    function handleSaveData(data) {
      //업체 정보 관련 req 데이터
      const dataChanged = JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      //업체 연락처 관련 req 데이터
      const contactUuids = new Set();

      // Create contactReq object with contactUuids Set and compUuid
      const contactReq = { contactUuids: Array.from(contactUuids), compUuid: edited.value.compUuid };

      if (!dataChanged
          && contactReq.contactUuids.length <=0) {
          alert("변경할 데이터가 없습니다.");
      } else {

          if (edited.value.compUuid != 0) {
            //업체수정
            if (dataChanged) {
              compTn.actions.createComp({
                  commit: () => {
                  }, state: {}
              }, edited.value).then(
                  (response) => {
                      alert(response.data.message);
                  },
                  (error) => {
                      console.log("saveComp failed", error);
                  }
              );
            }
          } else {
            //업체 등록
            compTn.actions.register({
                commit: () => {
                }, state: {}
            }, edited.value).then(
                (response) => {
                    alert(response.message);
                },
                (error) => {
                    console.log("saveComp failed", error);
                }
            );
          }

          if (contactReq.contactUuids.length > 0) {
              // Call updateContact method
              contactTn.actions.createContact({
                  commit: () => {
                  }, state: {}
              }, contactReq).then(
                  (response) => {
                      alert(response.data.message);
                  },
                  (error) => {
                      console.log("saveComp failed", error);
                  }
              );
          }

        //Close Drawer
        //emitCloseDrawer();
      }
    }
    function getContactList() {

      contactTn.actions.searchContactList({ commit: () => {}, state: {} }, edited.value)
        .then(
        (response) => {
          contactList.value = response.contacts;
        },
        (error) => {
          console.log("saveComp failed", error);
        }
      );
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("drawer-closed");
    }

    function resetForm() {
      if (edited.value && edited.value.compUuid != 0) {
        edited.value = updateEdited;
      } else {
        edited.value = { ...initEdited };
      }
    }
    function handleDeleteData(data) {
      emit("delete", edited.value.compUuid);
    }

    onMounted(() => {
      //getContactList();

    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      contactList,
      checkedContactUuids
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
