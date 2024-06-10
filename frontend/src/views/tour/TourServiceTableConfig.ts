import {ColDef, ValueFormatterParams} from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';
import DateHelper from "src/utils/helpers/DateHelper";

export class TourServiceTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(locale: string): ColDef[] {
    return [
      {
        field: 'serviceUuid',
        headerName: '',
        cellClass: 'no-border',
        floatingFilter: false,
        cellRenderer: OpenButtonCellRenderer,
        minWidth: 70,
        maxWidth: 80,
        sortable: false,
        suppressMovable: true,
        suppressNavigable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'serviceNameKor',
        headerName: i18n.global.t('serviceNameKor'),
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        filter: true,
      },
      {
        field: 'serviceNameEng',
        headerName: i18n.global.t('serviceNameEng'),
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'maxPeople',
        headerName: i18n.global.t('maxPeople'),
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        filter: true,
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
      colId: 'modifiedDt',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
