<template>
  <div id="reportform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="fas fa-print q-ml-xs q-mr-md"></i>
            Manage Reports
          </span>
        </div>
        <div class="col-2 text-right q-pr-md">
          <q-btn
            color="secondary"
            icon="search"
            label="Filters"
            @click="filterAction"
          >
            <q-badge v-if="filterNumber > 0" color="red" floating>{{
              filterNumber
            }}</q-badge>
          </q-btn>
        </div>
        <div class="col-auto text-right">
          <q-btn
            v-if="showinsertbutton"
            color="primary"
            icon="add"
            label="NEW REPORT"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="reportform-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="reportform-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="reportformGrid"
          />
        </div>
      </div>
      <report-form-drawer
        v-model="openDrawer"
        :reportid="reportId"
        @reportform-deleted="loadData"
        @reportform-drawer-closed="reportId = 0"
        @reportform-saved="loadData"
      />
      <report-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @reportsearch-reset="resetloadData"
        @reportsearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref} from "vue";
import _ from "lodash";

//Layout
import ReportFormDrawer from "src/views/report/ReportFormDrawer.vue";
import ReportSearchDrawer from "src/views/report/ReportSearchDrawer.vue";

//Service
import { ManageReportService } from "src/services/ManageReportService";

//Type
import { ReportForm } from "src/types/ReportForm";
import { ReportSearchForm } from "src/types/ReportSearchForm";
// Store
import store from "src/store";
//Table
import { GridOptions } from "ag-grid-community";
import { ReportFormTableConfig } from "src/views/report/ReportFormTableConfig";
import TableComp from "src/components/table/TableComp.vue";
import { TableHelper } from "src/components/table/TableHelper";
export default defineComponent({
  name: "ReportForm",
  components: {
    TableComp,
    ReportFormDrawer,
    ReportSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = ReportFormTableConfig.frameworkComponents;
    const columns = ReportFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<ReportForm[]>([]);
    const searchdefaultdata = ref<ReportSearchForm>(new ReportSearchForm());
    const searchdata = ref<ReportSearchForm>(new ReportSearchForm());
    const reportformGrid = ref();
    const reportId = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchReportGroup,
          searchdefaultdata.value.searchReportGroup
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchReportType,
          searchdefaultdata.value.searchReportType
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchName,
          searchdefaultdata.value.searchName
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission("REP_A");
      if (store.getters.currentUserHasApplicationPermission("REP_R")) {
        ManageReportService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          reportId.value = 0;
          data.value = response;
          // if (gridOptions.value.columnApi) {
          //   gridOptions.value.columnApi.applyColumnState({
          //     state: ReportFormTableConfig.defaultSortModel,
          //   });
          // }
        });
      }
    }
    function resetloadData() {
      searchdata.value = new ReportSearchForm();
    }

    // watch(loading, (loading) => {
    //   if (gridOptions.value.api) {
    //     // if (loading) {
    //     //   gridOptions.value.api.showLoadingOverlay();
    //     // } else {
    //     //   gridOptions.value.api.hideOverlay();
    //     // }
    //   }
    // });

    /* New */
    function createAction() {
      reportId.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      reportId.value = value;
      openDrawer.value = true;
    }

    /* Detail Filter */
    function filterAction() {
      openSearchDrawer.value = true;
    }
    return {
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      reportId,
      openDrawer,
      reportformGrid,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      frameworkComponents,
      overlayLoadingTemplate,
      searchdata,
      resetloadData,
      filterNumber,
      showinsertbutton,
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
#reportform {
  #reportform-grid-container {
    flex-grow: 1;

    #reportform-grid {
      height: 100%;
    }
  }
}
</style>
