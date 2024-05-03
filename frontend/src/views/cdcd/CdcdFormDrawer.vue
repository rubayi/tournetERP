<template>
  <div id="cdcd-form-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-cogs"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="60"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedCodeData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <cdcd-form-drawer-content
          v-model="cdcdformData"
          ref="codeFormDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Code"
    @confirm-clicked="deleteCdcdForm"
  >
    <template #htmlContent>
      <div>Are you sure you want to <b>PERMANENTLY DELETE</b> this Data?</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

// Components
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import DialogComp from "src/components/common/DialogComp.vue";

// View Layout
import CdcdFormDrawerContent from "src/views/cdcd/CdcdFormDrawerContent.vue";

// Services
import { CdcdService } from "src/services/CdcdService";

// Types
import { CdcdForm } from "src/types/CdcdForm";
// Store
import store from "src/store";
//helper
import { notificationHelper } from "src/utils/helpers/NotificationHelper";
export default defineComponent({
  name: "CdcdFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    CdcdFormDrawerContent,
  },
  props: {
    codeSeq: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:drawerData",
    "codeform-saved",
    "codeform-deleted",
    "codeform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Manage Code";
    const cdcdformData = ref<CdcdForm | null>(new CdcdForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const codeformDrawerContent = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);

    watch(
      () => props.modelValue,
      (newValue) => {
        openDrawer.value = newValue;
      }
    );
    watch(
      () => openDrawer.value,
      (newValue) => {
        emit("update:modelValue", newValue);
        getCodeformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      cdcdformData.value = new CdcdForm();
      if (props.codeSeq != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        showconfirmbutton.value = store.getters.currentUserHasApplicationPermission("CODE_W");
        showdeletebutton.value = store.getters.currentUserHasApplicationPermission("CODE_D");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value = store.getters.currentUserHasApplicationPermission("CODE_W");
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getCodeformData() {
      resetDrawer();
      if (props.codeSeq != 0) {
        loading.value = true;
        CdcdService.getOneCdcdForm(props.codeSeq)
          .then((response) => {
            cdcdformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedCodeData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (cdcdformData.value) {
        CdcdService.saveCdcdForm(cdcdformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Code  " ${response.codeKr} " saved.`
            );
            if (props.codeSeq != 0) {
              emit("codeform-saved", response);
              cdcdformData.value = new CdcdForm(response);
            } else {
              emit("codeform-saved", response);
              closeDrawer();
            }
          })
          .catch((error) => {
            notificationHelper.createErrorNotification(
              notificationHelper.formatResponseToErrorMessage(error.response)
            );
          })
          .finally(() => {
            notificationHelper.dismissOngoingNotification();
            loading.value = false;
          });
      }
    }

    //Delete Data
    function deleteAction() {
      openDeleteConfirm.value = true;
    }
    function deleteCdcdForm() {
      loading.value = true;
      CdcdService.deleteCdcdForm(props.codeSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Code ${
              cdcdformData.value ? cdcdformData.value.codeKr : ""
            } deleted`
          );
          emit("codeform-deleted", response);
          closeDrawer();
        })
        .catch((error) => {
          notificationHelper.createErrorNotification(
            notificationHelper.formatResponseToErrorMessage(error.response)
          );
        })
        .finally(() => {
          notificationHelper.dismissOngoingNotification();
          loading.value = false;
        });
    }

    function closeDrawer() {
      openDrawer.value = false;
      resetDrawer();
      emit("codeform-drawer-closed");
    }
    return {
      title,
      cdcdformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      codeformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteCdcdForm,
      saveUpdatedCodeData,
    };
  },
});
</script>

<style lang="scss">
#codeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
