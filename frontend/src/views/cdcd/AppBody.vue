<template>
  <div id="codeform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="fas fa-cogs q-ml-xs q-mr-md"></i>
            Manage Credit Card
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
            label="NEW CARD"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="code-form-grid-container" class="row">
        <div class="col-12">
          <table-comp
            id="cdcd-form-grid"
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
            ref="cdcdformGrid"
          />
        </div>
      </div>
      <cdcd-form-drawer
        v-model="openDrawer"
        :code-seq="codeUuid"
        @codeform-deleted="loadData"
        @codeform-drawer-closed="codeUuid = 0"
        @codeform-saved="loadData"
      />
      <cdcd-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @codesearch-reset="resetloadData"
        @codesearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { defineComponent, ref } from 'vue';

// Table
import { GridOptions } from 'ag-grid-community';
import { CdcdFormTableConfig } from 'src/views/cdcd/CdcdFormTableConfig';
import TableComp from 'src/components/table/TableComp.vue';
import { TableHelper } from 'src/components/table/TableHelper';

// Service
import { CdcdService } from 'src/services/CdcdService';
//Type
import { CdcdForm } from 'src/types/CdcdForm';
import { CdcdSearchForm } from 'src/types/CdcdSearchForm';
// Store
import store from 'src/store';
// Drawer
import CdcdFormDrawer from 'src/views/cdcd/CdcdFormDrawer.vue';
import CdcdSearchDrawer from 'src/views/cdcd/CdcdSearchDrawer.vue';

export default defineComponent({
  name: 'CdcdForm',
  components: {
    TableComp,
    CdcdFormDrawer,
    CdcdSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = CdcdFormTableConfig.columns;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      CdcdFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<CdcdForm[]>([]);
    const searchdefaultdata = ref<CdcdSearchForm>(new CdcdSearchForm());
    const searchdata = ref<CdcdSearchForm>(new CdcdSearchForm());
    const cdcdformGrid = ref();
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
          searchdata.value.searchCdCdUuid,
          searchdefaultdata.value.searchCdCdUuid
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchMngNameKor,
          searchdefaultdata.value.searchMngNameKor
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchCardNumber,
          searchdefaultdata.value.searchCardNumber
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CODE_W');
      if (store.getters.currentUserHasApplicationPermission('CODE_R')) {
        CdcdService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          codeUuid.value = 0;

          if (response) {
            data.value = response;
          }

        });
      }
    }
    function resetloadData() {
      searchdata.value = new CdcdSearchForm();
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
      cdcdformGrid,
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
#codeform {
  #codeform-grid-container {
    flex-grow: 1;

    #codeform-grid {
      height: 100%;
    }
  }
}
</style>
