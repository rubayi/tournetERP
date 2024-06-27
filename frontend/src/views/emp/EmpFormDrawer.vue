<template>
  <div id="emp-form-drawer">
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
      <div>
        <q-tabs v-model="tab" align="left">
          <q-tab name="content" :label="t('userInfoTab')" class="q-ml-md" />
          <q-tab
            name="menuAuth"
            :label="t('menuAuthTab')"
            v-if="empformData.empUuid"
          />
          <q-tab
            name="emergencyTn"
            :label="t('emergenTab')"
            v-if="empformData.empUuid"
          />
        </q-tabs>

        <q-tab-panels v-model="tab">
          <q-tab-panel name="content">
            <emp-form-drawer-content
              v-model="empformData"
              ref="empFormDrawerContent"
            />
          </q-tab-panel>

          <q-tab-panel name="menuAuth">
            <emp-form-drawer-menu-auth
              v-model="menuAuthList"
              ref="empFormDrawerMenuAuth"
              :data-val="checkedAuthUuids"
              :menu-max="menuMax"
              @update:authListEmpId="(newVal) => (checkedAuthUuids = newVal)"
            />
          </q-tab-panel>

          <q-tab-panel name="emergencyTn">
            <emp-emergency-tn-list
              :emp-uuid="empformData.empUuid"
              ref="empEmergencyTnList"
            />
          </q-tab-panel>
        </q-tab-panels>
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteEmpForm"
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
import EmpFormDrawerContent from 'src/views/emp/EmpFormDrawerContent.vue';
import EmpEmergencyTnList from 'src/views/emp/EmpEmergencyTnList.vue';
import EmpFormDrawerMenuAuth from 'src/views/emp/EmpFormDrawerMenuAuth.vue';
// Services
import { EmpService } from 'src/services/EmpService';
import { EmpAuthService, EmpAuthResponse } from 'src/services/EmpAuthService';
// Types
import { EmpForm } from 'src/types/EmpForm';
import { EmpAuthForm } from 'src/types/EmpAuthForm';
import { EmpAuthSearchForm } from 'src/types/EmpAuthSearchForm';
import { MenuAuthForm } from 'src/types/MenuAuthForm';
// Store
import store from 'src/store';
//helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
export default defineComponent({
  name: 'EmpFormDrawer',
  components: {
    DrawerComp,
    DialogComp,
    EmpFormDrawerContent,
    EmpEmergencyTnList,
    EmpFormDrawerMenuAuth,
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
    const title = i18n.global.t('manageEmployees');
    const empformData = ref<EmpForm>(new EmpForm());
    const empAuthFormDatas = ref<EmpAuthForm[]>([]);
    const menuAuthList = ref<MenuAuthForm[]>([]);
    const checkedAuthUuids = ref<number[]>([]);
    const prevCheckedIds = ref<number[]>([]);
    const menuMax = ref<number>(0);
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
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
    const tab = ref<string>('content');

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
        getAuthList();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      empformData.value = new EmpForm();
      if (props.empSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('USER_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('USER_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('USER_WU');
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

    // Loading Auth Datas
    function getEmpAuthFormDatas() {
      if (props.empSeq != 0) {
        loading.value = true;
        checkedAuthUuids.value = [];
        prevCheckedIds.value = [];
        const form: EmpAuthSearchForm = {
          empUuid: props.empSeq,
          empAuthUuid: 0,
          menuAuthUuid: 0,
          menuAuthUuids: [],
        };
        EmpAuthService.searchAuthListByEmpId(form)
          .then((response: EmpAuthResponse) => {
            empAuthFormDatas.value = response.menuAuths;
            for (let menuAuth of menuAuthList.value) {
              const empAuth = response.menuAuths.find(
                (empAuth) => empAuth.menuAuthUuid === menuAuth.menuAuthUuid
              );
              if (empAuth) {
                checkedAuthUuids.value.push(menuAuth.menuAuthUuid);
                prevCheckedIds.value.push(menuAuth.menuAuthUuid);
              }
            }
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    function getAuthList() {
      EmpAuthService.selectMenuAuths().then((response) => {
        menuAuthList.value = response.menuAuths;
        menuMax.value = response.maxNumber;
        getEmpAuthFormDatas();
      });
    }

    function saveUpdatedEmpData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      const idsToDelete = prevCheckedIds.value.filter(
        (id) => !checkedAuthUuids.value.includes(id)
      );
      const idsToAdd = checkedAuthUuids.value.filter(
        (id) => !prevCheckedIds.value.includes(id)
      );
      if (empformData.value) {
        if (idsToDelete) {
          EmpAuthService.deleteEmpAuth({
            menuAuthUuids: idsToDelete,
            empUuid: empformData.value.empUuid,
            empAuthUuid: 0,
            menuAuthUuid: 0,
          }).catch((error) => {
            notificationHelper.createErrorNotification(
              notificationHelper.formatResponseToErrorMessage(error.response)
            );
          });
        }
        if (idsToAdd) {
          EmpAuthService.updateEmpAuth({
            menuAuthUuids: idsToAdd,
            empUuid: empformData.value.empUuid,
            empAuthUuid: 0,
            menuAuthUuid: 0,
          }).catch((error) => {
            notificationHelper.createErrorNotification(
              notificationHelper.formatResponseToErrorMessage(error.response)
            );
          });
        }
        EmpService.saveEmpForm(empformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
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
            i18n.global.t('deletesucess')
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
      t: i18n.global.t,
      tab,
      title,
      empformData,
      empAuthFormDatas,
      menuAuthList,
      checkedAuthUuids,
      menuMax,
      loading,
      openDrawer,
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
