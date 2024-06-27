<template>
  <div id="carform-drawer">
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
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="q-pa-lg">
        <car-form-drawer-content
          :content-data="carFormData"
          :car-type-list="carTypeList"
          :car-brand-list="carBrandList"
          :car-manager-list="carManagerList"
          ref="compFormDrawerContent"
        />
        <car-img-list
          v-if="carFormData.trnCarUuid"
          :trncar-uuid="carFormData.trnCarUuid"
          ref="carImgList"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Work Order"
    @confirm-clicked="deleteCarForm"
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
import CarImgList from 'src/views/car/CarImgList.vue';
// View Layout
import CarFormDrawerContent from 'src/views/car/CarFormDrawerContent.vue';
// Services
import { CarService } from 'src/services/CarService';
import { ReportService } from 'src/services/CompReportService';
// Types
import { CarForm } from 'src/types/CarForm';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import { AnyData } from 'src/types/AnyData';

export default defineComponent({
  name: 'CarFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    CarFormDrawerContent,
    CarImgList,
  },
  props: {
    carSeq: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    carTypeList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carBrandList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carManagerList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    'update:modelValue',
    'update:drawerData',
    'carform-saved',
    'carform-deleted',
    'carform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageCar');
    const carFormData = ref<CarForm>(new CarForm());
    const printdata = ref<CarForm[]>([]);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('content');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const printbuttonlabel = ref<string>(i18n.global.t('print'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const showprintbutton = ref<boolean>(false);
    const compFormDrawerContent = ref();
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
          getCarFormData();
        }
      }
    );

    // Reset Drawer
    function resetDrawer() {
      carFormData.value = new CarForm();

      if (props.carSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        if (store.getters.currentUserHasApplicationPermission('CAR_WU')) {
          showconfirmbutton.value = true;
        } else {
          showconfirmbutton.value = false;
        }
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_D');
        showprintbutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_R');
      } else {
        confirmbuttoncolor.value = 'content';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_R');
        showdeletebutton.value = false;
        showprintbutton.value = false;
      }
    }

    // Loading One Data
    function getCarFormData() {
      resetDrawer();
      if (props.carSeq) {
        loading.value = true;
        CarService.getOneCarForm(props.carSeq)
          .then((response) => {
            carFormData.value = response;

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
      if (carFormData.value) {

        CarService.updateCarForm(carFormData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            emit('carform-saved', response);
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
    function deleteCarForm() {
      loading.value = true;
      CarService.deleteCarForm(props.carSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('carform-deleted', response);
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
      emit('carform-drawer-closed');
    }

    return {
      t: i18n.global.t,
      title,
      carFormData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      compFormDrawerContent,
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
      deleteCarForm,
      saveUpdatedCompData,
      getCarFormData,
    };
  },
});
</script>

<style lang="scss">
#carform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
