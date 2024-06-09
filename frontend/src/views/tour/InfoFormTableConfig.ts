import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';

export class InfoFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(locale: string): ColDef[] {
    return [
      {
        field: 'infoUuid',
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
        field: locale === 'en' ? 'infoTypeNameEn' : 'infoTypeName',
        headerName: i18n.global.t('infoType'),
        minWidth: 70,
        width: 80,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'contentKor',
        headerName: i18n.global.t('contentKor'),
        flex: 1,
        sortable: true,
        filter: true,
      },
      {
        field: 'contentEng',
        headerName: i18n.global.t('contentEng'),
        minWidth: 150,
        maxWidth: 180,
        sortable: true,
        filter: true,
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
