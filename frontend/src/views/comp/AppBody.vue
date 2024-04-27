<template>
  <div id="tournetform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="far fa-folder-open q-ml-xs q-mr-md"></i>
            MANAGE COMPANY
          </span>
        </div>
        <div class="col-auto text-right q-pr-md">
          <select-comp
            v-model="searchdata.searchyeartype"
            class="full-width"
            dense
            :options="yearoptions"
            outlined
            @update:model-value="loadData"
          />
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
            label="NEW WORK ORDER"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="tournet-form-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="tournet-form-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="tournetformGrid"
          />
        </div>
      </div>
      <comp-form-drawer
        v-model="openDrawer"
        :attachmenttypelistgroup="attachmenttypelistgroup"
        :crewassignedlistgroup="crewassignedlistgroup"
        :langtypelistgroup="langtypelistgroup"
        :postedbylistgroup="postedbylistgroup"
        :receivedbylistgroup="receivedbylistgroup"
        :workdeplistgroup="workdeplistgroup"
        :worknum="worknum"
        :workstatuslistgroup="workstatuslistgroup"
        @tournetform-deleted="loadData"
        @tournetform-drawer-closed="worknum = 0"
        @tournetform-saved="loadData"
      />
      <comp-search-drawer
        v-model="openSearchDrawer"
        :crewassignedlistgroup="crewassignedlistgroup"
        :postedbylistgroup="postedbylistgroup"
        :receivedbylistgroup="receivedbylistgroup"
        :rowsearchdata="searchdata"
        :workdeplistgroup="workdeplistgroup"
        :workstatuslistgroup="workstatuslistgroup"
        @tournetsearch-reset="resetLoadData"
        @tournetsearch-saved="loadData"
      />
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
import { ComCodeService } from "src/services/ComCodeService";
import { ReportService } from "src/services/ReportService";
//Type
import { CompForm } from "src/types/CompForm";
import { CompSearchForm } from "src/types/CompSearchForm";
import { TournetListReportVO } from "src/types/ReportVO";
import { SelectOption } from "src/types/SelectOption";
// Helpers
import ReportHelper from "src/utils/helpers/ReportHelper";
// Drawer
import CompFormDrawer from "src/views/comp/CompFormDrawer.vue";
import CompSearchDrawer from "src/views/comp/CompSearchDrawer.vue";
import SelectComp from "src/components/common/SelectComp.vue";

