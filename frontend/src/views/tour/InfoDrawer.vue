<template>
  <div id="tour-info-drawer">
    <drawer-comp
      v-model="openInfoDrawer"
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
      @confirm-clicked="saveUpdatedContactData"
      @delete-clicked="openDeleteConfirm = true"
      ref="infoDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <info-drawer-content
          v-model="infoForm"
          :tourUuid="tourUuid"
          ref="infoDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteTourInfoForm"
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
import InfoDrawerContent from 'src/views/tour/InfoDrawerContent.vue';
// Service
import { InfoService } from 'src/services/InfoService';
// Type
import { InfoForm } from 'src/types/InfoForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'InfoDrawer',
  components: {
    DrawerComp,
    DialogComp,
    InfoDrawerContent,
  },
  props: {
    infoSeq: {
      type: Number,
      default: 0,
    },
    infoDrawer: {
      type: Boolean,
      default: false,
    },
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:infoDrawer',
    'infoform-deleted',
    'infoform-saved',
    'infoform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('infos');
    const infoForm = ref<InfoForm>(new InfoForm());
    const loading = ref<boolean>(false);
    const openInfoDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const deleteTitle = ref<string>(i18n.global.t('deleteTitle'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const empformDrawerContent = ref();
    const empformDrawerMenuAuth = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);

    watch(
      () => props.infoDrawer,
      (newValue) => {
        openInfoDrawer.value = newValue;
      }
    );
    watch(
      () => openInfoDrawer.value,
      (newValue) => {
        emit('update:infoDrawer', newValue);
        getInfoFormData();
      }
    );

    function resetDrawer() {
      infoForm.value = new InfoForm();
      if (props.infoSeq != 0) {
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

    function getInfoFormData() {
      resetDrawer();
      if (props.infoSeq != 0) {
        loading.value = true;
        InfoService.getInfoForm(props.infoSeq)
          .then((response) => {
            console.log(response);
            infoForm.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    function saveUpdatedContactData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      if (infoForm.value) {
        infoForm.value.tourUuid = props.tourUuid;
        console.log(infoForm.value);
        InfoService.saveInfoForm(infoForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('infoform-saved', response);
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

    function deleteTourInfoForm() {
      loading.value = true;
      InfoService.deleteInfoForm(props.infoSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('infoform-deleted', response);
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
      openInfoDrawer.value = false;
      resetDrawer();
      emit('infoform-drawer-closed');

    }

    return {
      t: i18n.global.t,
      title,
      infoForm,
      loading,
      openInfoDrawer,
      confirmbuttoncolor,
      confirmbuttonlabel,
      deletebuttonlabel,
      deleteTitle,
      resetbuttonlabel,
      cancelbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      empformDrawerContent,
      empformDrawerMenuAuth,
      drawerComp,
      openDeleteConfirm,
      resetDrawer,
      getInfoFormData,
      saveUpdatedContactData,
      deleteAction,
      deleteTourInfoForm,
      closeDrawer,
    };
  },
});
</script>
