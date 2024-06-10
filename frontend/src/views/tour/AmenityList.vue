<template>
  <div id="amenity-list">
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
      <div id="amenity-list-grid-container" class="row grow-1">
        <table-comp
          id="tour-amenity-list-grid"
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
          ref="AmenityFormGrid"
        />
      </div>
      <amenity-drawer
        :amenity-drawer="openAmenityDrawer"
        :amenity-seq="infoUuid"
        :tour-uuid="tourUuid"
        @infoform-deleted="loadData"
        @infoform-drawer-closed="infoUuid = 0"
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
import { AmenityFormTableConfig } from 'src/views/tour/AmenityFormTableConfig';
// Service
import { AmenityService } from 'src/services/AmenityService';
// Type
import { AmenityForm } from 'src/types/AmenityForm';
import { AmenitySearchForm } from 'src/types/AmenitySearchForm';
// Store
import store from 'src/store';
// Drawer
import AmenityDrawer from 'src/views/tour/AmenityDrawer.vue';

export default defineComponent({
  name: 'AmenityList',
  components: {
    TableComp,
    AmenityDrawer,
  },
  props: {
    tourUuid: {
      type: Number,
      default: 0,
    },
  },

  setup(props) {
    const locale = i18n.global.locale.value;
    const openAmenityDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = AmenityFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      AmenityFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<AmenityForm[]>([]);
    const searchdata = ref<AmenitySearchForm>(new AmenitySearchForm());
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
        AmenityService.selectAmenities(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;

          }
        });
      }
    };
    function createAction() {
      infoUuid.value = 0;
      openAmenityDrawer.value = true;
    }
    function openAction(value: number) {
      infoUuid.value = value;
      openAmenityDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      infoUuid,
      openAmenityDrawer,
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
#amenity-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #amenity-list-grid-container {
    height: 300px;

    #tour-amenity-list-grid {
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
