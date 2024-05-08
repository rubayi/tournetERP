<template>
  <ag-grid-vue
    class="ag-theme-alpine grid"
    :column-defs="columnDefs"
    :context="context"
    style="width: 100%; height: 800px; overflow: auto"
    :overlay-loading-template="overlayLoadingTemplate"
    :pagination="pagination"
    pagination-auto-page-size="auto"
    :pagination-page-size="paginationPageSize"
    :row-data="rowData"
    :row-selection="rowSelection"
    :set-count="100"
    :open-action="openAction"
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
import { AgGridVue } from 'ag-grid-vue3';
import { ColDef, GridOptions } from 'ag-grid-community';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import { useTableApi } from 'src/utils/helpers/useTableApi';
import { defineComponent, ref, watch } from 'vue';
interface InternalGridOptions {
  api?: {
    setRowData: (rowData: unknown[]) => void;
  };
}
export default defineComponent({
  name: 'TableComp',
  components: { AgGridVue },
  props: {
    openAction: {
      type: Function,
      required: true,
    },
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
      default: 20,
    },
    rowData: {
      type: Array,
      default: () => [],
    },
    rowSelection: {
      type: String,
      default: 'none',
    },
  },
  emits: ['grid-ready', 'filterChanged', 'selectionChanged', 'sortChanged'],
  setup(props, { emit }) {
    const getRowHeight = () => {
      return 45;
    };
    // const defaultColDef = {
    //   flex: 1,
    // };
    const internalGridOptions = ref<InternalGridOptions>({});

    watch(
      () => props.rowData,
      (newVal) => {
        if (internalGridOptions.value.api && newVal) {
          internalGridOptions.value.api.setRowData(newVal);
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
      getRowHeight,
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
  background-image: url('src/assets/top_main.png');
  color: #fff;
  color: var(--ag-background-color, #fff);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
