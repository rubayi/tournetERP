<template>
  <div id="tournetsearch-drawer">
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
      @confirm-clicked="searchUpdatedTournetData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tournet-search-drawer-content
          v-model="tournetsearchData"
          :crewassignedlistgroup="crewassignedlistgroup"
          :letterroutenolistgroup="letterroutenolistgroup"
          :postedbylistgroup="postedbylistgroup"
          :receivedbylistgroup="receivedbylistgroup"
          :workdeplistgroup="workdeplistgroup"
          :workstatuslistgroup="workstatuslistgroup"
          ref="tournetsearchDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
// Components
import DrawerComp from "src/components/drawers/DrawerComp.vue";
// View Layout
import TournetSearchDrawerContent from "src/views/comp/CompSearchDrawerContent.vue";
// Types
import { TournetSearchForm } from "src/types/TournetSearchForm";
import { SelectOption } from "src/types/SelectOption";
// Store
import store from "src/store";
// Helpers
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
export default defineComponent({
  name: "TournetSearchDrawer",
  components: {
    DrawerComp,
    TournetSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => TournetSearchForm,
      default: () => new TournetSearchForm(),
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
    "tournetsearch-reset",
    "tournetsearch-saved",
  ],
  setup(props, { emit }) {
    const title = "This is a filter for detailed seach for data search.";
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("LOG_R");
    const tournetsearchDrawerContent = ref();
    const tournetsearchData = ref<TournetSearchForm>();

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      tournetsearchData
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
    function searchUpdatedTournetData() {
      emit("tournetsearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("tournetsearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      tournetsearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      tournetsearchDrawerContent,
      showconfirmbutton,
      searchUpdatedTournetData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#tournetsearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