export default defineComponent({
  name: "CompForm",
  components: {
    TableComp,
    SelectComp,
    CompFormDrawer,
    CompSearchDrawer,
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
    const tournetformGrid = ref();
    const worknum = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);

    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchsdate,
          searchdefaultdata.value.searchsdate
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchedate,
          searchdefaultdata.value.searchedate
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchsubject,
          searchdefaultdata.value.searchsubject
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchremarks,
          searchdefaultdata.value.searchremarks
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchcomment,
          searchdefaultdata.value.searchcomment
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchworkno,
          searchdefaultdata.value.searchworkno
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchletterto,
          searchdefaultdata.value.searchletterto
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchletterfrom,
          searchdefaultdata.value.searchletterfrom
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchreceiveddate,
          searchdefaultdata.value.searchreceiveddate
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchdatecrewassigned,
          searchdefaultdata.value.searchdatecrewassigned
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchdatecompleted,
          searchdefaultdata.value.searchdatecompleted
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchposteddate,
          searchdefaultdata.value.searchposteddate
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchdatesentbacktotraffic,
          searchdefaultdata.value.searchdatesentbacktotraffic
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchworkstatus,
          searchdefaultdata.value.searchworkstatus
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchletterrouteno,
          searchdefaultdata.value.searchletterrouteno
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchworkdep,
          searchdefaultdata.value.searchworkdep
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchcrewassigned,
          searchdefaultdata.value.searchcrewassigned
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchreceivedby,
          searchdefaultdata.value.searchreceivedby
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchpostedby,
          searchdefaultdata.value.searchpostedby
        )
      ) {
        filterNumber.value++;
      }
      searchdata.value.searchcount = filterNumber.value;
      showinsertbutton.value = true;
        //store.getters.currentUserHasApplicationPermission("LOG_A");
      //if (store.getters.currentUserHasApplicationPermission("LOG_R")) {
        CompService.getAllComp(searchdata.value).then(
          (response) => {
            loading.value = false;
            worknum.value = 0;
            data.value = response;
            if (gridOptions.value.columnApi) {
              // gridOptions.value.columnApi.applyColumnState({
              //   state: CompFormTableConfig.defaultSortModel,
              // });
            }
          }
        );
        printcodeValue();
     // }
    }

    function resetLoadData() {
      searchdata.value = new CompSearchForm();
      searchdata.value.searchyeartype = 0;
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
      worknum.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      worknum.value = value;
      openDrawer.value = true;
    }

    /* Detail Filter */
    function filterAction() {
      openSearchDrawer.value = true;
    }

    /* Detail Export PDF */
    function exportAction() {
      const exportFilename = "TOURNET ERP";
      const listReportVO: TournetListReportVO = {
        title: "",
        sort: tournetformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          tournetformGrid.value.getExportFilterString(reportHeaderMap),
        data: tournetformGrid.value.getFilteredRows(),
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
      const listReportVO: TournetListReportVO = {
        title: "",
        sort: tournetformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          tournetformGrid.value.getExportFilterString(reportHeaderMap),
        data: tournetformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.getTournetListPdfReport
      );
    }

    function printcodeValue() {
      loadcrewassignedListgroupOptions();
      loadreceivedbyListgroupOptions();
      loadpostedbyListgroupOptions();
      loadworkdepListgroupOptions();
      loadworkstatusListgroupOptions();
      loadattachmenttypeListgroupOptions();
      loadlangListgroupOptions();
    }
    /*Language Type List*/
    const langtypelistgroup = ref<SelectOption[]>([]);
    function loadlangListgroupOptions() {
      ComCodeService.getTournetLangType().then((response) => {
        langtypelistgroup.value = response.map(
          (x) => new SelectOption(x.namekr, x.id)
        );
      });
    }
    /*Filter Crew Assigned List*/
    const crewassignedlistgroup = ref<SelectOption[]>([]);
    function loadcrewassignedListgroupOptions() {

      CodeService.getGroupCodeForm(6).then((response) => {
        crewassignedlistgroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }
    /* Received by List*/
    const receivedbylistgroup = ref<string[]>([]);
    function loadreceivedbyListgroupOptions() {
      CompService.getGroupreceivedbyEntryForm().then(
        (response) => (receivedbylistgroup.value = response)
      );
    }

    /*Posted by List*/
    const postedbylistgroup = ref<string[]>([]);
    function loadpostedbyListgroupOptions() {
      CompService.getGrouppostedbyEntryForm().then(
        (response) => (postedbylistgroup.value = response)
      );
    }

    /*Work Order Dep List*/
    const workdeplistgroup = ref<SelectOption[]>([]);
    function loadworkdepListgroupOptions() {
      CodeService.getGroupCodeForm(9).then((response) => {
        workdeplistgroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeKr)
        );
      });
    }

    /*Filter Work Status List*/
    const workstatuslistgroup = ref<SelectOption[]>([]);
    function loadworkstatusListgroupOptions() {
      CodeService.getGroupCodeForm(5).then((response) => {
        workstatuslistgroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }
    /*Attachment Type*/
    const attachmenttypelistgroup = ref<SelectOption[]>([]);
    function loadattachmenttypeListgroupOptions() {
      CodeService.getGroupCodeForm(7).then((response) => {
        attachmenttypelistgroup.value = response.map(
          (x) => new SelectOption(x.codeKr + ":" + x.etc, x.codeUuid)
        );
      });
    }
    const yearoptions = ref<SelectOption[]>([]);
    yearoptionsgroupOptions();
    function yearoptionsgroupOptions() {
      yearoptions.value.push(new SelectOption("Last 1 Year", 0));
      yearoptions.value.push(new SelectOption("Last 3 Year", 3));
      yearoptions.value.push(new SelectOption("Last 5 Year", 5));
      yearoptions.value.push(new SelectOption("Last 10 Year", 10));
      yearoptions.value.push(new SelectOption("Last 20 Year", 20));
      yearoptions.value.push(new SelectOption("-All-", 99));
    }
    return {
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      worknum,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      frameworkComponents,
      tournetformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetLoadData,
      filterNumber,
      exportAction,
      printAction,
      showinsertbutton,
      crewassignedlistgroup,
      receivedbylistgroup,
      postedbylistgroup,
      workdeplistgroup,
      workstatuslistgroup,
      attachmenttypelistgroup,
      langtypelistgroup,
      yearoptions,
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
#tournetform {
  #tournetform-grid-container {
    flex-grow: 1;
    #tournetform-grid {
      height: 100%;
    }
  }
}
</style>
