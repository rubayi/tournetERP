import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';

export class EmpEmergencyFormTableConfig {
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
      field: 'emergencyUuid',
      headerName: 'ID Num',
      width: 100,
      sortable: true,
      filter: true,
    },
    {
      field: 'emerName',
      headerName: 'Name',
      sortable: true,
      filter: true,
    },
    {
      field: 'emerRelation',
      headerName: 'Relation',
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'emerCity',
      headerName: 'Address(City)',
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'emerPhone1',
      headerName: 'Phone 1',
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'emerPhone2',
      headerName: 'Phone 2',
      width: 150,
      sortable: true,
      filter: true,
    },
    {
      field: 'emerWorkPhone',
      headerName: 'Work Phone',
      width: 150,
      sortable: true,
      filter: true,
    },
  ];
}
