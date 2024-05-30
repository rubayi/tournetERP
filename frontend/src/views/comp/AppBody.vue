<template>
  <div id="compform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-sm">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <q-icon name="business" class="q-ml-xs q-mr-sm" size="sm"></q-icon>
            {{ t('manageCompany') }}
          </span>
        </div>
        <div class="col-auto text-right q-pr-md">
          <q-btn
            color="secondary"
            icon="search"
            :label="t('search')"
            @click="filterAction"
          >
            <q-badge v-if="filterNumber > 0" color="red" floating>{{
              filterNumber
            }}</q-badge>
          </q-btn>
        </div>
        <div class="col-auto text-right q-pr-md">
          <q-btn
            v-if="showexportbutton"
            class="q-ml-md"
            color="secondary"
            icon="download"
            :label="t('export')"
            outline
            @click="exportAction"
          />
          <q-btn
            class="q-ml-md"
            color="secondary"
            icon="print"
            :label="t('print')"
            outline
            @click="printAction"
          />
        </div>
        <div class="col-auto text-right">
          <q-btn
            v-if="showinsertbutton"
            color="primary"
            icon="add"
            :label="t('addnew')"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="tournet-form-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="comp-form-grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :open-action="openAction"
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
        :comp-rate-list="compRateList"
        :comp-optionRate-List="compOptionRateList"
        :coupon-yn-list="couponYnList"
        :comp-group-list="compGroupList"
        :hotel-rate-list="hotelRateList"
        :pkg-rate-list="pkgRateList"
        :pkg-only-rate-list="pkgOnlyRateList"
        :rentcar-rate-list="rentcarRateList"
        :restaurant-rate-list="restaurantRateList"
        :honeymoon-only-rate-list="honeymoonOnlyRateList"
        :honeymoon-rate-list="honeymoonRateList"
        :prepaid-how-list="prepaidHowList"
        :comp-seq="compUuid"
        @compform-deleted="loadData"
        @compform-drawer-closed="compUuid = 0"
        @compform-saved="loadData"
      />
      <comp-search-drawer
        v-model="openSearchDrawer"
        :comp-rate-list="compRateList"
        :coupon-yn-list="couponYnList"
        :comp-group-list="compGroupList"
        :comp-sector-list="compSectorList"
        :rowsearchdata="searchdata"
        @compsearch-reset="resetLoadData"
        @compsearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { defineComponent, ref } from 'vue';
//Lang
import i18n from 'src/i18n';
// Table
import { GridOptions } from 'ag-grid-community';
import { CompFormTableConfig } from 'src/views/comp/CompFormTableConfig';
import TableComp from 'src/components/table/TableComp.vue';
import { TableHelper } from 'src/components/table/TableHelper';
// Service
import { CompService } from 'src/services/CompService';
import { CdcdService } from 'src/services/CdcdService';
import { ReportService } from 'src/services/CompReportService';
//Type
import { CompForm } from 'src/types/CompForm';
import { CompSearchForm } from 'src/types/CompSearchForm';
import { CompListReport } from 'src/types/CompReport';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
// Helpers
import ReportHelper from 'src/utils/helpers/ReportHelper';
// Drawer
import CompFormDrawer from 'src/views/comp/CompFormDrawer.vue';
//import {CdcdForm} from "src/types/CdcdForm";
import { CdcdSearchForm } from 'src/types/CdcdSearchForm';
import CompSearchDrawer from 'src/views/comp/CompSearchDrawer.vue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';

