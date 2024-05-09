<template>
  <div id="emp-emergency-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="person"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="70"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedEmpData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-emergency-drawer-content
          v-model="emergencyForm"
          ref="empEmergencyDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Employee"
    @confirm-clicked="deleteEmpForm"
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
import EmpEmergencyDrawerContent from 'src/views/emp/EmpEmergencyDrawerContent.vue';
// Service
import { EmergencyService } from 'src/services/EmergencyService';
// Type
import { EmergencyForm } from 'src/types/EmergencyForm';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'EmpEmergencyDrawer',
  components: {
    DrawerComp,
    DialogComp,
    EmpEmergencyDrawerContent,
  },
  props: {
    empSeq: {
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
    'update:EmerDrawerData',
    'emerform-saved',
    'emerform-deleted',
    'emerform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = 'Manage Emergency Contancts';
    const emergencyForm = ref<EmergencyForm>(new EmergencyForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>('ADD');
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const empformDrawerContent = ref();
    const empformDrawerMenuAuth = ref();
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
        getEmerformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      emergencyForm.value = new EmergencyForm();
      if (props.empSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = 'CHANGE';
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = 'ADD';
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getEmerformData() {
      resetDrawer();
      if (props.empSeq != 0) {
        loading.value = true;
        EmergencyService.getEmerForm(props.empSeq)
          .then((response) => {
            emergencyForm.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }
    function saveUpdatedEmpData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification('Saving...');
      loading.value = true;
      if (emergencyForm.value) {
        EmergencyService.saveEmerForm(emergencyForm.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Employer  " ${response.emerName} " saved.`
            );
            if (props.empSeq != 0) {
              emit('emerform-saved', response);
              emergencyForm.value = new EmergencyForm(response);
            } else {
              emit('emerform-saved', response);
              resetDrawer();
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
    function deleteEmpForm() {
      loading.value = true;
      EmergencyService.deleteEmerForm(props.empSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Employer  ${
              emergencyForm.value ? emergencyForm.value.emerName : ''
            } deleted`
          );
          emit('emerform-deleted', response);
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
      emit('emerform-drawer-closed');
    }

    return {
      title,
      emergencyForm,
      loading,
      openDrawer,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      empformDrawerContent,
      empformDrawerMenuAuth,
      drawerComp,
      openDeleteConfirm,
      resetDrawer,
      getEmerformData,
      saveUpdatedEmpData,
      deleteAction,
      deleteEmpForm,
      closeDrawer,
    };
  },
});
</script>
<style lang="scss">
#Emergencyform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
