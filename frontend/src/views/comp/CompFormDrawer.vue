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
          :upload-file="uploadFile"
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

import CompService from "src/services/comp.service";

export default defineComponent({
  name: "CompFormDrawer",
  components: {
    DrawerComp,
    CompFormDrawerContent,
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

    watch(() => props.checkedContactUuids, (newVal) => {
      checkedContactUuids.value = newVal;
    }, { deep: true });


    function uploadFile (files) {
      console.log(files);
      attfile.value = files;
      //attfile.value = files[0];
    }

    async function handleSaveData(data) {
      //업체 정보 관련 req 데이터
      const dataChanged = JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      //업체 연락처 관련 req 데이터
      const contactUuids = new Set();

      // Create contactReq object with contactUuids Set and compUuid
      const contactReq = { contactUuids: Array.from(contactUuids), compUuid: edited.value.compUuid };

      const promises = [];

      if (!dataChanged && !attfile.value
          && contactReq.contactUuids.length <=0) {
          alert("변경할 데이터가 없습니다.");
      } else {

        // if (edited.value.compUuid != 0) {

          const fileToUpload = attfile.value; // Get the first file
          // Perform the upload operation with the first file
          // try {
          //   const response = await CompService.updateComp(fileToUpload, edited.value);
          //   console.log("File uploaded successfully:", response);
          // } catch (error) {
          //   console.error("File upload failed:", error);
          // }

          //업체수정
          //if (dataChanged) {

        console.log(edited.value);
          promises.push(
          CompService.updateComp(fileToUpload, edited.value).then(
                (response) => {
                    alert(response.data.message);
                },
                (error) => {
                    console.log("saveComp failed", error);
                }
            )
           );
          //}
        // } else {
        //   //업체 등록
        //   promises.push(
        //   compTn.actions.createComp({
        //       commit: () => {
        //       }, state: {}
        //   }, fileToUpload, edited.value).then(
        //       (response) => {
        //           alert(response.message);
        //       },
        //       (error) => {
        //           console.log("saveComp failed", error);
        //       }
        //   ));
        // }

        if (contactReq.contactUuids.length > 0) {
            // Call updateContact method
          promises.push(
            contactTn.actions.createContact({
                commit: () => {
                }, state: {}
            }, contactReq).then(
                (response) => {
                    alert(response.message);
                },
                (error) => {
                    console.log("saveComp failed", error);
                }
            ));
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
      checkedContactUuids,
      uploadFile
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
