import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';

export class EmpFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static columns: ColDef[] = [
    {
      field: 'empUuid',
      headerName: '',
      cellClass: 'no-border',
      floatingFilter: false,
      cellRenderer: OpenButtonCellRenderer,
      maxWidth: 80,
      minWidth: 80,
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      field: 'empUuid',
      headerName: i18n.global.t('num'),
      width: 100,
      sortable: true,
      filter: true,
      // checkboxSelection: true,
    },
    {
      field: 'username',
      headerName: i18n.global.t('username'),
      width: 130,
      sortable: true,
      filter: true,
    },
    {
      field: 'empKor',
      headerName: i18n.global.t('krname'),
      width: 140,
      sortable: true,
      filter: true,
    },
    {
      field: 'empEng',
      headerName: i18n.global.t('enname'),
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'empEmail',
      headerName: i18n.global.t('email'),
      width: 230,
      sortable: true,
      filter: true,
    },

    {
      field: 'empPhone',
      headerName: i18n.global.t('phone'),
      width: 140,
      sortable: true,
      filter: true,
    },
    {
      field: 'empDivName',
      headerName: i18n.global.t('divname'),
      width: 180,
      sortable: true,
      filter: true,
    },
    {
      field: 'empStatusName',
      headerName: i18n.global.t('empstatus'),
      width: 120,
      sortable: true,
      filter: true,
    },
    {
      field: 'empWorkPhone',
      headerName: i18n.global.t('workphone'),
      sortable: true,
      filter: true,
    },

  ];

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

