<template>
  <div id="carSearch-drawer">
    <drawer-comp
      v-model="opensearchDrawer"
      v-model:loading="loading"
      center-title
      confirm-button-color="secondary"
      :confirm-button-label="comfirmbuttonlabel"
      :cancel-button-label="cancelbuttonlabel"
      :reset-button-label="resetbuttonlabel"
      confirm-icon="search"
      icon-title="compass"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="false"
      :show-print-button="false"
      :show-reset-button="true"
      side="left"
      :title="title"
      :width="40"
      @cancel-clicked="closesearchDrawer"
      @confirm-clicked="searchUpdatedCompData"
      @reset-clicked="searchResetData"
      ref="drawersearchCar"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <car-search-drawer-content
          v-model="carsearchData"
          :car-type-list="carTypeList"
          :car-brand-list="carBrandList"
          ref="carsearchDrawerContent"
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
import CarSearchDrawerContent from 'src/views/car/CarSearchDrawerContent.vue';
// Types
import { CarSearchForm } from 'src/types/CarSearchForm';
// Store
import store from 'src/store';
// Helpers
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import i18n from 'src/i18n';
import { SelectOption } from 'src/types/SelectOption';
export default defineComponent({
  name: 'CarSearchDrawer',
  components: {
    DrawerComp,
    CarSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => CarSearchForm,
      default: () => new CarSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    carTypeList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carBrandList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },

  },
  emits: [
    'update:modelValue',
    'update:rowsearchdata',
    'carsearch-reset',
    'carsearch-saved',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('searchtitle');
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    const comfirmbuttonlabel = ref<string>(i18n.global.t('search'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    showconfirmbutton.value =
      store.getters.currentUserHasApplicationPermission('COMP_R');
    const carsearchDrawerContent = ref();
    const carsearchData = ref<CarSearchForm>(props.rowsearchdata);

    useSyncModelValue(
      props,
      'rowsearchdata',
      emit,
      'update:rowsearchdata',
      carsearchData
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
    function searchUpdatedCompData() {
      emit('carsearch-saved');
      closesearchDrawer();
    }

    function searchResetData() {
      emit('carsearch-reset');
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      carsearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      carsearchDrawerContent,
      showconfirmbutton,
      searchUpdatedCompData,
      searchResetData,
      comfirmbuttonlabel,
      resetbuttonlabel,
      cancelbuttonlabel,
    };
  },
});
</script>

<style lang="scss">
#carSearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
