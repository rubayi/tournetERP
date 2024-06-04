<template>
  <div id="company-contact-list">
    <q-page class="q-pt-md">
      <div class="row justify-end q-pb-sm">
        <div class="col q-pr-md q-mt-sm">
          <span class="emer-title">
            <q-icon name="phone" class="q-mr-sm"></q-icon
            >{{ t('tourContacts') }}</span
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
      <div id="company-contact-list-grid-container" class="row grow-1">
        <table-comp
          id="tour-contact-list-grid"
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
          ref="TourContactFormGrid"
        />
      </div>
      <tour-contact-drawer
        :contact-drawer="openContactDrawer"
        :tour-seq="contactUuid"
        :tour-uuid="tourUuid"
        @contactform-deleted="loadData"
        @contactform-contactDrawer-closed="contactUuid = 0"
        @contactform-saved="loadData"
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
import { TourContactFormTableConfig } from 'src/views/tour/TourContactFormTableConfig';
// Service
import { TourContactService } from 'src/services/TourContactService';
// Type
import { TourContactForm } from 'src/types/TourContactForm';
import { TourContactSearchForm } from 'src/types/TourContactSearchForm';
// Store
import store from 'src/store';
// Drawer
import TourContactDrawer from 'src/views/tour/TourContactDrawer.vue';

export default defineComponent({
  name: 'TourContactList',
  components: {
    TableComp,
    TourContactDrawer,
  },
  props: {
    tourUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props) {
    const locale = i18n.global.locale.value;
    const openContactDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const columns = TourContactFormTableConfig.getColumns(locale);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const frameworkComponents: { [key: string]: any } =
      TourContactFormTableConfig.frameworkComponents;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<TourContactForm[]>([]);
    const searchdata = ref<TourContactSearchForm>(new TourContactSearchForm());
    const contactFormGrid = ref();
    const contactUuid = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    const showinsertbutton = ref<boolean>(false);

    const loadData = () => {
      loading.value = true;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        searchdata.value.tourUuid = props.tourUuid;
        TourContactService.getTourContactList(searchdata.value).then((response) => {
          loading.value = false;
          if (response) {
            data.value = response;
            //console.log('data', data.value);
          }
        });
      }
    };
    function createAction() {
      contactUuid.value = 0;
      openContactDrawer.value = true;
    }
    function openAction(value: number) {
      contactUuid.value = value;
      openContactDrawer.value = true;
    }

    return {
      t: i18n.global.t,
      gridOptions,
      data,
      loadData,
      loading,
      columns,
      contactUuid,
      openContactDrawer,
      createAction,
      openAction,
      contactFormGrid,
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
#company-contact-list {
  width: 100%;
  height: 420px;
  overflow: hidden;
  #company-contact-list-grid-container {
    height: 300px;

    #company-contact-list-grid {
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
