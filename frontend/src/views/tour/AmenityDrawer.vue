<template>
  <div id="tour-amenity-drawer">
    <drawer-comp
      v-model="openAmenityDrawer"
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
      ref="amenityDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <amenity-drawer-content
          v-model="amenityForm"
          :tourUuid="tourUuid"
          ref="amenityDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteTourAmenityForm"
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
import AmenityDrawerContent from 'src/views/tour/AmenityDrawerContent.vue';
// Service
import { AmenityService } from 'src/services/AmenityService';
// Type
import { AmenityForm } from 'src/types/AmenityForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'AmenityDrawer',
  components: {
    DrawerComp,
    DialogComp,
    AmenityDrawerContent,
  },
  props: {
    amenitySeq: {
      type: Number,
      default: 0,
    },
    amenityDrawer: {
      type: Boolean,
      default: false,
    },
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:amenityDrawer',
    'amenityform-deleted',
    'amenityform-saved',
    'amenityform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('infos');
    const amenityForm = ref<AmenityForm>(new AmenityForm());
    const loading = ref<boolean>(false);
    const openAmenityDrawer = ref<boolean>(false);
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
      () => props.amenityDrawer,
      (newValue) => {
        openAmenityDrawer.value = newValue;
      }
    );
    watch(
      () => openAmenityDrawer.value,
      (newValue) => {
        emit('update:amenityDrawer', newValue);
        getAmenityFormData();
      }
    );

    function resetDrawer() {
      amenityForm.value = new AmenityForm();
      if (props.amenitySeq != 0) {
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

    function getAmenityFormData() {
      resetDrawer();
      if (props.amenitySeq != 0) {
        loading.value = true;
        AmenityService.getAmenityForm(props.amenitySeq)
          .then((response) => {
            amenityForm.value = response;
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
      if (amenityForm.value) {
        amenityForm.value.tourUuid = props.tourUuid;
        console.log(amenityForm.value);
        AmenityService.saveAmenityForm(amenityForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('amenityform-saved', response);
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

    function deleteTourAmenityForm() {
      loading.value = true;
      AmenityService.deleteAmenityForm(props.amenitySeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('amenityform-deleted', response);
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
      openAmenityDrawer.value = false;
      resetDrawer();
      emit('amenityform-drawer-closed');

    }

    return {
      t: i18n.global.t,
      title,
      amenityForm,
      loading,
      openAmenityDrawer,
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
      getAmenityFormData,
      saveUpdatedContactData,
      deleteAction,
      deleteTourAmenityForm,
      closeDrawer,
    };
  },
});
</script>
