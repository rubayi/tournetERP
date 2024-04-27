<template>
  <div id="codeform">
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
      <div id="code-form-grid-container" class="row">
        <div class="col-12">
          <table-comp
            id="code-form-grid"
            :column-defs="columns"
            :context="context"
            :loading="loading"
            :overlay-loading-template="overlayLoadingTemplate"
            :row-data="data"
            row-selection="single"
            @grid-ready="loadData"
            ref="codeformGrid"
          />
        </div>
      </div>
      <code-form-drawer
        v-model="openDrawer"
        :code-seq="codeUuid"
        @codeform-deleted="loadData"
        @codeform-drawer-closed="codeUuid = 0"
        @codeform-saved="loadData"
      />
      <code-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @codesearch-reset="resetloadData"
        @codesearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from "lodash";
import { defineComponent, ref} from "vue";

// Table
import { GridOptions } from "ag-grid-community";
import { CodeFormTableConfig } from "src/views/code/CodeFormTableConfig";
import TableComp from "src/components/table/TableComp.vue";
import { TableHelper } from "src/components/table/TableHelper";

// Service
import { CodeService } from "src/services/CodeService";
//Type
import { CodeForm } from "src/types/CodeForm";
import { CodeSearchForm } from "src/types/CodeSearchForm";

// Drawer
import CodeFormDrawer from "src/views/code/CodeFormDrawer.vue";
import CodeSearchDrawer from "src/views/code/CodeSearchDrawer.vue";

export default defineComponent({
  name: "CodeForm",
  components: {
    TableComp,
    CodeFormDrawer,
    CodeSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    //const frameworkComponents = CodeFormTableConfig.frameworkComponents;
    const columns = CodeFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<CodeForm[]>([]);
    const searchdefaultdata = ref<CodeSearchForm>(new CodeSearchForm());
    const searchdata = ref<CodeSearchForm>(new CodeSearchForm());
    const codeformGrid = ref();
    const codeUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchUprCodeUuid,
          searchdefaultdata.value.searchUprCodeUuid
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCodeKr,
          searchdefaultdata.value.searchCodeKr
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value = true;
       // store.getters.currentUserHasApplicationPermission("COD_A");
      // if (store.getters.currentUserHasApplicationPermission("COD_R")) {

        CodeService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          codeUuid.value = 0;

          if (response) {
            data.value = response;
          }
          // if (gridOptions.value.columnApi) {
          //   gridOptions.value.columnApi.applyColumnState({
          //     state: CodeFormTableConfig.defaultSortModel,
          //   });
          // }
        });
      // }

    }
    function resetloadData() {
      searchdata.value = new CodeSearchForm();
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
      codeUuid.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      codeUuid.value = value;
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
      codeUuid,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      codeformGrid,
      //frameworkComponents,
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
#codeform {
  #codeform-grid-container {
    flex-grow: 1;

    #codeform-grid {
      height: 100%;
    }
  }
}
</style>
