<template>
  <div id="company-emp-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emp-title">
            <q-icon name="people" class="q-mr-sm"></q-icon
            >{{ t('compEmp') }}</span
          >
        </div>
        <!-- <q-btn
          class="q-mr-xs"
          v-if="showinsertbutton"
          icon="add"
          :label="t('addnew')"
          style="color: darkgreen"
          @click="createAction"
        /> -->
      </div>
      <div id="company-emp-list-grid-container" class="row grow-1">
        <table-comp
          id="company-emp-list-grid"
          :column-defs="columns"
          :context="context"
          :framework-components="frameworkComponents"
          :loading="loading"
          :pagination="true"
          :overlay-loading-template="overlayLoadingTemplate"
          :gridHeight="'600'"
          :row-data="data"
          :open-action="openAction"
          row-selection="single"
          @grid-ready="loadData"
          ref="CompEmpFormGrid"
        />
      </div>
    </q-page>
  </div>
</template>
<script lang="ts">
import { ref, defineComponent } from 'vue';
//Lang
import i18n from 'src/i18n';
// Table
import { GridOptions } from 'ag-grid-community';
import { TableHelper } from 'src/components/table/TableHelper';
import TableComp from 'src/components/table/TableComp.vue';
import { CompEmpFormTableConfig } from 'src/views/comp/CompEmpFormTableConfig';
// Service
import { EmpService } from 'src/services/EmpService';
// Type
import { EmpForm } from 'src/types/EmpForm';
// Store
import store from 'src/store';
// Drawer

export default defineComponent({
  name: 'CompEmpList',
  components: {
    TableComp,
  },
  props: {
    compUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openEmpDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = CompEmpFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      CompEmpFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<EmpForm[]>([]);
    const empFormGrid = ref();
    const empUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    const openAction = (value: number) => {
      empUuid.value = value;
      openEmpDrawer.value = true;
    };
    const createAction = () => {
      empUuid.value = 0;
      openEmpDrawer.value = true;
    };

    const loadData = () => {
      openEmpDrawer.value = false;
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('COMP_WU');
      if (store.getters.currentUserHasApplicationPermission('COMP_R')) {
        EmpService.getEmpByComp(props.compUuid).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;
            console.log('data', data.value);
          }
        });
      }
    };

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      empUuid,
      openEmpDrawer,
      openAction,
      createAction,
      empFormGrid,
      overlayLoadingTemplate,
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
#company-emp-list {
  width: 100%;
  height: 720px;
  overflow: hidden;
  #company-emp-list-grid-container {
    height: 600px;

    #company-emp-list-grid {
      height: 600px;
    }
  }
}
.emp-title {
  font-weight: bold;
  font-size: 18px;
  color: darkgreen;
}
</style>