export default defineComponent({
  name: 'CompForm',
  components: {
    TableComp,
    CompFormDrawer,
    CompSearchDrawer,
  },
  setup() {
    const locale = i18n.global.locale.value;
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = CompFormTableConfig.frameworkComponents;
    const columns = CompFormTableConfig.getColumns(locale);
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const reportHeaderMap = CompFormTableConfig.reportHeaderMap;
    const data = ref<CompForm[]>([]);
    const searchdefaultdata = ref<CompSearchForm>(new CompSearchForm());
    const searchdata = ref<CompSearchForm>(new CompSearchForm());
    const compformGrid = ref();
    const compUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({ columnApi: undefined });
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    const showexportbutton = ref<boolean>(false);

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

      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('COMP_WU');
      if (store.getters.currentUserHasApplicationPermission('COMP_R')) {
        CompService.getAllComp(searchdata.value).then((response) => {
          loading.value = false;
          compUuid.value = 0;
          if (response) {
            data.value = response;
          }
          // if (gridOptions.value && gridOptions.value.columnApi) {
          //   gridOptions.value.columnApi.applyColumnState({
          //     state: CompFormTableConfig.defaultSortModel,
          //   });
          // }
        });
        //printcodeValue();
      }
    }

    function resetLoadData() {
      searchdata.value = new CompSearchForm();
    }

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
      const exportFilename = i18n.global.t('compreport');
      const listReport: CompListReport = {
        title: '',
        sort: compformGrid.value.getExportSortString(reportHeaderMap),
        filter: compformGrid.value.getExportFilterString(reportHeaderMap),
        data: compformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportEXCELData(
        exportFilename,
        listReport,
        ReportService.getCompListReport
      );
    }
    /* Detail Export PDF */
    function printAction() {
      const exportFilename = i18n.global.t('compreport');
      const listReport: CompListReport = {
        title: '',
        sort: compformGrid.value.getExportSortString(reportHeaderMap),
        filter: compformGrid.value.getExportFilterString(reportHeaderMap),
        data: compformGrid.value.getFilteredRows(),
      };
      ReportHelper.exportPDFData(
        exportFilename,
        listReport,
        ReportService.getCompListPdfReport
      );
    }

    const compSectorList = ref<SelectOption[]>([]);
    const compRateList = ref<SelectOption[]>([]);
    const hotelRateList = ref<SelectOption[]>([]);
    const couponYnList = ref<SelectOption[]>([]);
    const compGroupList = ref<SelectOption[]>([]);
    const compOptionRateList = ref<SelectOption[]>([]);
    const pkgRateList = ref<SelectOption[]>([]);
    const pkgOnlyRateList = ref<SelectOption[]>([]);
    const honeymoonRateList = ref<SelectOption[]>([]);
    const honeymoonOnlyRateList = ref<SelectOption[]>([]);
    const restaurantRateList = ref<SelectOption[]>([]);
    const rentcarRateList = ref<SelectOption[]>([]);

    // Loading options lists
    loadOptionsList(22, compSectorList, locale);
    loadOptionsList(652, compRateList, locale);
    loadOptionsList(239, hotelRateList, locale);
    loadOptionsList(515, couponYnList, locale);
    loadOptionsList(518, compGroupList, locale);
    loadOptionsList(240, compOptionRateList, locale);
    loadOptionsList(353, pkgRateList, locale);
    loadOptionsList(354, pkgOnlyRateList, locale);
    loadOptionsList(355, honeymoonRateList, locale);
    loadOptionsList(356, honeymoonOnlyRateList, locale);
    loadOptionsList(242, restaurantRateList, locale);
    loadOptionsList(241, rentcarRateList, locale);

    loadPrepaidHowListOptions();

    /*Prepaid How List*/
    const prepaidHowList = ref<SelectOption[]>([]);
    function loadPrepaidHowListOptions() {
      let searchReq: CdcdSearchForm = {
        searchCdCdUuid: 0,
        searchMngNameKor: '',
        searchMngNameEng: '',
        searchCardNumber: '',
      };
      CdcdService.getAll(searchReq).then((response) => {
        if (response) {
          prepaidHowList.value = response.map(
            (x) =>
              new SelectOption(
                locale === 'en' ? x.mngNameEng : x.mngNameKor,
                x.cdCdUuid
              )
          );
        }
      });
    }

    return {
      t: i18n.global.t,
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
      exportAction,
      printAction,
      frameworkComponents,
      compformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetLoadData,
      filterNumber,
      showinsertbutton,
      showexportbutton,
      compRateList,
      couponYnList,
      compGroupList,
      compSectorList,
      compOptionRateList,
      pkgRateList,
      pkgOnlyRateList,
      rentcarRateList,
      restaurantRateList,
      honeymoonOnlyRateList,
      honeymoonRateList,
      prepaidHowList,
      hotelRateList,
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
