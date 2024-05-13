<template>
  <div id="codesearch-drawer">
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
      @confirm-clicked="searchUpdatedCodeData"
      @reset-clicked="searchResetData"
      ref="drawersearchComp"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <cdcd-search-drawer-content
          v-model="codesearchData"
          ref="cdcdSearchDrawerContent"
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
import CdcdSearchDrawerContent from 'src/views/cdcd/CdcdSearchDrawerContent.vue';
// Types
import { CdcdSearchForm } from 'src/types/CdcdSearchForm';
// Helpers
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import i18n from 'src/i18n';
export default defineComponent({
  name: 'CodeSearchDrawer',
  components: {
    DrawerComp,
    CdcdSearchDrawerContent,
  },
  props: {
    rowsearchdata: {
      type: Object as () => CdcdSearchForm,
      default: () => new CdcdSearchForm(),
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    'update:modelValue',
    'update:rowsearchdata',
    'codesearch-reset',
    'codesearch-saved',
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
    const codesearchformDrawerContent = ref();
    const codesearchData = ref<CdcdSearchForm>(new CdcdSearchForm());

    useSyncModelValue(
      props,
      'rowsearchdata',
      emit,
      'update:rowsearchdata',
      codesearchData
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
    function searchUpdatedCodeData() {
      emit('codesearch-saved');
      closesearchDrawer();
    }

    function searchResetData() {
      emit('codesearch-reset');
    }

    function closesearchDrawer() {
      opensearchDrawer.value = false;
    }
    return {
      title,
      codesearchData,
      loading,
      opensearchDrawer,
      closesearchDrawer,
      codesearchformDrawerContent,
      showconfirmbutton,
      searchUpdatedCodeData,
      searchResetData,
      comfirmbuttonlabel,
      resetbuttonlabel,
      cancelbuttonlabel,
    };
  },
});
</script>

<style lang="scss">
#codesearch-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
