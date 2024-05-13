import { ColDef, ValueFormatterParams } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import i18n from 'src/i18n';

export class CdcdFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(): ColDef[] {
    return [
      {
        headerName: '',
        field: 'cdCdUuid',
        cellClass: 'no-border',
        floatingFilter: false,
        cellRenderer: OpenButtonCellRenderer,
        maxWidth: 80,
        minWidth: 80,
        sortable: false,
        suppressMovable: true,
        suppressNavigable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('mngNameKor'),
        field: 'mngNameKor',
        floatingFilter: false,
        minWidth: 120,
        flex: 1,
        sortable: true,
        resizable: true,
      },
      {
        headerName: i18n.global.t('mngNameEng'),
        field: 'mngNameEng',
        filter: true,
        minWidth: 140,
        flex: 1,
        sortable: true,
        resizable: true,
      },

      {
        headerName: i18n.global.t('carNum'),
        field: 'cardNumber',
        floatingFilter: false,
        minWidth: 150,
        flex: 1,
        sortable: true,
        resizable: true,
      },
      {
        headerName: i18n.global.t('nameOnCard'),
        field: 'nameOnCard',
        filter: true,
        minWidth: 200,
        maxWidth: 220,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('beginDt'),
        field: 'beginDt',
        filter: true,
        minWidth: 120,
        maxWidth: 150,
        sortable: true,
        resizable: true,
        valueFormatter: (params: ValueFormatterParams): string => {
          return params.value
            ? DateHelper.formatISOStringToShortDateString(params.value)
            : '';
        },
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('endDt'),
        field: 'endDt',
        filter: true,
        minWidth: 120,
        maxWidth: 150,
        sortable: true,
        resizable: true,
        valueFormatter: (params: ValueFormatterParams): string => {
          return params.value
            ? DateHelper.formatISOStringToShortDateString(params.value)
            : '';
        },
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('modifiedBy'),
        field: 'modifiedByName',
        floatingFilter: false,
        minWidth: 120,
        maxWidth: 150,
        flex: 1,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('modifiedDt'),
        field: 'modifiedDt',
        floatingFilter: false,
        minWidth: 170,
        maxWidth: 200,
        flex: 1,
        sortable: true,
        resizable: true,
        valueFormatter: (params: ValueFormatterParams): string => {
          return params.value
            ? DateHelper.formatISOStringToDateTimeString(params.value)
            : '';
        },
        cellStyle: { 'text-align': 'center' },
      },
    ];
  }
  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: 'cdCdUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
