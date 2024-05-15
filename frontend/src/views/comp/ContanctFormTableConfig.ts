import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';

export class ContactFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(locale: string): ColDef[] {
    return [
      {
        field: 'contactUuid',
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
        field: locale === 'en' ? 'contactTypeNameEn' : 'contactTypeName',
        headerName: i18n.global.t('contactType'),
        minWidth: 120,
        width: 150,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'contactCont',
        headerName: i18n.global.t('contactCont'),
        flex: 1,
        sortable: true,
        filter: true,
      },
      {
        field: locale === 'en' ? 'repYnNameEn' : 'repYnName',
        headerName: i18n.global.t('repYn'),
        minWidth: 70,
        maxWidth: 100,
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
      colId: 'contactUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
