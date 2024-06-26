<template>
  <div id="empform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-sm">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <q-icon name="person" class="q-ml-xs q-mr-sm" size="sm"></q-icon
            >{{ t('manageEmployees') }}</span
          >
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
      <div id="emp-form-grid-container" class="row">
        <div class="col-12">
          <table-comp
            id="emp-form-grid"
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
            ref="EmpFormGrid"
          />
        </div>
      </div>
      <emp-form-drawer
        v-model="openDrawer"
        :emp-seq="empUuid"
        @empform-deleted="loadData"
        @empform-drawer-closed="empUuid = 0"
        @empform-saved="loadData"
      />
      <emp-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        :group-names="GroupList"
        @empsearch-reset="resetloadData"
        @empsearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { ref, defineComponent } from 'vue';
//Lang
import i18n from 'src/i18n';
// Table
import { GridOptions } from 'ag-grid-community';
import { TableHelper } from 'src/components/table/TableHelper';
import TableComp from 'src/components/table/TableComp.vue';
import { EmpFormTableConfig } from 'src/views/emp/EmpFormTableConfig';
// Service
import { EmpService } from 'src/services/EmpService';
// Type
import { EmpForm } from 'src/types/EmpForm';
import { EmpSearchForm } from 'src/types/EmpSearchForm';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
// Drawer
import EmpFormDrawer from 'src/views/emp/EmpFormDrawer.vue';
import EmpSearchDrawer from 'src/views/emp/EmpSearchDrawer.vue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';

export default defineComponent({
  name: 'EmpForm',
  components: {
    TableComp,
    EmpFormDrawer,
    EmpSearchDrawer,
  },
  setup() {
    const locale = i18n.global.locale.value;
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = EmpFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      EmpFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<EmpForm[]>([]);
    const searchdefaultdata = ref<EmpSearchForm>(new EmpSearchForm());
    const searchdata = ref<EmpSearchForm>(new EmpSearchForm());
    const empformGrid = ref();
    const empUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);

    function loadData() {
      openDrawer.value = false;
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchEmpUuid,
          searchdefaultdata.value.searchEmpUuid
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchEmpKor,
          searchdefaultdata.value.searchEmpKor
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchEmpEng,
          searchdefaultdata.value.searchEmpEng
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchEmpDiv,
          searchdefaultdata.value.searchEmpDiv
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchEmpStatus,
          searchdefaultdata.value.searchEmpStatus
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchUsername,
          searchdefaultdata.value.searchUsername
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('USER_WU');
      if (store.getters.currentUserHasApplicationPermission('USER_R')) {
        EmpService.selectEmpsByCondition(searchdata.value).then((response) => {
          loading.value = false;

          if (response) {
            data.value = response;
          }
        });
      }
    }
    function resetloadData() {
      searchdata.value = new EmpSearchForm();
    }
    function createAction() {
      empUuid.value = 0;
      openDrawer.value = true;
    }
    function openAction(value: number) {
      empUuid.value = value;
      openDrawer.value = true;
    }
    function filterAction() {
      openSearchDrawer.value = true;
    }

    const GroupList = ref<SelectOption[]>([]);
    loadOptionsList(19, GroupList, locale);

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      empUuid,
      openDrawer,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
      empformGrid,
      overlayLoadingTemplate,
      searchdata,
      resetloadData,
      filterNumber,
      showinsertbutton,
      frameworkComponents,
      GroupList,
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
#EmpForm {
  #EmpForm-grid-container {
    flex-grow: 1;

    #EmpForm-grid {
      height: 100%;
    }
  }
}
</style>
