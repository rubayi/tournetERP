<template>
  <div id="comp-contact-drawer">
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
        <comp-contact-drawer-content
          v-model="contactForm"
          :compUuid="compUuid"
          ref="compContactDrawerContent"
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
import CompContactDrawerContent from 'src/views/comp/CompContactDrawerContent.vue';
// Service
import { ContactService } from 'src/services/ContactService';
// Type
import { ContactForm } from 'src/types/ContactForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'CompContactDrawer',
  components: {
    DrawerComp,
    DialogComp,
    CompContactDrawerContent,
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
    compUuid: {
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
    const title = i18n.global.t('compContacts');
    const contactForm = ref<ContactForm>(new ContactForm());
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
      contactForm.value = new ContactForm();
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
        ContactService.getContactForm(props.compSeq)
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
        contactForm.value.compUuid = props.compUuid;
        console.log(contactForm.value);
        ContactService.saveContactForm(contactForm.value)
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
      ContactService.deleteContactForm(props.compSeq)
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
