<template>
  <div id="hotel-room-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="tips_and_updates" class="q-mr-sm"></q-icon
            >{{ t('hotelRooms') }}</span
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
      <div id="hotel-room-list-grid-container" class="row grow-1">
        <table-comp
          id="hotel-room-list-grid"
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
          ref="hotelRoomFormGrid"
        />
      </div>
      <hotel-room-drawer
        :hotel-room-drawer="openHotelRoomDrawer"
        :room-seq="roomUuid"
        :service-uuid="serviceUuid"
        @hotelroomform-deleted="loadData"
        @hotelroomform-drawer-closed="roomUuid = 0"
        @hotelroomform-saved="loadData"
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
import { HotelRoomTableConfig } from 'src/views/tour/HotelRoomTableConfig';
// Service
import { HotelRoomService } from 'src/services/HotelRoomService';
// Type
import { HotelRoomForm } from 'src/types/HotelRoomForm';
import { HotelRoomSearchForm } from 'src/types/HotelRoomSearchForm';
// Store
import store from 'src/store';
// Drawer
import HotelRoomDrawer from 'src/views/tour/HotelRoomDrawer.vue';

export default defineComponent({
  name: 'HotelRoomList',
  components: {
    TableComp,
    HotelRoomDrawer,
  },
  props: {
    serviceUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openHotelRoomDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = HotelRoomTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      HotelRoomTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<HotelRoomForm[]>([]);
    const searchdata = ref<HotelRoomSearchForm>(new HotelRoomSearchForm());
    const infoFormGrid = ref();
    const roomUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);
    const hotelRoomFormGrid = ref();

    const loadData = () => {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        searchdata.value.searchServiceUuid = props.serviceUuid;
        HotelRoomService.getHotelRoomList(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;
          }
        });
      }
    };
    function createAction() {
      roomUuid.value = 0;
      openHotelRoomDrawer.value = true;
    }
    function openAction(value: number) {
      roomUuid.value = value;
      openHotelRoomDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      hotelRoomFormGrid,
      roomUuid,
      openHotelRoomDrawer,
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
#hotel-room-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #hotel-room-list-grid-container {
    height: 300px;

    #hotel-room-list-grid {
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
