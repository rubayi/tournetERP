<template>
  <div id="compsearch-drawer">
    <drawer-comp
      v-model="opensearchDrawer"
      v-model:loading="loading"
      center-title
      confirm-button-color="secondary"
      :confirm-button-label="comfirmbuttonlabel"
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
          v-model="compsearchData"
          :comp-sector-list="compSectorList"
          :comp-rate-list="compRateList"
          :coupon-yn-list="couponYnList"
          :comp-group-list="compGroupList"
          ref="compsearchDrawerContent"
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
// Store
import store from "src/store";
// Helpers
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import i18n from "src/i18n";
import {SelectOption} from "src/types/SelectOption";
export default defineComponent({
  name: "CompSearchDrawer",
  components: {
    DrawerComp,
    CompSearchDrawerContent
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
    compSectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compGroupList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    couponYnList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    "update:modelValue",
    "update:rowsearchdata",
    "compsearch-reset",
    "compsearch-saved",
  ],
  setup(props, { emit }) {

    const title = i18n.global.t('searchtitle');
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    const comfirmbuttonlabel = ref<string>(i18n.global.t('search'));
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission("COMP_R");
    const compsearchDrawerContent = ref();
    const compsearchData = ref<CompSearchForm>(props.rowsearchdata);

    useSyncModelValue(
      props,
      "rowsearchdata",
      emit,
      "update:rowsearchdata",
      compsearchData
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
      emit("compsearch-saved");
      closesearchDrawer();
    }

    function searchResetData() {
      emit("compsearch-reset");
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      compsearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      compsearchDrawerContent,
      showconfirmbutton,
      searchUpdatedCompData,
      searchResetData,
      comfirmbuttonlabel
    };
  },
});
</script>

<style lang="scss">
#compsearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
