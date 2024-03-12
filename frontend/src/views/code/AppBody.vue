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
      <div id="codeform-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="codeform-grid"
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
            ref="codeformGrid"
          />
        </div>
      </div>
      <code-form-drawer
        v-model="openDrawer"
        :code-seq="codeSeq"
        @codeform-deleted="loadData"
        @codeform-drawer-closed="codeSeq = 0"
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
import { defineComponent, ref, watch } from "vue";

// Table
import { GridOptions } from "ag-grid-community";
import { CodeFormTableConfig } from "@/views/code/CodeFormTableConfig";
import TableComp from "@/components/table/TableComp.vue";
import { TableHelper } from "@/components/table/TableHelper";

// Service
import { CodeService } from "@/services/CodeService";
//Type
import { CodeForm } from "@/types/CodeForm";
import { CodeSearchForm } from "@/types/CodeSearchForm";
// Store
import store from "@/store";
// Drawer
import CodeFormDrawer from "@/views/code/CodeFormDrawer.vue";
import CodeSearchDrawer from "@/views/code/CodeSearchDrawer.vue";

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
    const frameworkComponents = CodeFormTableConfig.frameworkComponents;
    const columns = CodeFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<CodeForm[]>([]);
    const searchdefaultdata = ref<CodeSearchForm>(new CodeSearchForm());
    const searchdata = ref<CodeSearchForm>(new CodeSearchForm());
    const codeformGrid = ref();
    const codeSeq = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchcode1,
          searchdefaultdata.value.searchcode1
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchname,
          searchdefaultdata.value.searchname
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission("COD_A");
      if (store.getters.currentUserHasApplicationPermission("COD_R")) {
        CodeService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          codeSeq.value = 0;
          data.value = response;
          if (gridOptions.value.columnApi) {
            gridOptions.value.columnApi.applyColumnState({
              state: CodeFormTableConfig.defaultSortModel,
            });
          }
        });
      }
    }
    function resetloadData() {
      searchdata.value = new CodeSearchForm();
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
      codeSeq.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      codeSeq.value = value;
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
      codeSeq,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      codeformGrid,
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
#codeform {
  #codeform-grid-container {
    flex-grow: 1;

    #codeform-grid {
      height: 100%;
    }
  }
}
</style>
