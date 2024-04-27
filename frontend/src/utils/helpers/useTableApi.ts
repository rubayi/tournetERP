import { ColDef, ColumnState, RowNode } from "ag-grid-community";
import _ from "lodash";
import { ref, watch } from "vue";
import DateHelper from "src/utils/helpers/DateHelper";

class FilterModelValue {
  filterType?: any;
  filter?: any;
  operator?: any;
  dateFrom?: any;
  dateTo?: any;
  type?: any;
}

/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export function useTableApi(
  props: any,
  emit: any,
  internalGridOptions: any
): any {
  let filterModel: any = null;
  let sortState: any = null;

  watch(
    () => [props.gridOptions, props.defaultColDef],
    () => {
      internalGridOptions.value = props.gridOptions;
      internalGridOptions.value.defaultColDef = props.defaultColDef;
    },
    { immediate: true }
  );

  watch(
    () => props.loading,
    (newVal) => {
      if (internalGridOptions.value.api) {
        if (newVal) {
          showLoadingOverlay();
        } else {
          hideOverlay();
          sizeColumnsToFit();
        }
      }
    },
    { immediate: true }
  );

  function showLoadingOverlay() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.showLoadingOverlay();
    }
  }

  function hideOverlay() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.hideOverlay();
    }
  }

  function sizeColumnsToFit() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.sizeColumnsToFit();
    }
  }

  function getColumnDefs() {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.getColumnDefs();
    }
    return null;
  }

  function setColumnDefs(colDefs: Array<ColDef>) {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.setColumnDefs(colDefs);
    }
  }

  function getColumnHeaderName(colId: string) {
    const colDefs: ColDef[] | null = getColumnDefs();
    const col: ColDef | null | undefined = colDefs
      ? colDefs.find((c) => c.colId === colId)
      : null;
    return col ? col.headerName : null;
  }

  function refreshCells() {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.refreshCells({ force: true });
    }
  }

  function redrawRows() {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.redrawRows();
    }
  }

  function getFilterInstance(name: string) {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.getFilterInstance(name);
    }
  }

  function setFloatingFiltersHeight(height: number | null) {
    if (internalGridOptions.value.api && height) {
      return internalGridOptions.value.api.setFloatingFiltersHeight(height);
    }
  }

  function getDisplayedRowAtIndex(index: number) {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.getDisplayedRowAtIndex(index);
    }
  }

  function getRowNodeByNodeId(nodeId: string) {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.getRowNode(nodeId);
    }
  }

  function getRowNodeByUUID(uuid: string) {
    let matching = null;
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.forEachNode((rowNode: RowNode) => {
        if (rowNode.data.uuid === uuid) {
          matching = rowNode;
        }
      });
    }
    return matching;
  }

  function getSelectedNodes() {
    if (internalGridOptions.value.api) {
      return internalGridOptions.value.api.getSelectedNodes();
    }
  }

  function getSelectedNode() {
    const nodes = getSelectedNodes();
    return nodes && nodes.length > 0 ? nodes[0] : null;
  }

  function filterChanged() {
    if (internalGridOptions.value.api) {
      emit("filterChanged", internalGridOptions.value.api.getFilterModel());
      saveFilterModel();
      resizeHeader();
    }
  }

  function saveFilterModel() {
    if (internalGridOptions.value.api) {
      filterModel = internalGridOptions.value.api.getFilterModel();
    }
  }

  function loadFilterModel() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.setFilterModel(filterModel);
    }
  }

  function getFilterModel() {
    return filterModel;
  }

  function resizeHeader() {
    if (
      internalGridOptions.value &&
      internalGridOptions.value.columnDefs &&
      internalGridOptions.value.api
    ) {
      const columnDefCopy = _.cloneDeep(internalGridOptions.value.columnDefs);
      const columnsWithMultiselectFilter = columnDefCopy
        .filter((x: any) => x.filter === "multiSelectFilter")
        ?.map((y: any) => y.field);

      const filterModelCopy =
        _.cloneDeep(internalGridOptions.value.api.getFilterModel()) ?? {};
      const multiselectColumnIsBeingFiltered =
        columnsWithMultiselectFilter.some(
          (c: string) =>
            filterModelCopy[c] !== undefined && !_.isEmpty(filterModelCopy[c])
        );

      if (multiselectColumnIsBeingFiltered) {
        const multiselectInputs = Array.from(document.getElementsByClassName("multiselect-input"));
        const maxValue = _.max(
          multiselectInputs.map((element) => element.clientHeight)
        );
        setFloatingFiltersHeight(
          maxValue && maxValue > 48 ? maxValue + 20 : 47
        );
      } else {
        setFloatingFiltersHeight(47);
      }
    }
  }

  function refreshTableSizes() {
    resetRowHeights();
    sizeColumnsToFit();
    resizeHeader();
  }

  function showNoRowsOverlay() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.hideOverlay();
      return internalGridOptions.value.api.showNoRowsOverlay();
    }
  }

  function onFilterChanged() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.onFilterChanged();
      // filter change default debounce value is 500ms
      setTimeout(resizeHeader, 600);
    }
  }

  function selectionChanged() {
    emit("selectionChanged");
  }

  function sortChanged() {
    emit("sortChanged");
    saveSortState();
  }

  function saveSortState() {
    if (internalGridOptions.value.columnApi) {
      const colState = internalGridOptions.value.columnApi.getColumnState();

      sortState = colState
        .filter(function (s: ColumnState) {
          return s.sort != null;
        })
        .map(function (s: ColumnState) {
          return {
            colId: s.colId,
            sort: s.sort,
            sortIndex: s.sortIndex,
          };
        });
    }
  }

  function loadSortState() {
    if (internalGridOptions.value.columnApi) {
      if (!sortState) {
        saveSortState();
      }
      internalGridOptions.value.columnApi.applyColumnState({
        state: sortState,
      });
    }
  }

  function getSortState() {
    return sortState;
  }

  function getColumnState() {
    if (internalGridOptions.value.columnApi) {
      return internalGridOptions.value.columnApi.getColumnState();
    } else {
      return null;
    }
  }

  function setColumnState(columnState: any) {
    if (internalGridOptions.value.columnApi) {
      internalGridOptions.value.columnApi.applyColumnState(columnState);
    }
  }

  function setSortForColumn(colId: string, sort: string, sortIndex?: number) {
    setTimeout(() => {
      if (internalGridOptions.value.columnApi) {
        const hasSortIndex =
          sortIndex !== undefined &&
          sortState &&
          sortState.length > 0 &&
          sortState.some((s: any) => s.sortIndex === sortIndex);

        if (hasSortIndex && sortIndex !== undefined) {
          const modifiedSortState = sortState.sort(
            (a: any, b: any) => a.sortIndex > b.sortIndex
          );
          const insertIndex =
            sortIndex <= modifiedSortState.length - 1
              ? sortIndex
              : modifiedSortState.length;

          if (insertIndex === modifiedSortState.length) {
            modifiedSortState.push({
              colId: colId,
              sort: sort,
              sortIndex: insertIndex,
            });
          } else {
            modifiedSortState.splice(insertIndex, 0, {
              colId: colId,
              sort: sort,
              sortIndex: insertIndex,
            });
            for (let i = 0; i < modifiedSortState.length; i++) {
              modifiedSortState[i].sortIndex = i;
            }
          }
          internalGridOptions.value.columnApi.applyColumnState({
            state: modifiedSortState,
          });
        } else {
          internalGridOptions.value.columnApi.applyColumnState({
            state: [
              {
                colId: colId,
                sort: sort,
                sortIndex: sortIndex,
              },
            ],
          });
        }
      }
    }, 100);
  }

  function getFilteredRows() {
    const filteredData = ref<any>([]);
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.forEachNodeAfterFilterAndSort((r: any) => {
        filteredData.value.push(r.data);
      });
    }
    return filteredData.value;
  }

  function getExportFilterString(reportHeaderMap: any) {
    let str = "";

    // Set the latest version of filter model from the current state of the grid
    saveFilterModel();
    const colDefs: ColDef[] | null = getColumnDefs();

    if (filterModel && colDefs) {
      const filteredStrArr: string[] = colDefs
        .map((c) => {
          let colDefFilterString = "";
          if (c.headerName && c.colId && filterModel[c.colId]) {
            let colDefHeaderName: string = c.headerName;

            if (reportHeaderMap && reportHeaderMap[c.colId]) {
              colDefHeaderName = reportHeaderMap[c.colId];
            }
            const filterModelValue: FilterModelValue = _.cloneDeep(
              filterModel[c.colId]
            );
            colDefFilterString = `[${generateColDefFilterString(
              colDefHeaderName,
              filterModelValue
            )}]`;
          }
          return colDefFilterString;
        })
        .filter((m) => m !== "" && m !== "[]");
      if (filteredStrArr && filteredStrArr.length > 0) {
        str = filteredStrArr.reduce(
          (accumulator, currentValue) => accumulator + ", " + currentValue
        );
      }
    }
    return str === "" ? "None" : str;
  }

  function generateColDefFilterString(
    colDefHeaderName: string | null,
    filterModelValue: FilterModelValue
  ) {
    let str = "";

    if (colDefHeaderName && filterModelValue) {
      str = `${colDefHeaderName}: `;

      // Multi-Select Filter
      if (_.isArray(filterModelValue) && !_.isEmpty(filterModelValue)) {
        str += filterModelValue
          .sort()
          .map(
            (o) =>
              `contains "${!_.isNull(o) && !_.isEqual(o, "null") ? o : ""}"`
          )
          .reduce(
            (accumulator, currentValue) => accumulator + " OR " + currentValue
          );
      }
      // AG-Grid Filter (i.e. typing in build-in header or additional filters from clicking on Filter Icon)
      else if (_.isObject(filterModelValue) && !_.isArray(filterModelValue)) {
        // Using AND/OR Additional Filters (i.e. click on Filter Icon -> fill in two filters)
        if (filterModelValue.operator) {
          str += Object.entries(filterModelValue)
            .map(([key, value]) => {
              let filterStr = "";
              if (key.includes("condition")) {
                const val: any = value;

                if (val.type !== "inRange" && !val.dateFrom) {
                  filterStr = `${val.type} "${val.filter}"`;
                } else if (val.type === "inRange" && !val.dateFrom) {
                  filterStr = `${val.type} "${val.filter}" - "${val.filterTo}"`;
                } else if (val.dateFrom && !val.dateTo) {
                  filterStr = `${
                    val.type
                  } "${DateHelper.formatISOStringToShortDateString(
                    filterModelValue.dateFrom
                  )}"`;
                } else if (!val.dateFrom && val.dateTo) {
                  filterStr = `${
                    val.type
                  } "${DateHelper.formatISOStringToShortDateString(
                    filterModelValue.dateTo
                  )}"`;
                } else if (val.dateFrom && val.dateTo) {
                  filterStr = `${val.type} "${
                    val.dateFrom
                      ? DateHelper.formatISOStringToShortDateString(
                          val.dateFrom
                        )
                      : ""
                  }" - "${
                    val.dateTo
                      ? DateHelper.formatISOStringToShortDateString(val.dateTo)
                      : ""
                  }"`;
                } else {
                  filterStr = `${val.type} "${val.filter}"`;
                }
              }
              return filterStr;
            })
            .filter((m) => m !== "")
            .reduce(
              (accumulator, currentValue) =>
                accumulator + ` ${filterModelValue.operator} ` + currentValue
            );
        } else if (
          filterModelValue.filterType &&
          filterModelValue.filterType === "date"
        ) {
          if (filterModelValue.type !== "inRange") {
            str += `${filterModelValue.type} "${
              filterModelValue.dateFrom
                ? DateHelper.formatISOStringToShortDateString(
                    filterModelValue.dateFrom
                  )
                : ""
            }"`;
          } else {
            str += `${filterModelValue.type} "${
              filterModelValue.dateFrom
                ? DateHelper.formatISOStringToShortDateString(
                    filterModelValue.dateFrom
                  )
                : ""
            }" - "${
              filterModelValue.dateTo
                ? DateHelper.formatISOStringToShortDateString(
                    filterModelValue.dateTo
                  )
                : ""
            }"`;
          }
        } else {
          const val: any = filterModelValue;
          str +=
            val.type !== "inRange"
              ? `${val.type} "${val.filter}"`
              : `${val.type} "${val.filter}" - "${val.filterTo}"`;
        }
      } else {
        str = "";
      }
    }
    return str;
  }

  function getExportSortString(reportHeaderMap: any) {
    let str = "";
    // Set the latest version of sort state from the current state of the grid
    saveSortState();

    if (sortState && sortState.length > 0) {
      str = sortState
        .sort((a: any, b: any) => (a.sortIndex > b.sortIndex ? 1 : -1))
        .map((s: any) => {
          let colDefSortString = "";
          if (s.colId) {
            let colDefHeaderName = getColumnHeaderName(s.colId);

            if (reportHeaderMap && reportHeaderMap[s.colId]) {
              colDefHeaderName = reportHeaderMap[s.colId];
            }
            const sortVal: string = s.sort;
            colDefSortString = `[${colDefHeaderName}: ${sortVal}]`;
          }
          return colDefSortString;
        })
        .filter((m: any) => m !== "" && m !== "[]")
        .reduce(
          (accumulator: any, currentValue: any) =>
            accumulator + ", " + currentValue
        );
    }
    return str === "" ? "None" : str;
  }

  function ensureIndexVisible(index: number) {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.ensureIndexVisible(index, "middle");
    }
  }

  function paginationGetPageSize(): number {
    let page = 0;
    if (internalGridOptions.value.api) {
      page = internalGridOptions.value.api.paginationGetPageSize();
    }
    return page;
  }

  function paginationGoToPage(page: number) {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.paginationGoToPage(page);
    }
  }

  function ensureIndexVisibleAcrossPages(index: number) {
    const pageNumber = Math.floor(index / paginationGetPageSize());
    paginationGoToPage(pageNumber);
    ensureIndexVisible(index);
  }

  function ensureSelectedRowVisible() {
    if (internalGridOptions.value.api) {
      const selectedNodes = internalGridOptions.value.api.getSelectedNodes();
      const selectedNode =
        selectedNodes && selectedNodes.length > 0 ? selectedNodes[0] : null;
      if (selectedNode && selectedNode.rowIndex != null) {
        ensureIndexVisibleAcrossPages(selectedNode.rowIndex);
      }
    }
  }

  function deselectAllRows() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.deselectAll();
    }
  }

  function resetColumnState() {
    if (internalGridOptions.value.columnApi) {
      internalGridOptions.value.columnApi.resetColumnState();
      if (props.defaultSortModel) {
        internalGridOptions.value.columnApi.applyColumnState({
          state: props.defaultSortModel as ColumnState[],
        });
      }
    }
  }

  function resetFilters() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.setFilterModel(props.defaultFilterValues);
      resizeHeader();
    }
  }

  function resetTableSortAndFilters() {
    resetColumnState();
    resetFilters();
  }

  function resetRowHeights() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.resetRowHeights();
    }
  }

  function clearTableSortAndFilters() {
    if (internalGridOptions.value.api) {
      internalGridOptions.value.api.setFilterModel(null);
      internalGridOptions.value.api.setSortModel(null);
    }
  }

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
}

/* eslint-enable @typescript-eslint/explicit-module-boundary-types */
