import { ColDef, ValueFormatterParams } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import TooltipComponent from 'src/components/table/TooltipComponent.vue';
import i18n from 'src/i18n';

export class CompFormTableConfig {
  static reportHeaderMap = {
    compUuid: 'NO',
    compGroupName: '',
    compKor: '',
    compEng: '',
    estDate: '',
    compRate: '',
  };

  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static getColumns(locale: string): ColDef[] {
    return [
      {
        headerName: '',
        field: 'compUuid',
        cellClass: 'no-border',
        floatingFilter: false,
        cellRenderer: OpenButtonCellRenderer,
        maxWidth: 80,
        minWidth: 90,
        sortable: false,
        suppressMovable: true,
        suppressNavigable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'compUuid',
        headerName: i18n.global.t('num'),
        minWidth: 70,
        maxWidth: 80,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('krcomp'),
        field: 'compKor',
        filter: true,
        flex: 1,
        sortable: true,
        resizable: true,
      },
      {
        headerName: i18n.global.t('encomp'),
        field: 'compEng',
        filter: true,
        flex: 1,
        sortable: true,
        resizable: true,
      },
      {
        headerName: i18n.global.t('compsector'),
        field: locale === 'en' ? 'compSectorNameEn' : 'compSectorName',
        filter: true,
        minWidth: 180,
        maxWidth: 180,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      // {
      //   headerName: i18n.global.t('estdt'),
      //   field: 'estDate',
      //   filter: true,
      //   minWidth: 100,
      //   maxWidth: 130,
      //   sortable: true,
      //   resizable: true,
      //   valueFormatter: (params: ValueFormatterParams): string => {
      //     return DateHelper.formatISOStringToShortDateString(params.value);
      //   },
      //   cellStyle: { 'text-align': 'center' },
      // },
      {
        headerName: i18n.global.t('compgroup'),
        field: locale === 'en' ? 'compGroupNameEn' : 'compGroupName',
        filter: true,
        minWidth: 100,
        maxWidth: 130,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('compRate'),
        field: 'compRateName',
        filter: true,
        minWidth: 120,
        maxWidth: 150,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('beginDt'),
        field: 'beginDt',
        filter: true,
        minWidth: 100,
        maxWidth: 130,
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
        minWidth: 100,
        maxWidth: 130,
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
        filter: true,
        minWidth: 100,
        maxWidth: 130,
        sortable: true,
        resizable: true,
        valueFormatter: (params: ValueFormatterParams): string => {
          return DateHelper.formatISOStringToShortDateString(params.value);
        },
        cellStyle: { 'text-align': 'center' },
      },
    ];
  }
  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
    customTooltip: TooltipComponent,
  };

  static defaultSortModel = [
    {
      colId: 'modifiedDt',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
