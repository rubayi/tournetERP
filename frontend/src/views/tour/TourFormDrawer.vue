<template>
  <div id="tour-form-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :delete-button-label="deletebuttonlabel"
      :cancel-button-label="cancelbuttonlabel"
      :reset-button-label="resetbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-cogs"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="40"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedCodeData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tour-form-drawer-content
          v-model="tourformData"
          :tour-area-list = "tourAreaList"
          :tour-area-sub-list = "tourAreaSubList"
          :sector-list = "sectorList"
          :company-list = "companyList"
          :prepaid-how-list = "prepaidHowList"
          ref="tourFormDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteTourForm"
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
import TourFormDrawerContent from 'src/views/tour/TourFormDrawerContent.vue';
// Services
import { TourService } from 'src/services/TourService';
// Types
import { TourForm } from 'src/types/TourForm';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import {SelectOption} from "src/types/SelectOption";

export default defineComponent({
  name: 'TourFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    TourFormDrawerContent,
  },
  props: {
    tourSeq: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    tourCategory: {
      type: Number,
      default: 0,
    },
    tourAreaList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    tourAreaSubList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    sectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    companyList: {
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
    'tourform-saved',
    'tourform-deleted',
    'tourform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageTour');
    const tourformData = ref<TourForm>(new TourForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const deleteTitle = ref<string>(i18n.global.t('deleteTitle'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const tourformDrawerContent = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);
    const lcTourCategory = ref(props.tourCategory);

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
        getTourFormData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      tourformData.value = new TourForm();
      tourformData.value.tourCategory = lcTourCategory.value;

      if (props.tourSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getTourFormData() {
      resetDrawer();
      if (props.tourSeq != 0) {
        loading.value = true;
        TourService.getOneTourForm(props.tourSeq)
          .then((response) => {
            tourformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedCodeData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      tourformData.value.tourCategory = lcTourCategory.value;
      if (tourformData.value) {
        TourService.saveTourForm(tourformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            if (props.tourSeq != 0) {
              emit('tourform-saved', response);
              tourformData.value = new TourForm(response);
            } else {
              emit('tourform-saved', response);
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
    function deleteTourForm() {
      loading.value = true;
      TourService.deleteTourForm(props.tourSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('tourform-deleted', response);
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
      emit('tourform-drawer-closed');
    }
    return {
      t: i18n.global.t,
      title,
      tourformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      tourformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      deletebuttonlabel,
      deleteTitle,
      resetbuttonlabel,
      cancelbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteTourForm,
      saveUpdatedCodeData,
    };
  },
});
</script>

<style lang="scss">
#tourform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
