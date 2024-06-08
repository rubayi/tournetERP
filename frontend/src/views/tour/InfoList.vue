<template>
  <div id="info-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="tips_and_updates" class="q-mr-sm"></q-icon
            >{{ t('tourInfos') }}</span
          >
        </div>
        <q-btn
          class="q-mr-xs"
          v-if="showinsertbutton"
          icon="add"
          :label="t('addnew')"
          style="color: darkgreen"
          @click="createAction"
        />
      </div>
      <div id="info-list-grid-container" class="row grow-1">
        <table-comp
          id="tour-info-list-grid"
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
          ref="InfoFormGrid"
        />
      </div>
      <info-drawer
        :info-drawer="openInfoDrawer"
        :tour-seq="infoUuid"
        :tour-uuid="tourUuid"
        @infoform-deleted="loadData"
        @infoform-infoDrawer-closed="infoUuid = 0"
        @infoform-saved="loadData"
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
import { InfoFormTableConfig } from 'src/views/tour/InfoFormTableConfig';
// Service
import { InfoService } from 'src/services/InfoService';
// Type
import { InfoForm } from 'src/types/InfoForm';
import { InfoSearchForm } from 'src/types/InfoSearchForm';
// Store
import store from 'src/store';
// Drawer
import InfoDrawer from 'src/views/tour/InfoDrawer.vue';

export default defineComponent({
  name: 'InfoList',
  components: {
    TableComp,
    InfoDrawer,
  },
  props: {
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openInfoDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = InfoFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      InfoFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<InfoForm[]>([]);
    const searchdata = ref<InfoSearchForm>(new InfoSearchForm());
    const infoFormGrid = ref();
    const infoUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    const loadData = () => {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        searchdata.value.tourUuid = props.tourUuid;
        InfoService.getInfoList(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;
            //console.log('data', data.value);
          }
        });
      }
    };
    function createAction() {
      infoUuid.value = 0;
      openInfoDrawer.value = true;
    }
    function openAction(value: number) {
      infoUuid.value = value;
      openInfoDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      infoUuid,
      openInfoDrawer,
      createAction,
      openAction,
      infoFormGrid,
      overlayLoadingTemplate,
      searchdata,
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
#info-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #info-list-grid-container {
    height: 300px;

    #tour-info-list-grid {
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
