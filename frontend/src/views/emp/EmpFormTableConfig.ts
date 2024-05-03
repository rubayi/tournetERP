import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';

export class EmpFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static columns: ColDef[] = [
    {
      field: 'edit',
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
      headerName: 'ID Num',
      width: 100,
      sortable: true,
      filter: true,
      // checkboxSelection: true,
    },
    {
      field: 'username',
      headerName: 'User Name',
      width: 130,
      sortable: true,
      filter: true,
    },
    {
      field: 'empKor',
      headerName: 'Korean Name',
      width: 140,
      sortable: true,
      filter: true,
    },
    {
      field: 'empEng',
      headerName: 'Name',
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'empEmail',
      headerName: 'Email',
      width: 230,
      sortable: true,
      filter: true,
    },

    {
      field: 'empPhone',
      headerName: 'Phone',
      width: 140,
      sortable: true,
      filter: true,
    },
    {
      field: 'empDivName',
      headerName: 'Department',
      width: 180,
      sortable: true,
      filter: true,
    },
    {
      field: 'empStatusName',
      headerName: 'Employed Status',
      width: 120,
      sortable: true,
      filter: true,
    },
    {
      field: 'empWorkPhone',
      headerName: 'Extension Num',
      sortable: true,
      filter: true,
    },
    {
      field: 'empEmailBook',
      headerName: 'Work Email',
      hide: true,
    },
    {
      field: 'empAddress1',
      headerName: 'Address1',
      hide: true,
    },
    {
      field: 'empAddress2',
      headerName: 'Address2',
      hide: true,
    },
    {
      field: 'empCity',
      headerName: 'City',
      hide: true,
    },
    {
      field: 'empState',
      headerName: 'State',
      hide: true,
    },
    {
      field: 'empCountry',
      headerName: 'Country',
      hide: true,
    },
    {
      field: 'empZip',
      headerName: 'Zip Code',
      hide: true,
    },
    {
      field: 'empDob',
      headerName: 'DOB',
      hide: true,
    },
    {
      field: 'empDobType',
      headerName: 'DOB Type',
      hide: true,
    },
    {
      field: 'empMemo',
      headerName: '주소',
      hide: true,
    },
    {
      field: 'empStatus',
      headerName: '주소',
      hide: true,
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
