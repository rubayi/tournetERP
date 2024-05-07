<template>
  <div id="comp-form-drawer">
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
      @confirm-clicked="saveUpdatedCompData"
      @confirm-printeded="printedonecompData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-12 col-xs-12">
            <comp-form-drawer-primary
              :primary-data="compFormData"
              :comp-sector-list = "compSectorList"
              :comp-rate-list = "compRateList"
              :hotel-rate-list="hotelRateList"
              :comp-optionRate-List="compOptionRateList"
              :coupon-yn-list="couponYnList"
              :comp-group-list="compGroupList"
              :pkg-rate-list="pkgRateList"
              :pkg-only-rate-list="pkgOnlyRateList"
              :rentcar-rate-list="rentcarRateList"
              :restaurant-rate-list="restaurantRateList"
              :honeymoon-only-rate-list="honeymoonOnlyRateList"
              :honeymoon-rate-list="honeymoonRateList"
              :prepaid-how-list="prepaidHowList"
              :upload-file="uploadFile"
              :preview-image="previewImage"
              ref="compFormDrawerPrimary"
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
    @confirm-clicked="deleteCompForm"
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
import CompFormDrawerPrimary from "src/views/comp/CompFormDrawerPrimary.vue";

// Services
import { CompService } from "src/services/CompService";
import { ReportService } from "src/services/CompReportService";

// Types
import { CompForm } from "src/types/CompForm";
import { CompListReportVO } from "src/types/CompReportVO";
import { SelectOption } from "src/types/SelectOption";

// Store
import store from "src/store";

//helper
import { notificationHelper } from "src/utils/helpers/NotificationHelper";
import ReportHelper from "src/utils/helpers/ReportHelper";
import {AnyData} from "src/types/AnyData";

export default defineComponent({
  name: "CompFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    CompFormDrawerPrimary,
  },
  props: {
    compSeq: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    compSectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    hotelRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compOptionRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    couponYnList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compGroupList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    rentcarRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    restaurantRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    prepaidHowList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    "update:modelValue",
    "update:drawerData",
    "compform-saved",
    "compform-deleted",
    "compform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Manage Company";
    const compFormData = ref<CompForm | undefined>(
      new CompForm()
    );

    const printdata = ref<CompForm[]>([]);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const showprintbutton = ref<boolean>(false);

    const compFormDrawerPrimary = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);
    const attfile = ref<AnyData | null>(null);
    const previewImage = ref<string>("");

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
          getCompFormData();
        }
      }
    );

    // Reset Drawer
    function resetDrawer() {
      compFormData.value = new CompForm();
      attfile.value = null;
      previewImage.value = "";
      console.log(previewImage.value);
      if (props.compSeq != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        if (
          store.getters.currentUserHasApplicationPermission("COMP_W")
        ) {
          showconfirmbutton.value = true;
        } else {
          showconfirmbutton.value = false;
        }
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("COMP_D");
        showprintbutton.value =
          store.getters.currentUserHasApplicationPermission("COMP_R");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("COMP_R");
        showdeletebutton.value = false;
        showprintbutton.value = false;
      }
    }

    function uploadFile (files:AnyData) {
      if (files) {
        attfile.value = files;
      }
      //attfile.value = files[0];
    }

    // Loading One Data
    function getCompFormData() {
      resetDrawer();
      if (props.compSeq) {
        loading.value = true;
        CompService.getOneCompForm(props.compSeq)
          .then((response) => {
            compFormData.value = response;

            printdata.value = [];
            printdata.value.push(response);
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedCompData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (compFormData.value) {

        const fileToUpload = attfile.value; // Get the first file
        CompService.saveCompForm(fileToUpload, compFormData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Company Info saved.`
            );
            emit("compform-saved", response);
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
    function deleteCompForm() {
      loading.value = true;
      CompService.deleteCompForm(props.compSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Defendant ${
              compFormData.value ? compFormData.value.compUuid: 0
            } deleted`
          );
          emit("compform-deleted", response);
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
    function printedonecompData() {
      const exportFilename = "HWY Traffic Work Order Report";
      const listReportVO: CompListReportVO = {
        title: "",
        sort: "",
        filter: "",
        data: printdata.value,
      };

      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.getCompOnePdfReport
      );
    }

    function closeDrawer() {
      openDrawer.value = false;
      resetDrawer();
      emit("compform-drawer-closed");
    }
    return {
      title,
      compFormData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      compFormDrawerPrimary,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      showprintbutton,
      deleteAction,
      openDeleteConfirm,
      deleteCompForm,
      saveUpdatedCompData,
      getCompFormData,
      printedonecompData,
      printdata,
      uploadFile,
      previewImage
    };
  },
});
</script>

<style lang="scss">
#compform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
