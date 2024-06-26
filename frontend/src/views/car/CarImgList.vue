<template>
  <div id="car-img-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="phone" class="q-mr-sm"></q-icon
            >{{ t('compCarImgs') }}</span
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
      <div id="car-img-list-grid-container" class="row grow-1">
        <table-comp
          id="car-img-list-grid"
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
          ref="CompCarImgFormGrid"
        />
      </div>
      <car-img-drawer
        :contact-drawer="openCarImgDrawer"
        :car-img-seq="carImgUuid"
        :comp-uuid="trnCarUuid"
        @carimgform-deleted="loadData"
        @carimgform-contactDrawer-closed="carImgUuid = 0"
        @carimgform-saved="loadData"
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
import { CarImgFormTableConfig } from 'src/views/car/CarImgFormTableConfig';
// Service
import { CarImgService } from 'src/services/CarImgService';
// Type
import { CarImgForm } from 'src/types/CarImgForm';
import { CarImgSearchForm } from 'src/types/CarImgSearchForm';
// Store
import store from 'src/store';
// Drawer
import CarImgDrawer from 'src/views/car/CarImgDrawer.vue';

export default defineComponent({
  name: 'CarImgList',
  components: {
    TableComp,
    CarImgDrawer,
  },
  props: {
    trnCarUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openCarImgDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = CarImgFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      CarImgFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<CarImgForm[]>([]);
    const searchdata = ref<CarImgSearchForm>(new CarImgSearchForm());
    const carImgFormGrid = ref();
    const carImgUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    const loadData = () => {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CAR_WU');
      if (store.getters.currentUserHasApplicationPermission('CAR_R')) {
        searchdata.value.trnCarUuid = props.trnCarUuid;
        CarImgService.getCarImgList(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;
            //console.log('data', data.value);
          }
        });
      }
    };
    function createAction() {
      carImgUuid.value = 0;
      openCarImgDrawer.value = true;
    }
    function openAction(value: number) {
      carImgUuid.value = value;
      openCarImgDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      carImgUuid,
      openCarImgDrawer,
      createAction,
      openAction,
      carImgFormGrid,
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
#car-img-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #car-img-list-grid-container {
    height: 300px;

    #car-img-list-grid {
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
