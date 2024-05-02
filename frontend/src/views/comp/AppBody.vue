<template>
  <div id="compform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="fas fa-cogs q-ml-xs q-mr-md"></i>
            Manage Code
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
            label="NEW CODE"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="comp-form-grid-container" class="row">
        <div class="col-12">
          <table-comp
            id="comp-form-grid"
            :column-defs="columns"
            :context="context"
            :framework-components="frameworkComponents"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :pagination="true"
            :row-data="data"
            :open-action="openAction"
            row-selection="single"
            @grid-ready="loadData"
            ref="compformGrid"
          />
        </div>
      </div>


    </q-page>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { defineComponent, ref } from 'vue';

// Table
import { GridOptions } from 'ag-grid-community';
import { CompFormTableConfig } from 'src/views/comp/CompFormTableConfig';
import TableComp from 'src/components/table/TableComp.vue';
import { TableHelper } from 'src/components/table/TableHelper';

// Service
import { CodeService } from 'src/services/CodeService';
//Type
import { CompForm } from 'src/types/CompForm';
import { CompSearchForm } from 'src/types/CompSearchForm';
// Store
import store from 'src/store';
import {CompService} from "src/services/CompService";
// Drawer
//import CompFormDrawer from 'src/views/comp/CompFormDrawer.vue';
//import CodeSearchDrawer from 'src/views/comp/CodeSearchDrawer.vue';

export default defineComponent({
  name: 'CompForm',
  components: {
    TableComp,

  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = CompFormTableConfig.columns;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      CompFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<CompForm[]>([]);
    const searchdefaultdata = ref<CompSearchForm>(new CompSearchForm());
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

      showinsertbutton.value =
        showinsertbutton.value = store.getters.currentUserHasApplicationPermission("COMP_W");
      if (store.getters.currentUserHasApplicationPermission("COMP_R")) {
        CompService.getAllComp(searchdata.value).then(
          (response) => {
            loading.value = false;
            compUuid.value = 0;
            data.value = response;
            // if (gridOptions.value.columnApi) {
            //   // gridOptions.value.columnApi.applyColumnState({
            //   //   state: CompFormTableConfig.defaultSortModel,
            //   // });
            // }
          }
        );
        //printcodeValue();
      }
    }
    function resetloadData() {
      searchdata.value = new CompSearchForm();
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
      compformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetloadData,
      filterNumber,
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
#compform {
  #compform-grid-container {
    flex-grow: 1;

    #compform-grid {
      height: 100%;
    }
  }
}
</style>
