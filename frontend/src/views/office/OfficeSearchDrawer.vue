<template>
  <div id="officesearch-drawer">
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
      @confirm-clicked="searchUpdatedOfficeData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <office-search-drawer-content
          v-model="officesearchData"
          ref="officesearchDrawerContent"
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
import OfficeSearchDrawerContent from "@/views/office/OfficeSearchDrawerContent.vue";
// Types
import { OfficeSearchForm } from "@/types/OfficeSearchForm";
// Store
import store from "@/store";
//Helpers
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";
export default defineComponent({
  name: "OfficeSearchDrawer",
  components: {
    DrawerComp,
    OfficeSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => OfficeSearchForm,
      default: () => new OfficeSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "officesearch-reset",
    "officesearch-saved",
  ],
  setup(props, { emit }) {
    const title = "This is a filter for detailed seach for data search.";
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("OFFICE_R");
    const officesearchformDrawerContent = ref();
    const officesearchData = ref<OfficeSearchForm>();

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      officesearchData
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
    function searchUpdatedOfficeData() {
      emit("officesearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("officesearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      officesearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      officesearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedOfficeData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#officesearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
