import { ColDef, ValueFormatterParams } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import TooltipComponent from 'src/components/table/TooltipComponent.vue';
import i18n from 'src/i18n';

export class CarFormTableConfig {
  static reportHeaderMap = {
    trnCarUuid: 'NO',
    carTypeKor: '',
    carBrand: '',
    licensePlate: '',
    carYear: '',
    carManager: '',
  };

  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static getColumns(locale: string): ColDef[] {
    return [
      {
        headerName: '',
        field: 'trnCarUuid',
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
        headerName: i18n.global.t('carType'),
        field: locale === 'en' ? 'carTypeEng' : 'carTypeKor',
        filter: true,
        minWidth: 100,
        maxWidth: 130,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('carBrand'),
        field: locale === 'en' ? 'carBrandEng' : 'carBrandKor',
        filter: true,
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('carYear'),
        field: 'carYear',
        filter: true,
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        resizable: true,
      },
      {
        headerName: i18n.global.t('licensePlate'),
        field: `licensePlate`,
        filter: true,
        minWidth: 150,
        maxWidth: 150,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('carVin'),
        field: `carVin`,
        filter: true,
        minWidth: 200,
        maxWidth: 200,
        sortable: true,
        resizable: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        headerName: i18n.global.t('carManager'),
        field: locale === 'en' ? 'carManagerNameEng' : 'carManagerNameKor',
        filter: true,
        flex: 1,
        sortable: true,
        resizable: true,
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
        headerName: i18n.global.t('carEndDt'),
        field: 'carEndDt',
        filter: true,
        minWidth: 140,
        maxWidth: 140,
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
        headerName: i18n.global.t('modifiedDt'),
        field: 'modifiedDt',
        filter: true,
        minWidth: 140,
        maxWidth: 140,
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
