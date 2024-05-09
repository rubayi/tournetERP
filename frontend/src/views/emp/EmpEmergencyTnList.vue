<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div id="officeform">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="phone" class="q-mr-sm"></q-icon
            >{{ t('emerContact') }}</span
          >
        </div>
        <q-btn
          v-if="showinsertbutton"
          icon="add"
          label="New Emergency Contact"
          style="color: darkgreen"
          @click="createAction"
        />
      </div>
      <div id="officeform-grid-container" class="row">
        <table-comp
          id="emp-emergency-form-grid"
          :column-defs="columns"
          :context="context"
          :framework-components="frameworkComponents"
          :loading="loading"
          :overlay-loading-template="overlayLoadingTemplate"
          :pagination="false"
          :gridHeight="'300'"
          :row-data="data"
          :open-action="openAction"
          row-selection="single"
          @grid-ready="loadData"
          ref="EmpEmergencyFormGrid"
        />
      </div>
      <emp-emergency-drawer
        v-model="openDrawer"
        :emp-seq="emergencyUuid"
        @emerform-deleted="loadData"
        @emerform-drawer-closed="emergencyUuid = 0"
        @emerform-saved="loadData"
      />
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
import { EmpEmergencyFormTableConfig } from 'src/views/emp/EmpEmergencyFormTableConfig';
// Service
import { EmergencyService } from 'src/services/EmergencyService';
// Type
import { EmergencyForm } from 'src/types/EmergencyForm';
import { EmergencySearchForm } from 'src/types/EmergencySearchForm';
// Store
import store from 'src/store';
// Drawer
import EmpEmergencyDrawer from 'src/views/emp/EmpEmergencyDrawer.vue';

export default defineComponent({
  name: 'EmpEmergencyTnList',
  components: {
    TableComp,
    EmpEmergencyDrawer,
  },
  setup() {
    const locale = i18n.global.locale.value;
    const openDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = EmpEmergencyFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      EmpEmergencyFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<EmergencyForm[]>([]);
    const searchdata = ref<EmergencySearchForm>(new EmergencySearchForm());
    const empEmergencyFormGrid = ref();
    const emergencyUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    function loadData() {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CODE_W');
      if (store.getters.currentUserHasApplicationPermission('CODE_R')) {
        EmergencyService.getEmergencyList(searchdata.value).then((response) => {
          loading.value = false;
          emergencyUuid.value = 0;
          if (response) {
            data.value = response;
          }
        });
      }
    }
    function createAction() {
      emergencyUuid.value = 0;
      openDrawer.value = true;
    }
    function openAction(value: number) {
      emergencyUuid.value = value;
      openDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      emergencyUuid,
      openDrawer,
      createAction,
      openAction,
      empEmergencyFormGrid,
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
#officeform {
  width: 100%;
  #officeform-grid-container {
    height: 300px;

    #officeform-grid {
      height: 300px;
    }
  }
}
.emer-title {
  font-weight: bold;
  font-size: 18px;
  color: darkgreen;
}
</style>
