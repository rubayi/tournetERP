import { ColDef, ValueFormatterParams } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import i18n from 'src/i18n';

export class CodeFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(locale: string): ColDef[] {
    return [
      {
        headerName: i18n.global.t('num'),
        field: 'codeUuid',
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
      // {
      //   headerName: i18n.global.t('compgroup'),
      //   field: locale === 'en' ? 'groupNameEn' : 'groupName',
      //   floatingFilter: false,
      //   maxWidth: 120,
      //   sortable: true,
      //   resizable: true,
      //   cellStyle: { 'text-align': 'center' },
      // },
      {
        headerName: i18n.global.t('codeNameKr'),
        field: 'codeKr',
        filter: true,
        sortable: true,
        resizable: true,
        flex: 1,
      },
      {
        headerName: i18n.global.t('codeNameEn'),
        field: 'codeEn',
        filter: true,
        sortable: true,
        resizable: true,
        flex: 1,
      },
      {
        headerName: i18n.global.t('abbrevi'),
        field: 'codeAbb',
        filter: true,
        minWidth: 110,
        maxWidth: 140,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('order'),
        field: 'codeOrd',
        floatingFilter: false,
        minWidth: 70,
        maxWidth: 100,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('codeLvl'),
        field: 'codeLvl',
        filter: true,
        minWidth: 70,
        maxWidth: 110,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('usage'),
        field: 'useYn',
        floatingFilter: false,
        minWidth: 70,
        maxWidth: 100,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('modifiedBy'),
        field: 'modifiedByName',
        floatingFilter: false,
        minWidth: 170,
        maxWidth: 200,
        flex: 1,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('modifiedDt'),
        field: 'modifiedDt',
        floatingFilter: false,
        minWidth: 220,
        maxWidth: 220,
        flex: 1,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
        valueFormatter: (params: ValueFormatterParams): string => {
          return params.value
            ? DateHelper.formatISOStringToDateTimeString(params.value)
            : '';
        },
      },
    ];
  }
  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: 'codeUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
