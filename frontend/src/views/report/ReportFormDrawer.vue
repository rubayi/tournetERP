<template>
  <div id="reportform-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-print"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="60"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedReportData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <report-form-drawer-content
          v-model="reportformData"
          ref="reportformDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Report"
    @confirm-clicked="deleteReportForm"
  >
    <template #htmlContent>
      <div>Are you sure you want to <b>PERMANENTLY DELETE</b> this Data?</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

// Component
import DrawerComp from "@/components/drawers/DrawerComp.vue";
import DialogComp from "@/components/common/DialogComp.vue";

// Layout
import ReportFormDrawerContent from "@/views/report/ReportFormDrawerContent.vue";

// Services
import { ManageReportService } from "@/services/ManageReportService";

// Types
import { ReportForm } from "@/types/ReportForm";

// Store
import store from "@/store";

//helper
import { notificationHelper } from "@/utils/helpers/NotificationHelper";
export default defineComponent({
  name: "ReportFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    ReportFormDrawerContent,
  },
  props: {
    reportid: {
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
    "reportform-saved",
    "reportform-deleted",
    "reportform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Manage Report";
    const reportformData = ref<ReportForm | null>(new ReportForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const reportformDrawerContent = ref();
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
        getReportformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      reportformData.value = new ReportForm();
      if (props.reportid != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("REP_E");
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("REP_D");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("REP_A");
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getReportformData() {
      resetDrawer();
      if (props.reportid != 0) {
        loading.value = true;
        ManageReportService.getOneReportForm(props.reportid)
          .then((response) => {
            reportformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedReportData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (reportformData.value) {
        ManageReportService.saveReportForm(reportformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Report  " ${response.reportName} " saved.`
            );

            emit("reportform-saved", response);
            reportformData.value = new ReportForm(response);
            if (props.reportid == 0) {
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
    function deleteReportForm() {
      loading.value = true;
      ManageReportService.deleteReportForm(props.reportid)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Report ${
              reportformData.value ? reportformData.value.reportName : ""
            } deleted`
          );
          emit("reportform-deleted", response);
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
      emit("reportform-drawer-closed");
    }
    return {
      title,
      reportformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      reportformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteReportForm,
      saveUpdatedReportData,
    };
  },
});
</script>

<style lang="scss">
#reportform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
