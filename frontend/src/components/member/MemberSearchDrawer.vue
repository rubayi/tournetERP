<template>
  <div id="membersearch-drawer">
    <drawer-comp
      v-model="opensearchDrawer"
      v-model:loading="loading"
      center-title
      confirm-button-color="secondary"
      confirm-button-label="Search"
      confirm-icon="fas fa-search"
      icon-title="far fa-compass"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="false"
      :show-print-button="false"
      :show-reset-button="true"
      side="left"
      :title="title"
      :width="50"
      @cancel-clicked="closesearchDrawer"
      @confirm-clicked="searchUpdatedMemberData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <member-search-drawer-content
          v-model="membersearchData"
          ref="membersearchDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
// Components
import DrawerComp from "@/components/drawers/DrawerComp.vue";
// View Layout
import MemberSearchDrawerContent from "@/views/member/MemberSearchDrawerContent.vue";
// Types
import { MemberSearchForm } from "@/types/MemberSearchForm";
// Store
import store from "@/store";

//Helpers
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";
export default defineComponent({
  name: "MemberSearchDrawer",
  components: {
    DrawerComp,
    MemberSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => MemberSearchForm,
      default: () => new MemberSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "membersearch-reset",
    "membersearch-saved",
  ],
  setup(props, { emit }) {
    const title = "This is a filter for detailed seach for data search.";
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("USER_R");
    const membersearchformDrawerContent = ref();
    const membersearchData = ref<MemberSearchForm>();

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      membersearchData
    );

    watch(
      () => props.modelValue,
      (newValue) => {
        opensearchDrawer.value = newValue;
      }
    );

    watch(
      () => opensearchDrawer.value,
      (newValue) => {
        emit("update:modelValue", newValue);
      }
    );

    //Search Data
    function searchUpdatedMemberData() {
      emit("membersearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("membersearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      membersearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      membersearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedMemberData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#membersearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
