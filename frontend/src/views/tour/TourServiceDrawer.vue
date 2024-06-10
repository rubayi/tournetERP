<template>
  <div id="tour-service-drawer">
    <drawer-comp
      v-model="openTourServiceDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :delete-button-label="deletebuttonlabel"
      :cancel-button-label="cancelbuttonlabel"
      :reset-button-label="resetbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="person"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="30"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedTrServiceData"
      @delete-clicked="openDeleteConfirm = true"
      ref="compDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tour-service-drawer-content
          v-model="infoForm"
          :tourUuid="tourUuid"
          ref="tourServiceDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteContactForm"
  >
    <template #htmlContent>
      <div>{{ t('deleteconfirmmsg') }}</div>
    </template>
  </dialog-comp>
</template>
<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
// Components
import DrawerComp from 'src/components/drawers/DrawerComp.vue';
import DialogComp from 'src/components/common/DialogComp.vue';
// View Layout
import TourServiceDrawerContent from 'src/views/tour/TourServiceDrawerContent.vue';
// Service
import { TourServiceService } from 'src/services/TourServiceService';
// Type
import { TourServiceForm } from 'src/types/TourServiceForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'TourServiceDrawer',
  components: {
    DrawerComp,
    DialogComp,
    TourServiceDrawerContent,
  },
  props: {
    compSeq: {
      type: Number,
      default: 0,
    },
    tourServiceDrawer: {
      type: Boolean,
      default: false,
    },
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:tourServiceDrawer',
    'tourserviceform-deleted',
    'tourserviceform-saved',
    'tourserviceform-Drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('infos');
    const infoForm = ref<TourServiceForm>(new TourServiceForm());
    const loading = ref<boolean>(false);
    const openTourServiceDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const deleteTitle = ref<string>(i18n.global.t('deleteTitle'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);

    watch(
      () => props.tourServiceDrawer,
      (newValue) => {
        openTourServiceDrawer.value = newValue;
      }
    );
    watch(
      () => openTourServiceDrawer.value,
      (newValue) => {
        emit('update:tourServiceDrawer', newValue);
        getTourServiceFormData();
      }
    );

    function resetDrawer() {
      infoForm.value = new TourServiceForm();
      if (props.compSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_WU');
        showdeletebutton.value = false;
      }
    }

    function getTourServiceFormData() {
      resetDrawer();
      if (props.compSeq != 0) {
        loading.value = true;
        TourServiceService.getTourServiceForm(props.compSeq)
          .then((response) => {
            infoForm.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    function saveUpdatedTrServiceData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      if (infoForm.value) {
        infoForm.value.tourUuid = props.tourUuid;
        console.log(infoForm.value);
        TourServiceService.saveTourServiceForm(infoForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('tourserviceform-saved', response);
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

    function deleteAction() {
      openDeleteConfirm.value = true;
    }

    function deleteContactForm() {
      loading.value = true;
      TourServiceService.deleteTourServiceForm(props.compSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('tourserviceform-deleted', response);
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
      openTourServiceDrawer.value = false;
      resetDrawer();
      emit('tourserviceform-Drawer-closed');

    }

    return {
      t: i18n.global.t,
      title,
      infoForm,
      loading,
      openTourServiceDrawer,
      confirmbuttoncolor,
      confirmbuttonlabel,
      deletebuttonlabel,
      deleteTitle,
      resetbuttonlabel,
      cancelbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      drawerComp,
      openDeleteConfirm,
      resetDrawer,
      getTourServiceFormData,
      saveUpdatedTrServiceData,
      deleteAction,
      deleteContactForm,
      closeDrawer,
    };
  },
});
</script>
