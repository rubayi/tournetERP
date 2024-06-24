<template>
  <div id="hotel-room-drawer">
    <drawer-comp
      v-model="openHotelRoomDrawer"
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
      ref="hotelRoomDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <hotel-room-drawer-content
          v-model="hotelRoomForm"
          :service-uuid="serviceUuid"
          ref="hotelRoomDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteTourHotelRoomForm"
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
import HotelRoomDrawerContent from 'src/views/tour/HotelRoomDrawerContent.vue';
// Service
import { HotelRoomService } from 'src/services/HotelRoomService';
// Type
import { HotelRoomForm } from 'src/types/HotelRoomForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'HotelRoomDrawer',
  components: {
    DrawerComp,
    DialogComp,
    HotelRoomDrawerContent,
  },
  props: {
    roomSeq: {
      type: Number,
      default: 0,
    },
    hotelRoomDrawer: {
      type: Boolean,
      default: false,
    },
    serviceUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:hotelRoomDrawer',
    'hotelroomform-deleted',
    'hotelroomform-saved',
    'hotelroomform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('infos');
    const hotelRoomForm = ref<HotelRoomForm>(new HotelRoomForm());
    const loading = ref<boolean>(false);
    const openHotelRoomDrawer = ref<boolean>(false);
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
      () => props.hotelRoomDrawer,
      (newValue) => {
        openHotelRoomDrawer.value = newValue;
      }
    );
    watch(
      () => openHotelRoomDrawer.value,
      (newValue) => {
        emit('update:hotelRoomDrawer', newValue);
        getHotelRoomFormData();
      }
    );

    function resetDrawer() {
      hotelRoomForm.value = new HotelRoomForm();
      if (props.roomSeq != 0) {
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

    function getHotelRoomFormData() {
      resetDrawer();
      if (props.roomSeq != 0) {
        loading.value = true;
        HotelRoomService.getOneHotelRoomForm(props.roomSeq)
          .then((response) => {
            hotelRoomForm.value = response;
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
      if (hotelRoomForm.value) {
        hotelRoomForm.value.serviceUuid = props.serviceUuid;
        console.log(hotelRoomForm.value);
        HotelRoomService.saveHotelRoomForm(hotelRoomForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('hotelroomform-saved', response);
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

    function deleteTourHotelRoomForm() {
      loading.value = true;
      HotelRoomService.deleteHotelRoomForm(props.roomSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('hotelroomform-deleted', response);
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
      openHotelRoomDrawer.value = false;
      resetDrawer();
      emit('hotelroomform-drawer-closed');

    }

    return {
      t: i18n.global.t,
      title,
      hotelRoomForm,
      loading,
      openHotelRoomDrawer,
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
      getHotelRoomFormData,
      saveUpdatedContactData,
      deleteAction,
      deleteTourHotelRoomForm,
      closeDrawer,
    };
  },
});
</script>
