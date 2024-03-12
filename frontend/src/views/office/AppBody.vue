<template>
  <div id="officeform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="far fa-building q-ml-xs q-mr-md"></i>
            Manage Office
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
            label="NEW OFFICE"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="officeform-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="officeform-grid"
            class="ag-theme-alpine grid"
            :column-defs="columns"
            :context="context"
            :framework-components="frameworkComponents"
            :grid-options="gridOptions"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="officeformGrid"
          />
        </div>
      </div>
      <office-form-drawer
        v-model="openDrawer"
        :officeid="officeid"
        @officeform-deleted="loadData"
        @officeform-drawer-closed="officeid = 0"
        @officeform-saved="loadData"
      />
      <office-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @officesearch-reset="resetloadData"
        @officesearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from "lodash";
import { defineComponent, ref, watch } from "vue";

//Table
import { GridOptions } from "ag-grid-community";
import { OfficeFormTableConfig } from "@/views/office/OfficeFormTableConfig";
import TableComp from "@/components/table/TableComp.vue";
import { TableHelper } from "@/components/table/TableHelper";
//Service
import { OfficeService } from "@/services/OfficeService";
//Type
import { OfficeForm } from "@/types/OfficeForm";
import { OfficeSearchForm } from "@/types/OfficeSearchForm";
// Layout
import OfficeFormDrawer from "@/views/office/OfficeFormDrawer.vue";
import OfficeSearchDrawer from "@/views/office/OfficeSearchDrawer.vue";
// Store
import store from "@/store";
export default defineComponent({
  name: "OfficeForm",
  components: {
    TableComp,
    OfficeFormDrawer,
    OfficeSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = OfficeFormTableConfig.frameworkComponents;
    const columns = OfficeFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<OfficeForm[]>([]);
    const searchdefaultdata = ref<OfficeSearchForm>(new OfficeSearchForm());
    const searchdata = ref<OfficeSearchForm>(new OfficeSearchForm());
    const officeformGrid = ref();
    const officeid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchIsland,
          searchdefaultdata.value.searchIsland
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchType,
          searchdefaultdata.value.searchType
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
        store.getters.currentUserHasApplicationPermission("OFFICE_A");
      if (store.getters.currentUserHasApplicationPermission("OFFICE_R")) {
        OfficeService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          officeid.value = 0;
          data.value = response;
          if (gridOptions.value.columnApi) {
            gridOptions.value.columnApi.applyColumnState({
              state: OfficeFormTableConfig.defaultSortModel,
            });
          }
        });
      }
    }
    function resetloadData() {
      searchdata.value = new OfficeSearchForm();
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
      officeid.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      officeid.value = value;
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
      officeid,
      openDrawer,
      officeformGrid,
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
#officeform {
  #officeform-grid-container {
    flex-grow: 1;

    #officeform-grid {
      height: 100%;
    }
  }
}
</style>
