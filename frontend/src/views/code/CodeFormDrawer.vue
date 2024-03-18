<template>
  <div id="codeform-drawer">
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
        <code-form-drawer-content
          v-model="codeformData"
          ref="codeformDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Code"
    @confirm-clicked="deleteCodeForm"
  >
    <template #htmlContent>
      <div>Are you sure you want to <b>PERMANENTLY DELETE</b> this Data?</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

// Components
import DrawerComp from "@/components/drawers/DrawerComp.vue";
import DialogComp from "@/components/common/DialogComp.vue";

// View Layout
import CodeFormDrawerContent from "@/views/code/CodeFormDrawerContent.vue";

// Services
import { CodeService } from "@/services/CodeService";

// Types
import { CodeForm } from "@/types/CodeForm";
// Store
import store from "@/store";
//helper
import { notificationHelper } from "@/utils/helpers/NotificationHelper";
export default defineComponent({
  name: "CodeFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    CodeFormDrawerContent,
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
    const codeformData = ref<CodeForm | null>(new CodeForm());
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
      codeformData.value = new CodeForm();
      if (props.codeSeq != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("COD_E");
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("COD_D");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("COD_A");
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getCodeformData() {
      resetDrawer();
      if (props.codeSeq != 0) {
        loading.value = true;
        CodeService.getOneCodeForm(props.codeSeq)
          .then((response) => {
            codeformData.value = response;
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
      if (codeformData.value) {
        CodeService.saveCodeForm(codeformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Code  " ${response.codename} " saved.`
            );
            if (props.codeSeq != 0) {
              emit("codeform-saved", response);
              codeformData.value = new CodeForm(response);
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
    function deleteCodeForm() {
      loading.value = true;
      CodeService.deleteCodeForm(props.codeSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Code ${
              codeformData.value ? codeformData.value.codename : ""
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
      codeformData,
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
      deleteCodeForm,
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