<template>
  <div id="hawaiifoodyform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="far fa-folder-open q-ml-xs q-mr-md"></i>
            HAWAIIFOODY WORK ORDER
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
      <div id="hawaiifoodyform-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="hawaiifoodyform-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :framework-components="frameworkComponents"
            :grid-options="gridOptions"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="hawaiifoodyformGrid"
          />
        </div>
      </div>
      <hawaiifoody-form-drawer
        v-model="openDrawer"
        :attachmenttypelistgroup="attachmenttypelistgroup"
        :crewassignedlistgroup="crewassignedlistgroup"
        :langtypelistgroup="langtypelistgroup"
        :postedbylistgroup="postedbylistgroup"
        :receivedbylistgroup="receivedbylistgroup"
        :workdeplistgroup="workdeplistgroup"
        :worknum="worknum"
        :workstatuslistgroup="workstatuslistgroup"
        @hawaiifoodyform-deleted="loadData"
        @hawaiifoodyform-drawer-closed="worknum = 0"
        @hawaiifoodyform-saved="loadData"
      />
      <hawaiifoody-search-drawer
        v-model="openSearchDrawer"
        :crewassignedlistgroup="crewassignedlistgroup"
        :postedbylistgroup="postedbylistgroup"
        :receivedbylistgroup="receivedbylistgroup"
        :rowsearchdata="searchdata"
        :workdeplistgroup="workdeplistgroup"
        :workstatuslistgroup="workstatuslistgroup"
        @hawaiifoodysearch-reset="resetloadData"
        @hawaiifoodysearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from "lodash";
import { defineComponent, ref, watch } from "vue";

// Table
import { GridOptions } from "ag-grid-community";
import { HawaiifoodyFormTableConfig } from "@/views/hawaiifoodysample/HawaiifoodyFormTableConfig";
import TableComp from "@/components/table/TableComp.vue";
import { TableHelper } from "@/components/table/TableHelper";
// Service
import { HawaiifoodyService } from "@/services/HawaiifoodyService";
import { CodeService } from "@/services/CodeService";
import { CommonCodeService } from "@/services/CommonCodeService";
import { ReportService } from "@/services/ReportService";
//Type
import { HawaiifoodyForm } from "@/types/HawaiifoodyForm";
import { HawaiifoodySearchForm } from "@/types/HawaiifoodySearchForm";
import { HawaiifoodyListReportVO } from "@/types/ReportVO";
import { SelectOption } from "@/types/SelectOption";
// Helpers
import ReportHelper from "@/utils/helpers/ReportHelper";
// Store
import store from "@/store";
// Drawer
import HawaiifoodyFormDrawer from "@/views/hawaiifoodysample/HawaiifoodyFormDrawer.vue";
import HawaiifoodySearchDrawer from "@/views/hawaiifoodysample/HawaiifoodySearchDrawer.vue";
import SelectComp from "@/components/common/SelectComp.vue";

export default defineComponent({
  name: "HawaiifoodyForm",
  components: {
    TableComp,
    SelectComp,
    HawaiifoodyFormDrawer,
    HawaiifoodySearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = HawaiifoodyFormTableConfig.frameworkComponents;
    const columns = HawaiifoodyFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const reportHeaderMap = HawaiifoodyFormTableConfig.reportHeaderMap;
    const data = ref<HawaiifoodyForm[]>([]);
    const searchdefaultdata = ref<HawaiifoodySearchForm>(
      new HawaiifoodySearchForm()
    );
    const searchdata = ref<HawaiifoodySearchForm>(new HawaiifoodySearchForm());
    const hawaiifoodyformGrid = ref();
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
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission("LOG_A");
      if (store.getters.currentUserHasApplicationPermission("LOG_R")) {
        HawaiifoodyService.getAllhawaiifoody(searchdata.value).then(
          (response) => {
            loading.value = false;
            worknum.value = 0;
            data.value = response;
            if (gridOptions.value.columnApi) {
              gridOptions.value.columnApi.applyColumnState({
                state: HawaiifoodyFormTableConfig.defaultSortModel,
              });
            }
          }
        );
        printcodeValue();
      }
    }

    function resetloadData() {
      searchdata.value = new HawaiifoodySearchForm();
      searchdata.value.searchyeartype = 0;
    }
    watch(loading, (loading) => {
      if (gridOptions.value.api) {
        if (loading) {
          gridOptions.value.api.showLoadingOverlay();
        } else {
          gridOptions.value.api.hideOverlay();
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
      const exportFilename = "HAWAII FOODY BRANCH";
      const listReportVO: HawaiifoodyListReportVO = {
        title: "",
        sort: hawaiifoodyformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          hawaiifoodyformGrid.value.getExportFilterString(reportHeaderMap),
        data: hawaiifoodyformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportEXCELData(
        exportFilename,
        listReportVO,
        ReportService.gethawaiifoodyListReport
      );
    }
    /* Detail Export PDF */
    function printAction() {
      const exportFilename = "HAWAII FOODY BRANCH";
      const listReportVO: HawaiifoodyListReportVO = {
        title: "",
        sort: hawaiifoodyformGrid.value.getExportSortString(reportHeaderMap),
        filter:
          hawaiifoodyformGrid.value.getExportFilterString(reportHeaderMap),
        data: hawaiifoodyformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportPDFData(
        exportFilename,
        listReportVO,
        ReportService.gethawaiifoodyListPdfReport
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
      CommonCodeService.getHawaiifoodyLangType().then((response) => {
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
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }
    /* Received by List*/
    const receivedbylistgroup = ref<string[]>([]);
    function loadreceivedbyListgroupOptions() {
      HawaiifoodyService.getGroupreceivedbyEntryForm().then(
        (response) => (receivedbylistgroup.value = response)
      );
    }

    /*Posted by List*/
    const postedbylistgroup = ref<string[]>([]);
    function loadpostedbyListgroupOptions() {
      HawaiifoodyService.getGrouppostedbyEntryForm().then(
        (response) => (postedbylistgroup.value = response)
      );
    }

    /*Work Order Dep List*/
    const workdeplistgroup = ref<SelectOption[]>([]);
    function loadworkdepListgroupOptions() {
      CodeService.getGroupCodeForm(9).then((response) => {
        workdeplistgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codename)
        );
      });
    }

    /*Filter Work Status List*/
    const workstatuslistgroup = ref<SelectOption[]>([]);
    function loadworkstatusListgroupOptions() {
      CodeService.getGroupCodeForm(5).then((response) => {
        workstatuslistgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }
    /*Attachment Type*/
    const attachmenttypelistgroup = ref<SelectOption[]>([]);
    function loadattachmenttypeListgroupOptions() {
      CodeService.getGroupCodeForm(7).then((response) => {
        attachmenttypelistgroup.value = response.map(
          (x) => new SelectOption(x.codename + ":" + x.desciption, x.codeSeq)
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
      hawaiifoodyformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetloadData,
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
#hawaiifoodyform {
  #hawaiifoodyform-grid-container {
    flex-grow: 1;
    #hawaiifoodyform-grid {
      height: 100%;
    }
  }
}
</style>
