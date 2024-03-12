<template>
  <div id="officeform-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="far fa-building"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="60"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedOfficeData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <office-form-drawer-content
          v-model="officeformData"
          ref="officeformDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Office"
    @confirm-clicked="deleteOfficeForm"
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

// Layout
import OfficeFormDrawerContent from "@/views/office/OfficeFormDrawerContent.vue";

// Services
import { OfficeService } from "@/services/OfficeService";

// Types
import { OfficeForm } from "@/types/OfficeForm";
// Store
import store from "@/store";
//helper
import { notificationHelper } from "@/utils/helpers/NotificationHelper";
export default defineComponent({
  name: "OfficeFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    OfficeFormDrawerContent,
  },
  props: {
    officeid: {
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
    "officeform-saved",
    "officeform-deleted",
    "officeform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Manage Office";
    const officeformData = ref<OfficeForm | null>(new OfficeForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const officeformDrawerContent = ref();
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
        getOfficeformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      officeformData.value = new OfficeForm();
      if (props.officeid != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("OFFICE_E");
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("OFFICE_D");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("OFFICE_A");
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getOfficeformData() {
      resetDrawer();
      if (props.officeid != 0) {
        loading.value = true;
        OfficeService.getOneOfficeForm(props.officeid)
          .then((response) => {
            officeformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedOfficeData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (officeformData.value) {
        OfficeService.saveOfficeForm(officeformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Office  " ${response.officename} " saved.`
            );

            emit("officeform-saved", response);
            officeformData.value = new OfficeForm(response);
            if (props.officeid == 0) {
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
    function deleteOfficeForm() {
      loading.value = true;
      OfficeService.deleteOfficeForm(props.officeid)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Office ${
              officeformData.value ? officeformData.value.officename : ""
            } deleted`
          );
          emit("officeform-deleted", response);
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
      emit("officeform-drawer-closed");
    }
    return {
      title,
      officeformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      officeformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteOfficeForm,
      saveUpdatedOfficeData,
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
