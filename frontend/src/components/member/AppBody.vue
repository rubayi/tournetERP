<template>
  <div id="memberform">
    <q-page class="q-pa-md column">
      <div class="row q-mb-none">
        <div class="col q-pr-md">
          <span class="part_title text-primary">
            <i class="fas fa-users q-ml-xs q-mr-md"></i>
            Manage Members
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
            label="NEW MEMBER"
            outline
            @click="createAction"
          />
        </div>
      </div>
      <div id="memberform-grid-container" class="row grow-1">
        <div class="col-12">
          <table-comp
            id="memberform-grid"
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
            ref="memberformGrid"
          />
        </div>
      </div>
      <member-form-drawer
        v-model="openDrawer"
        :mem-i-d="memID"
        @memberform-deleted="loadData"
        @memberform-drawer-closed="memID = 0"
        @memberform-saved="loadData"
      />
      <member-search-drawer
        v-model="openSearchDrawer"
        :rowsearchdata="searchdata"
        @membersearch-reset="resetloadData"
        @membersearch-saved="loadData"
      />
    </q-page>
  </div>
</template>

<script lang="ts">
import _ from "lodash";
import { defineComponent, ref, watch } from "vue";
//Component
import TableComp from "@/components/table/TableComp.vue";
import { TableHelper } from "@/components/table/TableHelper";
//Table
import { GridOptions } from "ag-grid-community";
import { MemberFormTableConfig } from "@/views/member/MemberFormTableConfig";

//Service
import { MemberService } from "@/services/MemberService";
//Type
import { MemberForm } from "@/types/MemberForm";
import { MemberSearchForm } from "@/types/MemberSearchForm";
// Layout
import MemberFormDrawer from "@/views/member/MemberFormDrawer.vue";
import MemberSearchDrawer from "@/views/member/MemberSearchDrawer.vue";
// Store
import store from "@/store";
export default defineComponent({
  name: "MemberForm",
  components: {
    TableComp,
    MemberFormDrawer,
    MemberSearchDrawer,
  },
  setup() {
    const openDrawer = ref<boolean>(false);
    const openSearchDrawer = ref<boolean>(false);
    const loading = ref<boolean>(false);
    const frameworkComponents = MemberFormTableConfig.frameworkComponents;
    const columns = MemberFormTableConfig.columns;
    const overlayLoadingTemplate = TableHelper.loadingOverlay;
    const data = ref<MemberForm[]>([]);
    const searchdefaultdata = ref<MemberSearchForm>(new MemberSearchForm());
    const searchdata = ref<MemberSearchForm>(new MemberSearchForm());
    const memberformGrid = ref();
    const memID = ref<number>(0);
    const gridOptions = ref<GridOptions>({});
    var filterNumber = ref<number>(0);
    const showinsertbutton = ref<boolean>(false);
    /* List */
    function loadData() {
      loading.value = true;
      filterNumber.value = 0;
      if (
        !_.isEqual(
          searchdata.value.searchComid,
          searchdefaultdata.value.searchComid
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchName,
          searchdefaultdata.value.searchName
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchfirstname,
          searchdefaultdata.value.searchfirstname
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchlastname,
          searchdefaultdata.value.searchlastname
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchposition,
          searchdefaultdata.value.searchposition
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchemail,
          searchdefaultdata.value.searchemail
        )
      ) {
        filterNumber.value++;
      }
      if (
        !_.isEqual(
          searchdata.value.searchphone,
          searchdefaultdata.value.searchphone
        )
      ) {
        filterNumber.value++;
      }
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission("USER_A");
      if (store.getters.currentUserHasApplicationPermission("USER_R")) {
        MemberService.getAll(searchdata.value).then((response) => {
          loading.value = false;
          memID.value = 0;
          data.value = response;
          if (gridOptions.value.columnApi) {
            gridOptions.value.columnApi.applyColumnState({
              state: MemberFormTableConfig.defaultSortModel,
            });
          }
        });
      }
    }
    function resetloadData() {
      searchdata.value = new MemberSearchForm();
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
      memID.value = 0;
      openDrawer.value = true;
    }

    /* Edit */
    function openAction(value: number) {
      memID.value = value;
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
      memID,
      openDrawer,
      memberformGrid,
      openSearchDrawer,
      createAction,
      openAction,
      filterAction,
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
#memberform {
  #memberform-grid-container {
    flex-grow: 1;

    #memberform-grid {
      height: 100%;
    }
  }
}
</style>
