<template>
  <div id="cdcd-form-drawer">
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
        <cdcd-form-drawer-content
          v-model="cdcdformData"
          ref="codeFormDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteCdcdForm"
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
import CdcdFormDrawerContent from 'src/views/cdcd/CdcdFormDrawerContent.vue';
// Services
import { CdcdService } from 'src/services/CdcdService';
// Types
import { CdcdForm } from 'src/types/CdcdForm';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'CdcdFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    CdcdFormDrawerContent,
  },
  props: {
    codeSeq: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    'update:modelValue',
    'update:drawerData',
    'cdcdform-saved',
    'cdcdform-deleted',
    'cdcdform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageCreditCard');
    const cdcdformData = ref<CdcdForm>(new CdcdForm());
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
    const codeformDrawerContent = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);

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
        getCodeformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      cdcdformData.value = new CdcdForm();
      if (props.codeSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('ACCOUNT_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('ACCOUNT_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('ACCOUNT_WU');
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getCodeformData() {
      openDrawer.value = false;
      resetDrawer();
      if (props.codeSeq != 0) {
        loading.value = true;
        CdcdService.getOneCdcdForm(props.codeSeq)
          .then((response) => {
            cdcdformData.value = response;
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
      if (cdcdformData.value) {
        CdcdService.saveCdcdForm(cdcdformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            if (props.codeSeq != 0) {
              emit('cdcdform-saved', response);
              cdcdformData.value = new CdcdForm(response);
            } else {
              emit('cdcdform-saved', response);
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
    function deleteCdcdForm() {
      loading.value = true;
      CdcdService.deleteCdcdForm(props.codeSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('cdcdform-deleted', response);
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
      emit('cdcdform-drawer-closed');
    }
    return {
      t: i18n.global.t,
      title,
      cdcdformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      codeformDrawerContent,
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
      deleteCdcdForm,
      saveUpdatedCodeData,
    };
  },
});
</script>

<style lang="scss">
#cdcdform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
