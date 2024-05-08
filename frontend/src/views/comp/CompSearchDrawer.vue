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
      @confirm-clicked="searchUpdatedCompData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <comp-search-drawer-content
          v-model="tournetsearchData"
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
import CompSearchDrawerContent from "src/views/comp/CompSearchDrawerContent.vue";
// Types
import { CompSearchForm } from "src/types/CompSearchForm";
import { SelectOption } from "src/types/SelectOption";
// Store
import store from "src/store";
// Helpers
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import i18n from "@/i18n";
export default defineComponent({
  name: "CompSearchDrawer",
  components: {
    DrawerComp,
    CompSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => CompSearchForm,
      default: () => new CompSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "tournetsearch-reset",
    "tournetsearch-saved",
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('searchtitle');
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("COMP_R");
    const tournetsearchDrawerContent = ref();
    const tournetsearchData = ref<CompSearchForm>();

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
    function searchUpdatedCompData() {
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
      searchUpdatedCompData,
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
