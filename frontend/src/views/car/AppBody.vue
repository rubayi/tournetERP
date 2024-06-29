<template>
  <div id="carform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-sm">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <q-icon
              name="directions_car"
              class="q-ml-xs q-mr-sm"
              size="sm"
            ></q-icon>
            {{ t('manageCar') }}
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
            id="car-form-grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :open-action="openAction"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="CarFormGrid"
          />
        </div>
      </div>
      <car-form-drawer
        v-model="openDrawer"
        :car-type-list="carTypeList"
        :car-brand-list="carBrandList"
        :car-manager-list="carManagerList"
        :car-seq="trnCarUuid"
        @carForm-deleted="loadData"
        @carForm-drawer-closed="trnCarUuid = 0"
        @carForm-saved="loadData"
      />
      <car-search-drawer
        v-model="openSearchDrawer"
        :car-brand-list="carBrandList"
        :car-type-list="carTypeList"
        :rowsearchdata="searchdata"
        @carSearch-reset="resetLoadData"
        @carSearch-saved="loadData"
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
import { CarFormTableConfig } from 'src/views/car/CarFormTableConfig';
import TableComp from 'src/components/table/TableComp.vue';
import { TableHelper } from 'src/components/table/TableHelper';
// Service
import { CarService } from 'src/services/CarService';
import { ReportService } from 'src/services/CompReportService';
//Type
import { CarForm } from 'src/types/CarForm';
import { CarSearchForm } from 'src/types/CarSearchForm';
import { CompListReport } from 'src/types/CompReport';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
// Helpers
import ReportHelper from 'src/utils/helpers/ReportHelper';
// Drawer
import CarFormDrawer from 'src/views/car/CarFormDrawer.vue';
import CarSearchDrawer from 'src/views/car/CarSearchDrawer.vue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import { EmpSearchForm } from 'src/types/EmpSearchForm';
import { EmpService } from 'src/services/EmpService';

export default defineComponent({
  name: 'CarForm',
  components: {
    TableComp,
    CarFormDrawer,
    CarSearchDrawer,
  },
  setup() {
    const locale = i18n.global.locale.value;
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = CarFormTableConfig.frameworkComponents;
    const columns = CarFormTableConfig.getColumns(locale);
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const reportHeaderMap = CarFormTableConfig.reportHeaderMap;
    const data = ref<CarForm[]>([]);
    const searchdefaultdata = ref<CarSearchForm>(new CarSearchForm());
    const searchdata = ref<CarSearchForm>(new CarSearchForm());
    const CarFormGrid = ref();
    const trnCarUuid = ref<number>(0);
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
          searchdata.value.searchCarBrand,
          searchdefaultdata.value.searchCarBrand
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCarManager,
          searchdefaultdata.value.searchCarManager
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCarVin,
          searchdefaultdata.value.searchCarVin
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCarType,
          searchdefaultdata.value.searchCarType
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchPeople,
          searchdefaultdata.value.searchPeople
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchTrnCarUuid,
          searchdefaultdata.value.searchTrnCarUuid
        )
      ) {
        filterNumber.value++;
      }

      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CAR_WU');
      if (store.getters.currentUserHasApplicationPermission('CAR_R')) {
        CarService.getAllCar(searchdata.value).then((response) => {
          loading.value = false;
          trnCarUuid.value = 0;
          if (response) {
            data.value = response;
          }
        });
      }
    }

    function resetLoadData() {
      searchdata.value = new CarSearchForm();
    }

    /* New */
    function createAction() {
      trnCarUuid.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      trnCarUuid.value = value;
      openDrawer.value = true;
    }

    /* Detail Filter */
    function filterAction() {
      openSearchDrawer.value = true;
    }

    /* Detail Export PDF */
    function exportAction() {
      const exportFilename = i18n.global.t('carreport');
      const listReport: CompListReport = {
        title: '',
        sort: CarFormGrid.value.getExportSortString(reportHeaderMap),
        filter: CarFormGrid.value.getExportFilterString(reportHeaderMap),
        data: CarFormGrid.value.getFilteredRows(),
      };
      ReportHelper.exportEXCELData(
        exportFilename,
        listReport,
        ReportService.getCompListReport
      );
    }
    /* Detail Export PDF */
    function printAction() {
      const exportFilename = i18n.global.t('carreport');
      const listReport: CompListReport = {
        title: '',
        sort: CarFormGrid.value.getExportSortString(reportHeaderMap),
        filter: CarFormGrid.value.getExportFilterString(reportHeaderMap),
        data: CarFormGrid.value.getFilteredRows(),
      };
      ReportHelper.exportPDFData(
        exportFilename,
        listReport,
        ReportService.getCompListPdfReport
      );
    }

    const carTypeList = ref<SelectOption[]>([]);
    const carBrandList = ref<SelectOption[]>([]);

    // Loading options lists
    loadOptionsList(544, carTypeList, locale);
    loadOptionsList(545, carBrandList, locale);

    /*Prepaid How List*/
    const carManagerList = ref<SelectOption[]>([]);
    loadCarManagerListOptions();
    function loadCarManagerListOptions() {
      let searchReq: EmpSearchForm = {
        searchEmpUuid: 0,
        searchEmpKor: '',
        searchEmpEng: '',
        searchEmpDiv: 157,
        searchEmpStatus: 0,
        searchUsername: '',
      };
      EmpService.selectEmpsByCondition(searchReq).then((response) => {
        carManagerList.value = response.map(
          (x) =>
            new SelectOption(locale === 'en' ? x.empEng : x.empKor, x.empUuid)
        );
      });
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      trnCarUuid,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      exportAction,
      printAction,
      frameworkComponents,
      CarFormGrid,
      overlayLoadingTemplate,
      searchdata,
      resetLoadData,
      filterNumber,
      showinsertbutton,
      showexportbutton,
      carBrandList,
      carTypeList,
      carManagerList,
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
#CarForm {
  #CarForm-grid-container {
    flex-grow: 1;
    #CarForm-grid {
      height: 100%;
    }
  }
}
</style>
