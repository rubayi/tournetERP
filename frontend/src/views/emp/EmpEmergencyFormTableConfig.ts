import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import i18n from 'src/i18n';

export class EmpEmergencyFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static getColumns(): ColDef[] {
    return [
      {
        field: 'emergencyUuid',
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
        field: 'emerRelation',
        headerName: i18n.global.t('relation'),
        minWidth: 170,
        maxWidth: 200,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'emerName',
        headerName: i18n.global.t('emergencyName'),
        sortable: true,
        filter: true,
        flex: 1,
        cellStyle: { 'text-align': 'center' },
      },

      {
        field: 'emerPhone1',
        headerName: i18n.global.t('phone'),
        flex: 1,
        sortable: true,
        filter: true,
        cellStyle: { 'text-align': 'center' },
      },
      {
        field: 'emerWorkPhone',
        headerName: i18n.global.t('workphone'),
        flex: 1,
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
      colId: 'emergencyUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
