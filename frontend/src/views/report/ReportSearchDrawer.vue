<template>
  <div id="reportsearch-drawer">
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
      @confirm-clicked="searchUpdatedReportData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <report-search-drawer-content
          v-model="reportsearchData"
          ref="reportsearchDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
// Components
import DrawerComp from "@/components/drawers/DrawerComp.vue";
// Layout
import ReportSearchDrawerContent from "@/views/report/ReportSearchDrawerContent.vue";
// Types
import { ReportSearchForm } from "@/types/ReportSearchForm";
// Store
import store from "@/store";
//Service
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";
export default defineComponent({
  name: "ReportSearchDrawer",
  components: {
    DrawerComp,
    ReportSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => ReportSearchForm,
      default: () => new ReportSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "reportsearch-reset",
    "reportsearch-saved",
  ],
  setup(props, { emit }) {
    const title = "This is a filter for detailed seach for data search.";
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("REP_R");
    const reportsearchformDrawerContent = ref();
    const reportsearchData = ref<ReportSearchForm>();

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      reportsearchData
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
    function searchUpdatedReportData() {
      emit("reportsearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("reportsearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      reportsearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      reportsearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedReportData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#reportsearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
