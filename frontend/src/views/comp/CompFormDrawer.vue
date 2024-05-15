<template>
  <div id="compform-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :delete-button-label="deletebuttonlabel"
      :cancel-button-label="cancelbuttonlabel"
      :print-button-label="printbuttonlabel"
      :reset-button-label="resetbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="business"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="showprintbutton"
      side="right"
      :title="title"
      :width="70"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedCompData"
      @confirm-printeded="printedonecompData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="q-pa-lg">
        <comp-form-drawer-primary
          :primary-data="compFormData"
          :comp-sector-list="compSectorList"
          :comp-rate-list="compRateList"
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
          ref="compFormDrawerPrimary"
        />
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
      <div>{{ t('deleteconfirmmsg') }}</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
//Lang
import i18n from 'src/i18n';
// Components
import DrawerComp from 'src/components/drawers/DrawerComp.vue';
import DialogComp from 'src/components/common/DialogComp.vue';
// View Layout
import CompFormDrawerPrimary from 'src/views/comp/CompFormDrawerPrimary.vue';
// Services
import { CompService } from 'src/services/CompService';
import { ReportService } from 'src/services/CompReportService';
// Types
import { CompForm } from 'src/types/CompForm';
import { CompListReport } from 'src/types/CompReport';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import ReportHelper from 'src/utils/helpers/ReportHelper';
import { AnyData } from 'src/types/AnyData';

export default defineComponent({
  name: 'CompFormDrawer',
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
    'update:modelValue',
    'update:drawerData',
    'compform-saved',
    'compform-deleted',
    'compform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageCompany');
    const compFormData = ref<CompForm | undefined>(new CompForm());
    const printdata = ref<CompForm[]>([]);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const printbuttonlabel = ref<string>(i18n.global.t('print'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const showprintbutton = ref<boolean>(false);
    const compFormDrawerPrimary = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);
    const attfile = ref<AnyData | null>(null);

    watch(
      () => props.modelValue,
      (newValue) => {
        openDrawer.value = newValue;
      }
    );
    watch(
      () => openDrawer.value,
      (newValue) => {
        emit('update:modelValue', newValue);
        if (newValue == true) {
          getCompFormData();
        }
      }
    );

    // Reset Drawer
    function resetDrawer() {
      compFormData.value = new CompForm();
      attfile.value = null;

      if (props.compSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        if (store.getters.currentUserHasApplicationPermission('COMP_W')) {
          showconfirmbutton.value = true;
        } else {
          showconfirmbutton.value = false;
        }
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_D');
        showprintbutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_R');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_R');
        showdeletebutton.value = false;
        showprintbutton.value = false;
      }
    }

    function uploadFile(files: AnyData) {
      if (files) {
        attfile.value = files;
      }
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
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      if (compFormData.value) {
        const fileToUpload = attfile.value;
        CompService.saveCompForm(fileToUpload, compFormData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            emit('compform-saved', response);
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
            i18n.global.t('deletesucess')
          );
          emit('compform-deleted', response);
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
      const exportFilename = i18n.global.t('compreport');
      const listReportVO: CompListReport = {
        title: 'aaa',
        sort: '',
        filter: '',
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
      emit('compform-drawer-closed');
    }

    return {
      t: i18n.global.t,
      title,
      compFormData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      compFormDrawerPrimary,
      confirmbuttoncolor,
      confirmbuttonlabel,
      printbuttonlabel,
      deletebuttonlabel,
      resetbuttonlabel,
      cancelbuttonlabel,
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
