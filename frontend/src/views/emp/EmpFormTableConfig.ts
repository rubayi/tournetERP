import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';

export class EmpFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(locale: string): ColDef[] {
    return [
    {
      field: 'empUuid',
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
      field: 'empUuid',
      headerName: i18n.global.t('num'),
      minWidth: 70,
      maxWidth: 80,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'username',
      headerName: i18n.global.t('username'),
      minWidth: 100,
      width: 130,
      sortable: true,
      filter: true,
    },
    {
      field: 'empKor',
      headerName: i18n.global.t('krname'),
      sortable: true,
      filter: true,
      flex: 1,
    },
    {
      field: 'empEng',
      headerName: i18n.global.t('enname'),
      sortable: true,
      filter: true,
      flex: 1,
    },
    {
      field: 'empEmailBook',
      headerName: i18n.global.t('email'),
      minWidth: 220,
      maxWidth: 250,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'empPhone',
      headerName: i18n.global.t('phone'),
      minWidth: 110,
      maxWidth: 140,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'empComp',
      headerName: i18n.global.t('empcompname'),
      minWidth: 110,
      maxWidth: 140,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'empDivName',
      headerName: i18n.global.t('divname'),
      minWidth: 110,
      maxWidth: 140,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'empStatusName',
      headerName: i18n.global.t('empstatus'),
      minWidth: 70,
      maxWidth: 100,
      sortable: true,
      filter: true,
      cellStyle: { 'text-align': 'center' },
    },
    {
      field: 'empWorkPhone',
      headerName: i18n.global.t('workphone'),
      minWidth: 110,
      maxWidth: 140,
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
      colId: 'empUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
