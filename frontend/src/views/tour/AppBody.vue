<template>
  <div id="tourform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-sm">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <q-icon
              name="credit_card"
              class="q-ml-xs q-mr-sm"
              size="sm"
            ></q-icon>
            {{ t(`manageTour`) }}
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
      <div id="tour-form-grid-container" class="row">
        <div class="col-12">
          <table-comp
            id="tour-form-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            :open-action="openAction"
            row-selection="single"
            @grid-ready="loadData"
            ref="tourformGrid"
          />
        </div>
      </div>
      <tour-form-drawer
        v-model="openDrawer"
        :tour-category ="tourCategory"
        :tour-seq="tourUuid"
        :tour-area-list = "tourAreaList"
        :tour-area-sub-list = "tourAreaSubList"
        :sector-list = "sectorList"
        :prepaid-how-list = "prepaidHowList"
        :company-list = "companyList"
        @tourform-deleted="loadData"
        @tourform-drawer-closed="tourUuid = 0"
        @tourform-saved="loadData"
      />
      <tour-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @toursearch-reset="resetloadData"
        @toursearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { defineComponent, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
//Lang
import i18n from 'src/i18n';
// Table
import { GridOptions } from 'ag-grid-community';
import { TourFormTableConfig } from 'src/views/tour/TourFormTableConfig';
import TableComp from 'src/components/table/TableComp.vue';
import { TableHelper } from 'src/components/table/TableHelper';

// Service
import { TourService } from 'src/services/TourService';
//Type
import { TourForm } from 'src/types/TourForm';
import { TourSearchForm } from 'src/types/TourSearchForm';
// Store
import store from 'src/store';
// Drawer
import TourFormDrawer from 'src/views/tour/TourFormDrawer.vue';
import TourSearchDrawer from 'src/views/tour/TourSearchDrawer.vue';
import {CodeService} from "src/services/CodeService";
import {CodeForm} from "src/types/CodeForm";
import {CodeSearchForm} from "src/types/CodeSearchForm";
import {SelectOption} from "src/types/SelectOption";
import {loadOptionsList} from "src/utils/commoncode/commonCode";
import {CdcdSearchForm} from "src/types/CdcdSearchForm";
import {CdcdService} from "src/services/CdcdService";
import {CompService} from "src/services/CompService";
import {CompSearchForm} from "src/types/CompSearchForm";

export default defineComponent({
  name: 'TourForm',
  components: {
    TableComp,
    TourFormDrawer,
    TourSearchDrawer
  },
  setup() {
    const locale = i18n.global.locale.value;

    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = TourFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      TourFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<TourForm[]>([]);
    const searchdefaultdata = ref<TourSearchForm>(new TourSearchForm());
    const searchdata = ref<TourSearchForm>(new TourSearchForm());
    const tourformGrid = ref();
    const tourUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);

    const sector = ref<string>("");
    const tourCategory = ref<number>(0);

    onMounted(() => {
      const route = useRoute();
      sector.value = route.params.sector as string;
      setSectorAndTourCategory();
    });


    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchTourEng,
          searchdefaultdata.value.searchTourEng
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchTourKor,
          searchdefaultdata.value.searchTourKor
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchTourArea,
          searchdefaultdata.value.searchTourArea
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchTourAreaSub,
          searchdefaultdata.value.searchTourAreaSub
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchPrepaidMethod,
          searchdefaultdata.value.searchPrepaidMethod
        )
      ) {
        filterNumber.value++;
      }

      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');

      if( tourCategory.value){
        searchdata.value.searchTourCategory = tourCategory.value;

        if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
          TourService.getAll(searchdata.value).then((response) => {
            loading.value = false;
            tourUuid.value = 0;

            if (response) {
              data.value = response;
            }
          });
        }
      }
    }
    function resetloadData() {
      searchdata.value = new TourSearchForm();
    }

    // watch(loading, (loading) => {
    //   if (gridOptions.value.api) {
    //     if (loading) {
    //        gridOptions.value.api.showLoadingOverlay();
    //     } else {
    //        gridOptions.value.api.hideOverlay();
    //     }
    //   }
    // });

    /* New */
    function createAction() {
      tourUuid.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      tourUuid.value = value;
      openDrawer.value = true;
    }

    /* Detail Filter */
    function filterAction() {
      openSearchDrawer.value = true;
    }


    const setSectorAndTourCategory = () => {
      const tourCategoryOptions = ref<CodeForm[]>([]);

      const searchReq:CodeSearchForm = {
        searchUprCodeUuid: 3,
        searchCodeLvl: 1,
        searchCodeKr: '',
        searchCodeEn: ''
      }
      CodeService.getAll(searchReq).then((response) => {

        tourCategoryOptions.value = response;

        for (let category of tourCategoryOptions.value) {
          if (category.codeEn.toLowerCase() === sector.value
            || category.codeEn === sector.value) {
            tourCategory.value = category.codeUuid;
            loadData();
            break; // Stop looping once category is found
          }
        }
      });
    };


    const tourAreaList = ref<SelectOption[]>([]);
    const tourAreaSubList = ref<SelectOption[]>([]);
    const sectorList = ref<SelectOption[]>([]);

    loadOptionsList(43, tourAreaList, locale);
    loadOptionsList(183, tourAreaSubList, locale);
    loadOptionsList(22, sectorList, locale);



    /*Prepaid How List*/
    const prepaidHowList = ref<SelectOption[]>([]);
    loadPrepaidHowListOptions();
    function loadPrepaidHowListOptions() {
      let searchReq: CdcdSearchForm = {
        searchCdCdUuid: 0,
        searchMngNameKor: '',
        searchMngNameEng: '',
        searchCardNumber: '',
      };
      CdcdService.getAll(searchReq).then((response) => {
        prepaidHowList.value = response.map(
          (x) =>
            new SelectOption(
              locale === 'en' ? x.mngNameEng : x.mngNameKor,
              x.cdCdUuid
            )
        );
      });
    }

    /*Company List*/
    const companyList = ref<SelectOption[]>([]);
    loadcompanyListOptions();
    function loadcompanyListOptions() {
      let searchReq: CompSearchForm = {
        searchCompSector: 0,
        searchCompKor: '',
        searchCompEng: '',
        searchBeginDt: '',
        searchEndDt: '',
        searchCompMemo: '',
        searchCompUuid: '',
        searchCompRate: '',
        searchCouponYn: 0,
        searchCompGroup: '',
      };
      CompService.getAllComp(searchReq).then((response) => {
        companyList.value = response.map(
          (x) =>
            new SelectOption(
              locale === 'en' ? x.compEng : x.compKor,
              x.compUuid
            )
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
      tourUuid,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      tourformGrid,
      overlayLoadingTemplate,
      tourAreaList,
      tourAreaSubList,
      sectorList,
      prepaidHowList,
      searchdata,
      resetloadData,
      filterNumber,
      showinsertbutton,
      frameworkComponents,
      tourCategory,
      companyList
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
#codeform {
  #codeform-grid-container {
    flex-grow: 1;

    #codeform-grid {
      height: 100%;
    }
  }
}
</style>
