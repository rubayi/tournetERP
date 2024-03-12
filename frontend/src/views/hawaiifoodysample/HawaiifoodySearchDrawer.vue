<template>
  <div id="hawaiifoodysearch-drawer">
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
      :width="60"
      @cancel-clicked="closesearchDrawer"
      @confirm-clicked="searchUpdatedHawaiifoodyData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <hawaiifoody-search-drawer-content
          v-model="hawaiifoodysearchData"
          :crewassignedlistgroup="crewassignedlistgroup"
          :letterroutenolistgroup="letterroutenolistgroup"
          :postedbylistgroup="postedbylistgroup"
          :receivedbylistgroup="receivedbylistgroup"
          :workdeplistgroup="workdeplistgroup"
          :workstatuslistgroup="workstatuslistgroup"
          ref="hawaiifoodysearchDrawerContent"
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
import HawaiifoodySearchDrawerContent from "@/views/hawaiifoodysample/HawaiifoodySearchDrawerContent.vue";
// Types
import { HawaiifoodySearchForm } from "@/types/HawaiifoodySearchForm";
import { SelectOption } from "@/types/SelectOption";
// Store
import store from "@/store";
// Helpers
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";
export default defineComponent({
  name: "HawaiifoodySearchDrawer",
  components: {
    DrawerComp,
    HawaiifoodySearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => HawaiifoodySearchForm,
      default: () => new HawaiifoodySearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    crewassignedlistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    letterroutenolistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    postedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
    receivedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
    workdeplistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    workstatuslistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "hawaiifoodysearch-reset",
    "hawaiifoodysearch-saved",
  ],
  setup(props, { emit }) {
    const title = "This is a filter for detailed seach for data search.";
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("LOG_R");
    const hawaiifoodysearchDrawerContent = ref();
    const hawaiifoodysearchData = ref<HawaiifoodySearchForm>();

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      hawaiifoodysearchData
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
    function searchUpdatedHawaiifoodyData() {
      emit("hawaiifoodysearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("hawaiifoodysearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      hawaiifoodysearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      hawaiifoodysearchDrawerContent,
      showconfirmbutton,
      searchUpdatedHawaiifoodyData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#hawaiifoodysearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
