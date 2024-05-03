<template>
  <div id="compform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="far fa-folder-open q-ml-xs q-mr-md"></i>
            MANAGE COMPANY
          </span>
        </div>
        <div class="col-auto text-right q-pr-md">
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
        <div class="col-auto text-right q-pr-md">
          <q-btn
            class="q-ml-md"
            color="secondary"
            icon="download"
            label="EXPORT"
            outline
            @click="exportAction"
          />
          <q-btn
            class="q-ml-md"
            color="secondary"
            icon="print"
            label="PRINT"
            outline
            @click="printAction"
          />
        </div>
        <div class="col-auto text-right">
          <q-btn
            v-if="showinsertbutton"
            color="primary"
            icon="add"
            label="ADD NEW COMPANY"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="tournet-form-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="comp-form-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="compformGrid"
          />
        </div>
      </div>
      <comp-form-drawer
        v-model="openDrawer"
        :comp-sector-list="compSectorList"
        :comp-seq="compUuid"
        />
<!--      <comp-search-drawer-->
<!--        v-model="openSearchDrawer"-->
<!--        :compRateList="compRateList"-->
<!--        :compGroupList="compGroupList"-->
<!--        :couponYnList="couponYnList"-->
<!--        :rowsearchdata="searchdata"-->
<!--        @tournetsearch-reset="resetLoadData"-->
<!--        @tournetsearch-saved="loadData"-->
<!--      />-->
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from "lodash";
import { defineComponent, ref, watch } from "vue";

// Table
import { GridOptions } from "ag-grid-community";
import { CompFormTableConfig } from "src/views/comp/CompFormTableConfig";
import TableComp from "src/components/table/TableComp.vue";
import { TableHelper } from "src/components/table/TableHelper";
// Service
import { CompService } from "src/services/CompService";
import { CodeService } from "src/services/CodeService";
import { ReportService } from "src/services/ReportService";
//Type
import { CompForm } from "src/types/CompForm";
import { CompSearchForm } from "src/types/CompSearchForm";
import { CompListReportVO } from "src/types/CompReportVO";
import { SelectOption } from "src/types/SelectOption";
// Store
import store from "src/store";
// Helpers
import ReportHelper from "src/utils/helpers/ReportHelper";
// Drawer
import CompFormDrawer from "src/views/comp/CompFormDrawer.vue";
// import CompSearchDrawer from "src/views/comp/CompSearchDrawer.vue";

export default defineComponent({
  name: "CompForm",
  components: {
    TableComp,
    CompFormDrawer
    // CompSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = CompFormTableConfig.frameworkComponents;
    const columns = CompFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const reportHeaderMap = CompFormTableConfig.reportHeaderMap;
    const data = ref<CompForm[]>([]);
    const searchdefaultdata = ref<CompSearchForm>(
      new CompSearchForm()
    );
    const searchdata = ref<CompSearchForm>(new CompSearchForm());
    const compformGrid = ref();
    const compUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);

    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchBeginDt,
          searchdefaultdata.value.searchBeginDt
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchEndDt,
          searchdefaultdata.value.searchEndDt
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCompKor,
          searchdefaultdata.value.searchCompKor
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCompEng,
          searchdefaultdata.value.searchCompEng
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCompSector,
          searchdefaultdata.value.searchCompSector
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCompMemo,
          searchdefaultdata.value.searchCompMemo
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCompRate,
          searchdefaultdata.value.searchCompRate
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCouponYn,
          searchdefaultdata.value.searchCouponYn
        )
      ) {
        filterNumber.value++;
      }

      showinsertbutton.value = store.getters.currentUserHasApplicationPermission("COMP_W");
      if (store.getters.currentUserHasApplicationPermission("COMP_R")) {
        CompService.getAllComp(searchdata.value).then(
          (response) => {
            loading.value = false;
            compUuid.value = 0;
            console.log(response);
            data.value = response;

          }
        );
        printcodeValue();
      }
    }

    function resetLoadData() {
      searchdata.value = new CompSearchForm();
    }
    watch(loading, (loading) => {
      if (gridOptions.value.api) {
        if (loading) {
          // gridOptions.value.api.showLoadingOverlay();
        } else {
          // gridOptions.value.api.hideOverlay();
        }
      }
    });

    /* New */
    function createAction() {
      compUuid.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      compUuid.value = value;
      openDrawer.value = true;
    }

    /* Detail Filter */
    function filterAction() {
      openSearchDrawer.value = true;
    }

    /* Detail Export PDF */
    function exportAction() {
      const exportFilename = "TOURNET ERP";
      const listReportVO: CompListReportVO = {
        title: "",
        sort: compformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          compformGrid.value.getExportFilterString(reportHeaderMap),
        data: compformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportEXCELData(
        exportFilename,
        listReportVO,
        ReportService.getTournetListReport
      );
    }
    /* Detail Export PDF */
    function printAction() {
      const exportFilename = "TOURNET ERP";
      const listReportVO: CompListReportVO = {
        title: "",
        sort: compformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          compformGrid.value.getExportFilterString(reportHeaderMap),
        data: compformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.getTournetListPdfReport
      );
    }

    function printcodeValue() {
      loadCompRateListOptions();
      // loadCouponYnListOptions();
      // loadCompGroupListOptions();
      loadCompSectorOptions();
    }
    /*Comp Sector List*/
    const compSectorList = ref<SelectOption[]>([]);
    function loadCompSectorOptions() {
      CodeService.getGroupCodeForm(22).then((response) => {
        compSectorList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });

    }
    /*Comp Rate List*/
    const compRateList = ref<SelectOption[]>([]);
    function loadCompRateListOptions() {

      CodeService.getGroupCodeForm(239).then((response) => {
        compRateList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }
    /* Coupon Yn List*/
    const couponYnList = ref<SelectOption[]>([]);
    function loadCouponYnListOptions() {
      CodeService.getGroupCodeForm(343).then((response) => {
        couponYnList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    /*Comp Group List*/
    const compGroupList = ref<SelectOption[]>([]);
    function loadCompGroupListOptions() {
      CodeService.getGroupCodeForm(518).then((response) => {
        compGroupList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }


    return {
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      compUuid,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      frameworkComponents,
      compformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetLoadData,
      filterNumber,
      exportAction,
      printAction,
      showinsertbutton,
      compRateList,
      couponYnList,
      compGroupList,
      compSectorList,
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
#compform {
  #compform-grid-container {
    flex-grow: 1;
    #compform-grid {
      height: 100%;
    }
  }
}
</style>
