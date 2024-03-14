<template>
  <ag-grid-vue
    class="ag-theme-alpine grid"
    :column-defs="columnDefs"
    :context="context"
    :framework-components="frameworkComponents"
    :overlay-loading-template="overlayLoadingTemplate"
    :overlay-no-rows-template="overlayNoRowsTemplate"
    :pagination="pagination"
    pagination-auto-page-size="auto"
    :pagination-page-size="paginationPageSize"
    :row-data="rowData"
    :row-selection="rowSelection"
    :set-count="100"
    :tooltip-show-delay="0"
    @filter-changed="filterChanged"
    @grid-ready="$emit('grid-ready', $event)"
    @grid-size-changed="refreshTableSizes"
    @selection-changed="selectionChanged"
    @sort-changed="sortChanged"
    ref="agGrid"
  />
</template>

<script lang="ts">
import { AgGridVue } from "ag-grid-vue3";
import { defineComponent, ref, watch } from "vue";
import { ColDef, GridOptions } from "ag-grid-community";
import { useTableApi } from "src/utils/helpers/useTableApi";

export default defineComponent({
  name: "TableComp",
  components: { AgGridVue },
  props: {
    columnDefs: {
      type: Object as () => Array<ColDef>,
      default: Object as () => [],
    },
    context: {
      type: Object,
      default: () => this,
    },
    defaultColDef: {
      type: Object as () => ColDef,
      default: () => ({}),
    },
    defaultFilterValues: {
      type: Object,
      default: null,
    },
    defaultSortModel: {
      type: Array,
      default: null,
    },
    frameworkComponents: {
      type: Object as () => {
        [p: string]: {
          new (): never;
        };
      },
      default: () => ({}),
    },
    gridOptions: {
      type: Object as () => GridOptions,
      default: () => ({}),
    },
    loading: {
      type: Boolean,
      default: false,
    },
    overlayLoadingTemplate: {
      type: String,
      default:
        '<span class="ag-overlay-loading-center">Please wait while your rows are loading</span>',
    },
    overlayNoRowsTemplate: {
      type: String,
      default:
        '<span class="ag-overlay-loading-center">There are no rows to show</span>',
    },
    pagination: {
      type: Boolean,
      default: true,
    },
    paginationPageSize: {
      type: Number,
      default: 18,
    },
    rowData: {
      type: Array,
      default: () => [],
    },
    rowSelection: {
      type: String,
      default: "none",
    },
  },
  emits: ["grid-ready", "filterChanged", "selectionChanged", "sortChanged"],
  setup(props, { emit }) {
    const internalGridOptions = ref<GridOptions>({});

    watch(
      () => props.rowData,
      (newVal) => {
        if (internalGridOptions.value.api && newVal) {
          //internalGridOptions.value.api.setRowData(newVal);
        }
      },
      { immediate: true, deep: true }
    );

    const {
      filterChanged,
      loadFilterModel,
      getFilterModel,
      getFilterInstance,
      setFloatingFiltersHeight,
      getDisplayedRowAtIndex,
      getRowNodeByNodeId,
      getRowNodeByUUID,
      getSelectedNodes,
      getSelectedNode,
      setColumnDefs,
      showLoadingOverlay,
      hideOverlay,
      sizeColumnsToFit,
      getColumnDefs,
      refreshCells,
      redrawRows,
      refreshTableSizes,
      showNoRowsOverlay,
      onFilterChanged,
      selectionChanged,
      sortChanged,
      loadSortState,
      getSortState,
      getColumnState,
      setColumnState,
      setSortForColumn,
      getFilteredRows,
      getExportFilterString,
      getExportSortString,
      ensureIndexVisible,
      paginationGetPageSize,
      paginationGoToPage,
      ensureIndexVisibleAcrossPages,
      ensureSelectedRowVisible,
      deselectAllRows,
      resetColumnState,
      resetFilters,
      resetTableSortAndFilters,
      resetRowHeights,
      clearTableSortAndFilters,
    } = useTableApi(props, emit, internalGridOptions);

    return {
      internalGridOptions,
      filterChanged,
      loadFilterModel,
      getFilterModel,
      getFilterInstance,
      setFloatingFiltersHeight,
      getDisplayedRowAtIndex,
      getRowNodeByNodeId,
      getRowNodeByUUID,
      getSelectedNodes,
      getSelectedNode,
      setColumnDefs,
      showLoadingOverlay,
      hideOverlay,
      sizeColumnsToFit,
      getColumnDefs,
      refreshCells,
      redrawRows,
      refreshTableSizes,
      showNoRowsOverlay,
      onFilterChanged,
      selectionChanged,
      sortChanged,
      loadSortState,
      getSortState,
      getColumnState,
      setColumnState,
      setSortForColumn,
      getFilteredRows,
      getExportFilterString,
      getExportSortString,
      ensureIndexVisible,
      paginationGetPageSize,
      paginationGoToPage,
      ensureIndexVisibleAcrossPages,
      ensureSelectedRowVisible,
      deselectAllRows,
      resetColumnState,
      resetFilters,
      resetTableSortAndFilters,
      resetRowHeights,
      clearTableSortAndFilters,
    };
  },
});
</script>

<style lang="scss">
.ag-header-cell {
  background-image: url("~@/assets/top_main.png");
  color: #fff;
  color: var(--ag-background-color, #fff);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
