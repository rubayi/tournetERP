<template>
  <div id="car-img-drawer">
    <drawer-comp
      v-model="openCarImgDrawer"
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
      @cancel-clicked="closeImgDrawer"
      @confirm-clicked="saveUpdatedCarImgData"
      @delete-clicked="openDeleteConfirm = true"
      ref="compDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <car-img-drawer-content
          v-model="carimgForm"
          :trnCarUuid="trnCarUuid"
          :upload-file="uploadFile"
          ref="carImgDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteCarImgForm"
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
import CarImgDrawerContent from 'src/views/car/CarImgDrawerContent.vue';
// Service
import { CarImgService } from 'src/services/CarImgService';
// Type
import { CarImgForm } from 'src/types/CarImgForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import { AnyData } from 'src/types/AnyData';

export default defineComponent({
  name: 'CarImgDrawer',
  components: {
    DrawerComp,
    DialogComp,
    CarImgDrawerContent,
  },
  props: {
    carImgSeq: {
      type: Number,
      default: 0,
    },
    carImgDrawer: {
      type: Boolean,
      default: false,
    },
    trnCarUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:carImgDrawer',
    'carimgform-deleted',
    'carimgform-saved',
    'carimgformCarImgDrawerClosed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('compCarImgs');
    const carimgForm = ref<CarImgForm>(new CarImgForm());
    const loading = ref<boolean>(false);
    const openCarImgDrawer = ref<boolean>(false);
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
    const attfile = ref<AnyData | null>(null);

    watch(
      () => props.carImgDrawer,
      (newValue) => {
        openCarImgDrawer.value = newValue;
      }
    );
    watch(
      () => openCarImgDrawer.value,
      (newValue) => {
        emit('update:carImgDrawer', newValue);
        getCarImgFormData();
      }
    );

    function resetDrawer() {
      carimgForm.value = new CarImgForm();
      attfile.value = null;

      if (props.carImgSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CAR_WU');
        showdeletebutton.value = false;
      }
    }

    function getCarImgFormData() {
      resetDrawer();
      if (props.carImgSeq != 0) {
        loading.value = true;
        CarImgService.getCarImgForm(props.carImgSeq)
          .then((response) => {
            carimgForm.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    function saveUpdatedCarImgData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      if (carimgForm.value) {
        const fileToUpload = attfile.value || null;

        carimgForm.value.trnCarUuid = props.trnCarUuid;
        CarImgService.saveCarImgForm(fileToUpload, carimgForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('carimgform-saved', response);
            closeImgDrawer();
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

    function deleteCarImgForm() {
      loading.value = true;
      CarImgService.deleteCarImgForm(props.carImgSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('carimgform-deleted', response);
          closeImgDrawer();
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

    function uploadFile(files: AnyData) {
      if (files) {
        attfile.value = files;
      }
    }

    function closeImgDrawer() {
      openCarImgDrawer.value = false;
      resetDrawer();
      emit('carimgformCarImgDrawerClosed');
    }

    return {
      t: i18n.global.t,
      title,
      carimgForm,
      loading,
      openCarImgDrawer,
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
      getCarImgFormData,
      saveUpdatedCarImgData,
      deleteAction,
      deleteCarImgForm,
      closeImgDrawer,
      uploadFile,
    };
  },
});
</script>
