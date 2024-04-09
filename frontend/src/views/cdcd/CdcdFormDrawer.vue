<template>
  <div id="cdcd-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <cdcd-form-drawer-content
          :data-val="edited"
          :upload-file="uploadFile"
          />

<!--        <cdcd-form-drawer-contact-->
<!--          v-if="edited.creditCardUuid"-->
<!--          :data-val="contactList"-->
<!--          />-->
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { cdcdmngTn  } from "src/store/cdcdmng.module";
// Layout
import CdcdFormDrawerContent from "src/components/cdcd/CdcdFormDrawerContent.vue";

import CdcdMngService from "src/services/cdcdmng.service";

export default defineComponent({
  name: "CdcdFormDrawer",
  components: {
    DrawerComp,
    CdcdFormDrawerContent,
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

    const attfile = ref(null);

    const previewImage = ref(null);

    watch(() => props.dataVal, (newVal) => {
      edited.value = { ...newVal };
      initialData.value = { ...newVal };
      //getContactList();
    }, { deep: true });

    async function handleSaveData(data) {
      //업체 정보 관련 req 데이터
      const dataChanged = JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      //업체 연락처 관련 req 데이터
      const contactUuids = new Set();

      // Create contactReq object with contactUuids Set and creditCardUuid
      const contactReq = { contactUuids: Array.from(contactUuids), creditCardUuid: edited.value.creditCardUuid };

      const promises = [];

      if (!dataChanged <=0) {
          alert("변경할 데이터가 없습니다.");
      } else {

        // if (edited.value.creditCardUuid != 0) {

          //업체수정
          //if (dataChanged) {

          promises.push(
          CdcdMngService.updateCdcdMng(edited.value).then(
                (response) => {
                    alert(response.data.message);
                },
                (error) => {
                    console.log("saveCdcd failed", error);
                }
            )
           );
          //}
        // } else {
        //   //업체 등록
        //   promises.push(
        //   cdcdmngTn.actions.createCdcd({
        //       commit: () => {
        //       }, state: {}
        //   }, fileToUpload, edited.value).then(
        //       (response) => {
        //           alert(response.message);
        //       },
        //       (error) => {
        //           console.log("saveCdcd failed", error);
        //       }
        //   ));
        // }


        //Action after update,delete
        try {
          await Promise.all(promises);
          // Emit the event after all promises have resolved
          emitCloseDrawer();
        } catch (error) {
          console.error("One or more promises failed:", error);
          // Handle error if necessary
        }
      }
    }
    function getContactList() {

      contactTn.actions.searchContactList({ commit: () => {}, state: {} }, edited.value)
        .then(
        (response) => {
          contactList.value = response.contacts;
        },
        (error) => {
          console.log("saveCdcd failed", error);
        }
      );
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("drawer-closed");
    }

    function resetForm() {
      if (edited.value && edited.value.creditCardUuid != 0) {
        edited.value = updateEdited;
      } else {
        edited.value = { ...initEdited };
      }
    }
    function handleDeleteData(data) {
      emit("delete", edited.value.creditCardUuid);
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
