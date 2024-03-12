<template>
  <div id="memberform-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-users"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="70"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedMemberData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <member-form-drawer-content
          v-model="memberformData"
          ref="memberformDrawerContent"
        />
        <member-form-drawer-permission
          v-model="memberformData.securityForm"
          ref="memberformpermissionDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-title="Delete Member"
    @confirm-clicked="deleteMemberForm"
  >
    <template #htmlContent>
      <div>Are you sure you want to <b>PERMANENTLY DELETE</b> this Data?</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

// Components
import DrawerComp from "@/components/drawers/DrawerComp.vue";
import DialogComp from "@/components/common/DialogComp.vue";

// Layout
import MemberFormDrawerContent from "@/views/member/MemberFormDrawerContent.vue";
import MemberFormDrawerPermission from "@/views/member/MemberFormDrawerPermission.vue";
// Services
import { MemberService } from "@/services/MemberService";

// Types
import { MemberForm } from "@/types/MemberForm";
// Store
import store from "@/store";
//helper
import { notificationHelper } from "@/utils/helpers/NotificationHelper";
export default defineComponent({
  name: "MemberFormDrawer",
  components: {
    DrawerComp,
    DialogComp,
    MemberFormDrawerContent,
    MemberFormDrawerPermission,
  },
  props: {
    memID: {
      type: Number,
      default: 0,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:drawerData",
    "memberform-saved",
    "memberform-deleted",
    "memberform-drawer-closed",
  ],
  setup(props, { emit }) {
    const title = "Manage Member";
    const memberformData = ref<MemberForm | null>(new MemberForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>("primary");
    const confirmbuttonlabel = ref<string>("ADD");
    const confirmicon = ref<string>("fas fa-plus");
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const memberformDrawerContent = ref();
    const memberformpermissionDrawerContent = ref();
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
        emit("update:modelValue", newValue);
        getMemberformData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      memberformData.value = new MemberForm();
      if (props.memID != 0) {
        confirmbuttoncolor.value = "warning";
        confirmbuttonlabel.value = "CHANGE";
        confirmicon.value = "fas fa-edit";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("USER_E");
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission("USER_D");
      } else {
        confirmbuttoncolor.value = "primary";
        confirmbuttonlabel.value = "ADD";
        confirmicon.value = "fas fa-plus";
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission("USER_A");
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getMemberformData() {
      resetDrawer();
      if (props.memID != 0) {
        loading.value = true;
        MemberService.getOneMemberForm(props.memID)
          .then((response) => {
            memberformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedMemberData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Saving...");
      loading.value = true;
      if (memberformData.value) {
        MemberService.saveMemberForm(memberformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              `Member  " ${response.firstName}  ${response.lastName} " saved.`
            );
            emit("memberform-saved", response);
            memberformData.value = new MemberForm(response);
            if (props.memID == 0) {
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
    function deleteMemberForm() {
      loading.value = true;
      MemberService.deleteMemberForm(props.memID)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            `Member ${
              memberformData.value ? memberformData.value.firstName : ""
            } deleted`
          );
          emit("memberform-deleted", response);
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
      emit("memberform-drawer-closed");
    }
    return {
      title,
      memberformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      memberformDrawerContent,
      memberformpermissionDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteMemberForm,
      saveUpdatedMemberData,
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
