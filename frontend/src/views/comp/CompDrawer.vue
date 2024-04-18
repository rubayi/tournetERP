<template>
  <div id="comp-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="q-pa-md">
        <comp-form-drawer-content
          :data-val="edited"
          :upload-file="uploadFile"
          />
        <div v-if="edited.compUuid" class="q-pa-lg q-gutter-sm" >
          <q-btn
              icon="search"
              label="연락처 등록"
              style="color: darkgreen"
              @click="openAction"
          />
        </div>
          <comp-contact-drawer
              :open-drawer="newOpenDrawer"
              :drawer-width="700"
              :data-val="contactEdited"
              :on-close-click="closeAction"
              @update:openDrawer="newOpenDrawer = $event"
              @drawer-closed="subDrawerClose"
          />

        <comp-contact-list
            :data-val="lcContactList"
            @drawer-closed="subDrawerClose"
        />

      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
// Layout
import CompFormDrawerContent from "src/components/company/CompFormDrawer.vue";
import CompContactDrawer from "src/components/company/CompContactFormDrawer.vue";
import CompContactList from "src/views/comp/CompContactList.vue";

import CompService from "src/services/comp.service";
import { contactInitialData } from "src/views/comp/CompContact";

export default defineComponent({
  name: "CompDrawer",
  components: {
    DrawerComp,
    CompFormDrawerContent,
    CompContactDrawer,
    CompContactList
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    contactList: Array,
    onCloseClick: Function,
    subDrawerClose: Function,
  },
  emits: ["update:dataVal", "update:openDrawer", "update:changeFlag"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const initialData = ref(null);

    const attfile = ref(null);

    const newOpenDrawer = ref(false);


    const contactEdited = ref(contactInitialData);

    const lcContactList = ref(props.contactList);

    watch(() => props.dataVal, (newVal) => {
      edited.value = { ...newVal };
    }, { deep: true });

    watch(() => props.contactList, (newVal) => {
      lcContactList.value = { ...newVal };
    }, { deep: true });


    function uploadFile (files) {
      attfile.value = files;
      //attfile.value = files[0];
    }

    function openAction() {

      contactEdited.value.compUuid = edited.value.compUuid;
      newOpenDrawer.value = !newOpenDrawer.value;
    }

    function closeAction() {
      contactEdited.value = {
          contactUuid: 0,
          compUuid: 0,
          contactType: "",
          repYn: "",
          contactCont: "",
      };
      newOpenDrawer.value = !newOpenDrawer.value;
    }

    const promises = [];

    async function handleSaveData(data) {
      //업체 정보 관련 req 데이터
      const dataChanged = JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      if (!dataChanged && !attfile.value) {
          alert("변경할 데이터가 없습니다.");
      } else {

        const fileToUpload = attfile.value; // Get the first file

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

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("drawer-closed");

    }

    async function handleDeleteData(data) {
      emit("delete", edited.value.compUuid);

      promises.push(
          CompService.deleteComp(edited.value.compUuid).then(
              (response) => {
                alert(response.data.message);
              },
              (error) => {
                console.log("saveComp failed", error);
              }
          )
      );

      try {
        await Promise.all(promises);
        // Emit the event after all promises have resolved
        emitCloseDrawer();
      } catch (error) {
        console.error("One or more promises failed:", error);
        // Handle error if necessary
      }
    }

    onMounted(() => {

    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      uploadFile,
      openAction,
      newOpenDrawer,
      closeAction,
      contactEdited,
      lcContactList
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
