<template>
  <div id="tour-contact-drawer">
    <drawer-comp
      v-model="openContactDrawer"
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
      ref="compDrawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tour-contact-drawer-content
          v-model="contactForm"
          :tourUuid="tourUuid"
          ref="tourContactDrawerContent"
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
import TourContactDrawerContent from 'src/views/tour/TourContactDrawerContent.vue';
// Service
import { TourContactService } from 'src/services/TourContactService';
// Type
import { TourContactForm } from 'src/types/TourContactForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'TourContactDrawer',
  components: {
    DrawerComp,
    DialogComp,
    TourContactDrawerContent,
  },
  props: {
    compSeq: {
      type: Number,
      default: 0,
    },
    contactDrawer: {
      type: Boolean,
      default: false,
    },
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  emits: [
    'update:contactDrawer',
    'contactform-deleted',
    'contactform-saved',
    'contactform-contactDrawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('tourContacts');
    const contactForm = ref<TourContactForm>(new TourContactForm());
    const loading = ref<boolean>(false);
    const openContactDrawer = ref<boolean>(false);
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
      () => props.contactDrawer,
      (newValue) => {
        openContactDrawer.value = newValue;
      }
    );
    watch(
      () => openContactDrawer.value,
      (newValue) => {
        emit('update:contactDrawer', newValue);
        getContactFormData();
      }
    );

    function resetDrawer() {
      contactForm.value = new TourContactForm();
      if (props.compSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('COMP_WU');
        showdeletebutton.value = false;
      }
    }

    function getContactFormData() {
      resetDrawer();
      if (props.compSeq != 0) {
        loading.value = true;
        TourContactService.getTourContactForm(props.compSeq)
          .then((response) => {
            contactForm.value = response;
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
      if (contactForm.value) {
        contactForm.value.tourUuid = props.tourUuid;
        console.log(contactForm.value);
        TourContactService.saveTourContactForm(contactForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );

            emit('contactform-saved', response);
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
      TourContactService.deleteTourContactForm(props.compSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('contactform-deleted', response);
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
      openContactDrawer.value = false;
      resetDrawer();
      emit('contactform-contactDrawer-closed');
    }

    return {
      t: i18n.global.t,
      title,
      contactForm,
      loading,
      openContactDrawer,
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
      getContactFormData,
      saveUpdatedContactData,
      deleteAction,
      deleteContactForm,
      closeDrawer,
    };
  },
});
</script>
