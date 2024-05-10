<template>
  <div id="codeform-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="setting"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="50"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedCodeData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <code-form-drawer-content
          v-model="codeformData"
          ref="codeFormDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Code"
    @confirm-clicked="deleteCodeForm"
  >
    <template #htmlContent>
      <div>Are you sure you want to <b>PERMANENTLY DELETE</b> this Data?</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
// Components
import DrawerComp from 'src/components/drawers/DrawerComp.vue';
import DialogComp from 'src/components/common/DialogComp.vue';
// View Layout
import CodeFormDrawerContent from 'src/views/code/CodeFormDrawerContent.vue';
// Services
import { CodeService } from 'src/services/CodeService';
// Types
import { CodeForm } from 'src/types/CodeForm';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
//Lang
import i18n from "src/i18n";
export default defineComponent({
  name: 'CodeFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    CodeFormDrawerContent,
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
    'codeform-saved',
    'codeform-deleted',
    'codeform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageCodes');
    const codeformData = ref<CodeForm>(new CodeForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('fas fa-plus');
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
      codeformData.value = new CodeForm();
      if (props.codeSeq != 0) {
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
    function getCodeformData() {
      resetDrawer();
      if (props.codeSeq != 0) {
        loading.value = true;
        CodeService.getOneCodeForm(props.codeSeq)
          .then((response) => {
            codeformData.value = response;
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
      if (codeformData.value) {
        CodeService.saveCodeForm(codeformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(i18n.global.t('saved'));
            if (props.codeSeq != 0) {
              emit('codeform-saved', response);
              codeformData.value = new CodeForm(response);
            } else {
              emit('codeform-saved', response);
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
    function deleteCodeForm() {
      loading.value = true;
      CodeService.deleteCodeForm(props.codeSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(i18n.global.t('deletesucess'));
          emit('codeform-deleted', response);
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
      emit('codeform-drawer-closed');
    }
    return {
      title,
      codeformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      codeformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      deletebuttonlabel,
      resetbuttonlabel,
      cancelbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteCodeForm,
      saveUpdatedCodeData,
    };
  },
});
</script>

<style lang="scss">
#codeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
