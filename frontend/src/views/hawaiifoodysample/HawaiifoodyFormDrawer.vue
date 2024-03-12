<template>
  <div id="hawaiifoodyform-drawer">
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
      @confirm-clicked="saveUpdatedhawaiifoodyFormData"
      @confirm-printeded="printedonehawaiifoodyData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-12 col-xs-12">
            <hawaiifoody-form-drawer-primary
              v-model:data="hawaiifoodyformData"
              v-model:suspensedate="hawaiifoodyformData.suspensedate"
              v-model:workdate="hawaiifoodyformData.workdate"
              v-model:workstatues="hawaiifoodyformData.memostatueslist"
              :langtypelistgroup="langtypelistgroup"
              :workdeplistgroup="workdeplistgroup"
              :workstatuslistgroup="workstatuslistgroup"
              ref="hawaiifoodyformDrawerprimary"
            />
          </div>
        </div>
        <div
          v-if="hawaiifoodyformData.worknum != null"
          class="row q-col-gutter-md q-mt-xs"
        >
          <div class="col-8 col-md-8 col-xs-12">
            <hawaiifoody-form-drawer-orderlog
              v-model:data="hawaiifoodyformData.workorderlogform"
              v-model:datecompleted="hawaiifoodyformData.workorderlogform.datecompleted"
              v-model:datecrewassigned="hawaiifoodyformData.workorderlogform.datecrewassigned"
              v-model:datesentbacktotraffic="hawaiifoodyformData.workorderlogform.datesentbacktotraffic"
              v-model:posteddate="hawaiifoodyformData.workorderlogform.posteddate"
              v-model:receiveddate="hawaiifoodyformData.workorderlogform.receiveddate"
              v-model:workcrewassign="hawaiifoodyformData.crewassignlist"
              :crewassignedlistgroup="crewassignedlistgroup"
              :postedbylistgroup="postedbylistgroup"
              :receivedbylistgroup="receivedbylistgroup"
              ref="hawaiifoodyformDrawerorderlog"
            />
          </div>
          <div class="col-4 col-md-4 col-xs-12">
            <hawaiifoody-form-drawer-attachment
              v-if="hawaiifoodyformData.worknnum != 0"
              v-model:attachmentlist="hawaiifoodyformData.attachmentlist"
              v-model:subworknum="hawaiifoodyformData.worknum"
              :attachmenttypelistgroup="attachmenttypelistgroup"
              @subAttachment-changed="getHawaiifoodyFormData"
              ref="hawaiifoodyformDrawerattachment"
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
    @confirm-clicked="deleteHawaiifoodyForm"
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
import HawaiifoodyFormDrawerPrimary from "@/views/hawaiifoodysample/HawaiifoodyFormDrawerPrimary.vue";
import HawaiifoodyFormDrawerOrderlog from "@/views/hawaiifoodysample/HawaiifoodyFormDrawerOrderlog.vue";
import HawaiifoodyFormDrawerAttachment from "@/views/hawaiifoodysample/HawaiifoodyFormDrawerAttachment.vue";

// Services
import { HawaiifoodyService } from "@/services/HawaiifoodyService";
import { ReportService } from "@/services/ReportService";

// Types
import { HawaiifoodyForm } from "@/types/HawaiifoodyForm";
import { HawaiifoodyWorkorderlogForm } from "@/types/HawaiifoodyWorkorderlogForm";
import { HawaiifoodyListReportVO } from "@/types/ReportVO";
import { SelectOption } from "@/types/SelectOption";

// Store
import store from "@/store";

//helper
import { notificationHelper } from "@/utils/helpers/NotificationHelper";
import ReportHelper from "@/utils/helpers/ReportHelper";

export default defineComponent({
  name: "HawaiifoodyFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    HawaiifoodyFormDrawerPrimary,
    HawaiifoodyFormDrawerOrderlog,
    HawaiifoodyFormDrawerAttachment,
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
    "hawaiifoodyform-saved",
    "hawaiifoodyform-deleted",
    "hawaiifoodyform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "HawaiiFoody Work Order";
    const hawaiifoodyformData = ref<HawaiifoodyForm | null>(
      new HawaiifoodyForm()
    );

    const printdata = ref<HawaiifoodyForm[]>([]);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const showprintbutton = ref<boolean>(false);

    const hawaiifoodyformDrawerprimary = ref();
    const hawaiifoodyformDrawerorderlog = ref();
    const hawaiifoodyformDrawerattachment = ref();
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
          getHawaiifoodyFormData();
        }
      }
    );

    // Reset Drawer
    function resetDrawer() {
      hawaiifoodyformData.value = new HawaiifoodyForm();
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
    function getHawaiifoodyFormData() {
      resetDrawer();
      if (props.worknum) {
        loading.value = true;
        HawaiifoodyService.getOneHawaiifoodyLogForm(props.worknum)
          .then((response) => {
            hawaiifoodyformData.value = response;
            if (response.workorderlogform == null) {
              hawaiifoodyformData.value.workorderlogform =
                new HawaiifoodyWorkorderlogForm();
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
    function saveUpdatedhawaiifoodyFormData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (hawaiifoodyformData.value) {
        HawaiifoodyService.saveHawaiifoodyLogForm(hawaiifoodyformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Traffic Work Order saved.`
            );
            emit("hawaiifoodyform-saved", response);
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
    function deleteHawaiifoodyForm() {
      loading.value = true;
      HawaiifoodyService.deleteHawaiifoodyForm(props.worknum)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Defendant ${
              hawaiifoodyformData.value ? hawaiifoodyformData.value.worknum : 0
            } deleted`
          );
          emit("hawaiifoodyform-deleted", response);
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
    function printedonehawaiifoodyData() {
      const exportFilename = "HWY Traffic Work Order Report";
      const listReportVO: HawaiifoodyListReportVO = {
        title: "",
        sort: "",
        filter: "",
        data: printdata.value,
      };

      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.gethawaiifoodyOnePdfReport
      );
    }

    function closeDrawer() {
      openDrawer.value = false;
      resetDrawer();
      emit("hawaiifoodyform-drawer-closed");
    }
    return {
      title,
      hawaiifoodyformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      hawaiifoodyformDrawerprimary,
      hawaiifoodyformDrawerorderlog,
      hawaiifoodyformDrawerattachment,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      showprintbutton,
      deleteAction,
      openDeleteConfirm,
      deleteHawaiifoodyForm,
      saveUpdatedhawaiifoodyFormData,
      printedonehawaiifoodyData,
      getHawaiifoodyFormData,
      printdata,
    };
  },
});
</script>

<style lang="scss">
#hawaiifoodyform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
