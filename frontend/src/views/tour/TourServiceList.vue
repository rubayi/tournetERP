<template>
  <div id="tour-service-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="tips_and_updates" class="q-mr-sm"></q-icon
            >{{ t('tourServices') }}</span
          >
        </div>
        <q-btn
          class="q-mr-xs"
          v-if="showinsertbutton"
          icon="add"
          :label="t('addnew')"
          style="color: darkgreen"
          @click="createAction"
        />
      </div>
      <div id="tour-service-list-grid-container" class="row grow-1">
        <table-comp
          id="tour-service-list-grid"
          :column-defs="columns"
          :context="context"
          :framework-components="frameworkComponents"
          :loading="loading"
          :overlay-loading-template="overlayLoadingTemplate"
          :pagination="false"
          :gridHeight="'300'"
          :row-data="data"
          :open-action="openAction"
          row-selection="single"
          @grid-ready="loadData"
          ref="tourServiceFormGrid"
        />
      </div>
      <tour-service-drawer
        :tour-service-drawer="openTourServiceDrawer"
        :tour-seq="serviceUuid"
        :tour-uuid="tourUuid"
        @tourserviceform-deleted="loadData"
        @tourserviceform-drawer-closed="serviceUuid = 0"
        @tourserviceform-saved="loadData"
      />
    </q-page>
  </div>
</template>
<script lang="ts">
import { ref, defineComponent } from 'vue';
//Lang
import i18n from 'src/i18n';
// Table
import { GridOptions } from 'ag-grid-community';
import { TableHelper } from 'src/components/table/TableHelper';
import TableComp from 'src/components/table/TableComp.vue';
import { TourServiceTableConfig } from 'src/views/tour/TourServiceTableConfig';
// Service
import { TourServiceService } from 'src/services/TourServiceService';
// Type
import { TourServiceForm } from 'src/types/TourServiceForm';
import { TourServiceSearchForm } from 'src/types/TourServiceSearchForm';
// Store
import store from 'src/store';
// Drawer
import TourServiceDrawer from 'src/views/tour/TourServiceDrawer.vue';

export default defineComponent({
  name: 'TourServiceList',
  components: {
    TableComp,
    TourServiceDrawer,
  },
  props: {
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openTourServiceDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = TourServiceTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      TourServiceTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<TourServiceForm[]>([]);
    const searchdata = ref<TourServiceSearchForm>(new TourServiceSearchForm());
    const infoFormGrid = ref();
    const serviceUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    const loadData = () => {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        searchdata.value.searchTourUuid = props.tourUuid;
        TourServiceService.getTourServiceList(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;

          }
        });
      }
    };
    function createAction() {
      serviceUuid.value = 0;
      openTourServiceDrawer.value = true;
    }
    function openAction(value: number) {
      serviceUuid.value = value;
      openTourServiceDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      serviceUuid,
      openTourServiceDrawer,
      createAction,
      openAction,
      infoFormGrid,
      overlayLoadingTemplate,
      searchdata,
      showinsertbutton,
      frameworkComponents,
    };
  },
  data() {
    return {
      context: {
        componentParent: this,
      },
    };
  },
});
</script>
<style lang="scss">
#tour-service-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #tour-service-list-grid-container {
    height: 300px;

    #tour-service-list-grid {
      height: 300px;
    }
  }
}
.emer-title {
  font-weight: bold;
  font-size: 18px;
  color: darkgreen;
}
</style>
