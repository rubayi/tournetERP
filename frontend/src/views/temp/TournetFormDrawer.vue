<template>
  <div id="tournet-form-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="far fa-folder-open"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="showprintbutton"
      side="right"
      :title="title"
      :width="80"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedtournetFormData"
      @confirm-printeded="printedonetournetData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-12 col-xs-12">
            <tournet-form-drawer-primary
              v-model:data="tournetformData"
              v-model:suspensedate="tournetformData.suspensedate"
              v-model:workdate="tournetformData.workdate"
              v-model:workstatues="tournetformData.memostatueslist"
              :langtypelistgroup="langtypelistgroup"
              :workdeplistgroup="workdeplistgroup"
              :workstatuslistgroup="workstatuslistgroup"
              ref="tournetformDrawerprimary"
            />
          </div>
        </div>
        <div
          v-if="tournetformData.worknum != null"
          class="row q-col-gutter-md q-mt-xs"
        >
          <div class="col-8 col-md-8 col-xs-12">
            <tournet-form-drawer-orderlog
              v-model:data="tournetformData.workorderlogform"
              v-model:datecompleted="tournetformData.workorderlogform.datecompleted"
              v-model:datecrewassigned="tournetformData.workorderlogform.datecrewassigned"
              v-model:datesentbacktotraffic="tournetformData.workorderlogform.datesentbacktotraffic"
              v-model:posteddate="tournetformData.workorderlogform.posteddate"
              v-model:receiveddate="tournetformData.workorderlogform.receiveddate"
              v-model:workcrewassign="tournetformData.crewassignlist"
              :crewassignedlistgroup="crewassignedlistgroup"
              :postedbylistgroup="postedbylistgroup"
              :receivedbylistgroup="receivedbylistgroup"
              ref="tournetformDrawerorderlog"
            />
          </div>
          <div class="col-4 col-md-4 col-xs-12">
            <tournet-form-drawer-attachment
              v-if="tournetformData.worknnum != 0"
              v-model:attachmentlist="tournetformData.attachmentlist"
              v-model:subworknum="tournetformData.worknum"
              :attachmenttypelistgroup="attachmenttypelistgroup"
              @subAttachment-changed="getTournetFormData"
              ref="tournetformDrawerattachment"
            />
          </div>
        </div>
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Work Order"
    @confirm-clicked="deleteTournetForm"
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
import TournetFormDrawerPrimary from "src/views/temp/TournetFormDrawerPrimary.vue";
import TournetFormDrawerOrderlog from "src/views/temp/TournetFormDrawerOrderlog.vue";
import TournetFormDrawerAttachment from "src/views/temp/TournetFormDrawerAttachment.vue";

// Services
import { TournetService } from "src/services/TournetService";
import { ReportService } from "src/services/ReportService";

// Types
import { TournetForm } from "src/types/TournetForm";
import { TournetWorkorderlogForm } from "src/types/TournetWorkorderlogForm";
import { TournetListReportVO } from "src/types/ReportVO";
import { SelectOption } from "src/types/SelectOption";

// Store
import store from "src/store";

//helper
import { notificationHelper } from "src/utils/helpers/NotificationHelper";
import ReportHelper from "src/utils/helpers/ReportHelper";

export default defineComponent({
  name: "TournetFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    TournetFormDrawerPrimary,
    TournetFormDrawerOrderlog,
    TournetFormDrawerAttachment,
  },
  props: {
    worknum: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    crewassignedlistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    postedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
    receivedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
    workdeplistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    workstatuslistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    attachmenttypelistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    langtypelistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    "update:modelValue",
    "update:drawerData",
    "tournet-form-saved",
    "tournet-form-deleted",
    "tournet-form-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Tournet Work Order";
    const tournetformData = ref<TournetForm | null>(
      new TournetForm()
    );

    const printdata = ref<TournetForm[]>([]);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const showprintbutton = ref<boolean>(false);

    const tournetformDrawerprimary = ref();
    const tournetformDrawerorderlog = ref();
    const tournetformDrawerattachment = ref();
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
        if (newValue == true) {
          getTournetFormData();
        }
      }
    );

    // Reset Drawer
    function resetDrawer() {
      tournetformData.value = new TournetForm();
      if (props.worknum != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "SAVE";
        confirmicon.value = "fas fa-edit";
        if (
          store.getters.currentUserHasApplicationPermission("LOG_E") ||
          store.getters.currentUserHasApplicationPermission("MEMO_A") ||
          store.getters.currentUserHasApplicationPermission("MEMO_E")
        ) {
          showconfirmbutton.value = true;
        } else {
          showconfirmbutton.value = false;
        }
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("LOG_D");
        showprintbutton.value =
          store.getters.currentUserHasApplicationPermission("LOG_R");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("LOG_A");
        showdeletebutton.value = false;
        showprintbutton.value = false;
      }
    }

    // Loading One Data
    function getTournetFormData() {
      resetDrawer();
      if (props.worknum) {
        loading.value = true;
        TournetService.getOneTournetLogForm(props.worknum)
          .then((response) => {
            tournetformData.value = response;
            if (response.workorderlogform == null) {
              tournetformData.value.workorderlogform =
                new TournetWorkorderlogForm();
            }
            printdata.value = [];
            printdata.value.push(response);
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedtournetFormData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (tournetformData.value) {
        TournetService.saveTournetLogForm(tournetformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Traffic Work Order saved.`
            );
            emit("tournet-form-saved", response);
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
    }

    //Delete Data
    function deleteAction() {
      openDeleteConfirm.value = true;
    }
    function deleteTournetForm() {
      loading.value = true;
      TournetService.deleteTournetForm(props.worknum)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Defendant ${
              tournetformData.value ? tournetformData.value.worknum : 0
            } deleted`
          );
          emit("tournet-form-deleted", response);
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

    /* Detail Export PDF */
    function printedonetournetData() {
      const exportFilename = "HWY Traffic Work Order Report";
      const listReportVO: TournetListReportVO = {
        title: "",
        sort: "",
        filter: "",
        data: printdata.value,
      };

      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.getTournetOnePdfReport
      );
    }

    function closeDrawer() {
      openDrawer.value = false;
      resetDrawer();
      emit("tournet-form-drawer-closed");
    }
    return {
      title,
      tournetformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      tournetformDrawerprimary,
      tournetformDrawerorderlog,
      tournetformDrawerattachment,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      showprintbutton,
      deleteAction,
      openDeleteConfirm,
      deleteTournetForm,
      saveUpdatedtournetFormData,
      printedonetournetData,
      getTournetFormData,
      printdata,
    };
  },
});
</script>

<style lang="scss">
#tournetform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
