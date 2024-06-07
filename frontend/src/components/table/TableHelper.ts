import { ColDef } from 'ag-grid-community';
import _ from 'lodash';
import DateHelper from 'src/utils/helpers/DateHelper';

/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export class TableHelper {
  static loadingOverlay =
    '<span class="ag-overlay-loading-center">Please wait while the rows are loading...</span>';

  static errorOverlay =
    '<span class="ag-overlay-loading-center">An error occurred - Unable to load data</span>';

  static noRowsOverlay =
    '<span class="ag-overlay-loading-center">There are no rows to show</span>';

  static textFilterParams = {
    inRangeInclusive: true,
    newRowsAction: 'keep',
    filterOptions: [
      'contains',
      'notContains',
      {
        displayKey: 'notBlank',
        displayName: 'Not blank',
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        test: function (filterValue: any, cellValue: any) {
          return (
            cellValue !== null && cellValue !== undefined && cellValue !== ''
          );
        },
        hideFilterInput: true,
      },
      'equals',
      'notEqual',
      'startsWith',
      'endsWith',
    ],
  };

  static numberFilterParams = {
    inRangeInclusive: true,
    newRowsAction: 'keep',
    filterOptions: [
      'equals',
      'notEqual',
      {
        displayKey: 'notBlank',
        displayName: 'Not blank',
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        test: function (filterValue: any, cellValue: any) {
          return (
            cellValue !== null && cellValue !== undefined && cellValue !== ''
          );
        },
        hideFilterInput: true,
      },
      'lessThan',
      'lessThanOrEqual',
      'greaterThan',
      'greaterThanOrEqual',
      'inRange',
    ],
  };

  static dateFilterParams = {
    inRangeInclusive: true,
    newRowsAction: 'keep',
    comparator: DateHelper.shortDateComparator,
    filterOptions: [
      'equals',
      'notEqual',
      {
        displayKey: 'notBlank',
        displayName: 'Not blank',
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        test: function (filterValue: any, cellValue: any) {
          return (
            cellValue !== null && cellValue !== undefined && cellValue !== ''
          );
        },
        hideFilterInput: true,
      },
      'lessThan',
      'lessThanOrEqual',
      'greaterThan',
      'greaterThanOrEqual',
      'inRange',
    ],
  };

  static defaultColDef = {
    minWidth: 150,
    sortable: true,
    suppressMenu: true,
    resizable: true,
    floatingFilter: true,
    filter: 'agTextColumnFilter',
    filterParams: TableHelper.textFilterParams,
  };
}

export function getOverlayForProjectIdDependentTable(
  isAddMode: boolean,
  entityName: string
): string {
  if (isAddMode) {
    return `<span class="ag-overlay-loading-center">${entityName} visible after project created </span>`;
  } else {
    return '<span class="ag-overlay-loading-center">There are no rows to show</span>';
  }
}

export function modifyActionCellRenderer(
  configColumns: ColDef[],
  disableAdd: boolean,
  disableEdit: boolean,
  disableDelete: boolean
): ColDef[] {
  const columns = _.cloneDeep(configColumns);
  if (columns[0].cellRenderer) {
    columns[0].hide = false;
    if (disableAdd && disableEdit && disableDelete) {
      columns[0].hide = true;
    } else if (disableEdit && disableDelete) {
      columns[0].cellRenderer = 'actionAddCellRenderer';
    } else if (disableDelete) {
      columns[0].cellRenderer = 'actionEditCellRenderer';
    } else if (disableEdit) {
      // disableDelete values of undefined means no delete permission exists and the full actionCellRenderer should be shown
      columns[0].cellRenderer = 'actionDeleteCellRenderer';
    } else {
      columns[0].cellRenderer = 'actionCellRenderer';
    }
  }
  return columns;
}
/* eslint-enable @typescript-eslint/explicit-module-boundary-types */
