<template>
  <div id="emp-form-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-cogs"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="60"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedEmpData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-form-drawer-content
          v-model="empformData"
          ref="empFormDrawerContent"
        />
        <!-- <emp-form-drawer-menu-auth
          v-if="edited.empUuid"
          :data-val="empMunuAuthList"
          :option-list="munuAuthList"
          :req-list="checkedAuthUuids"
          :menu-max="munuMax"
          @update:checkedAuthUuids="munuAuthList = $event"
        /> -->
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
import EmpFormDrawerContent from 'src/views/emp/EmpFormDrawerContent.vue';
// Services
import { EmpService } from 'src/services/EmpService';
// Types
import { EmpForm } from 'src/types/EmpForm';
// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
export default defineComponent({
  name: 'EmpFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    EmpFormDrawerContent,
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
    'update:drawerData',
    'empform-saved',
    'empform-deleted',
    'empform-drawer-closed',
  ],
  setup(props, { emit }) {
    const title = 'Manage Employees';
    const empformData = ref<EmpForm | null>(new EmpForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>('ADD');
    const confirmicon = ref<string>('fas fa-plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const empformDrawerContent = ref();
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
        getEmpformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      empformData.value = new EmpForm();
      if (props.empSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = 'CHANGE';
        confirmicon.value = 'fas fa-edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = 'ADD';
        confirmicon.value = 'fas fa-plus';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CODE_W');
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getEmpformData() {
      resetDrawer();
      if (props.empSeq != 0) {
        loading.value = true;
        EmpService.getEmpForm(props.empSeq)
          .then((response) => {
            empformData.value = response;
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
      if (empformData.value) {
        EmpService.saveEmpForm(empformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Employer  " ${response.empKor} " saved.`
            );
            if (props.empSeq != 0) {
              emit('empform-saved', response);
              empformData.value = new EmpForm(response);
            } else {
              emit('empform-saved', response);
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
      EmpService.deleteEmpForm(props.empSeq)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Employer  ${
              empformData.value ? empformData.value.empKor : ''
            } deleted`
          );
          emit('empform-deleted', response);
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
      emit('empform-drawer-closed');
    }

    return {
      title,
      empformData,
      loading,
      openDrawer,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      empformDrawerContent,
      drawerComp,
      openDeleteConfirm,
      resetDrawer,
      getEmpformData,
      saveUpdatedEmpData,
      deleteAction,
      deleteEmpForm,
      closeDrawer,
    };
  },
});
</script>

<style lang="scss">
#officeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
