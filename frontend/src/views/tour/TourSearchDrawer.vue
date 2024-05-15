<template>
  <div id="toursearch-drawer">
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
      :width="30"
      @cancel-clicked="closesearchDrawer"
      @confirm-clicked="searchUpdatedTourData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tour-search-drawer-content
          v-model="toursearchData"
          ref="tourSearchDrawerContent"
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
import TourSearchDrawerContent from 'src/views/tour/TourSearchDrawerContent.vue';
// Types
import { TourSearchForm } from 'src/types/TourSearchForm';
// Helpers
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import i18n from 'src/i18n';
export default defineComponent({
  name: 'TourSearchDrawer',
  components: {
    DrawerComp,
    TourSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => TourSearchForm,
      default: () => new TourSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    'update:modelValue',
    'update:rowsearchdata',
    'toursearch-reset',
    'toursearch-saved',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('searchtitle');
    const loading = ref<boolean>(false);
    const opensearchDrawer = ref<boolean>(false);
    const showconfirmbutton = ref<boolean>(false);
    const comfirmbuttonlabel = ref<string>(i18n.global.t('search'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    showconfirmbutton.value = true;
    //store.getters.currentUserHasApplicationPermission("COD_R");
    const toursearchformDrawerContent = ref();
    const toursearchData = ref<TourSearchForm>(new TourSearchForm());

    useSyncModelValue(
      props,
      'rowsearchdata',
      emit,
      'update:rowsearchdata',
      toursearchData
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
    function searchUpdatedTourData() {
      emit('toursearch-saved');
      closesearchDrawer();
    }

    function searchResetData() {
      emit('toursearch-reset');
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      toursearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      toursearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedTourData,
      searchResetData,
      comfirmbuttonlabel,
      resetbuttonlabel,
      cancelbuttonlabel,
    };
  },
});
</script>

<style lang="scss">
#toursearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
