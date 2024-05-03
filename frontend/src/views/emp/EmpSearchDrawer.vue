<template>
  <div id="empsearch-drawer">
    <drawer-comp
      v-model="opensearchDrawer"
      v-model:loading="loading"
      center-title
      confirm-button-color="secondary"
      confirm-button-label="Search"
      confirm-icon="search"
      icon-title="compass"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="false"
      :show-print-button="false"
      :show-reset-button="true"
      side="left"
      :title="title"
      :width="50"
      @cancel-clicked="closesearchDrawer"
      @confirm-clicked="searchUpdatedEmpData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-search-drawer-content
          v-model="empsearchData"
          ref="empsearchDrawerContent"
        />
      </div>
    </drawer-comp>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
// Components
import DrawerComp from 'src/components/drawers/DrawerComp.vue';
// View Layout
import EmpSearchDrawerContent from 'src/views/emp/EmpSearchDrawerContent.vue';
// Types
import { EmpSearchForm } from 'src/types/EmpSearchForm';
// Store
//import store from "src/store";
// Helpers
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
export default defineComponent({
  name: 'EmpSearchDrawer',
  components: {
    DrawerComp,
    EmpSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => EmpSearchForm,
      default: () => new EmpSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    'update:modelValue',
    'update:rowsearchdata',
    'empsearch-reset',
    'empsearch-saved',
  ],
  setup(props, { emit }) {
    const title = 'This is a filter for detailed seach for data search.';
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    showconfirmbutton.value = true;
    //store.getters.currentUserHasApplicationPermission("COD_R");
    const empsearchformDrawerContent = ref();
    const empsearchData = ref<EmpSearchForm>();

    useSyncModelValue(
      props,
      'rowsearchdata',
      emit,
      'update:rowsearchdata',
      empsearchData
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
        emit('update:modelValue', newValue);
      }
    );

    //Search Data
    function searchUpdatedEmpData() {
      emit('empsearch-saved');
      closesearchDrawer();
    }

    function searchResetData() {
      emit('empsearch-reset');
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      empsearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      empsearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedEmpData,
      searchResetData,
    };
  },
});
</script>

<style lang="scss">
#empsearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
