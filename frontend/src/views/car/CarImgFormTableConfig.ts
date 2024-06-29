import { ColDef } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import ImageCellRenderer from 'src/components/table/ImageCellRenderer.vue';
import i18n from 'src/i18n';

export class CarImgFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  // static getColumns(locale: string): ColDef[] {
  static getColumns(): ColDef[] {
    return [
      {
        field: 'carImgUuid',
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
        field: 'imgFile',
        headerName: i18n.global.t('imgFile'),
        flex: 1,
        sortable: true,
        filter: true,
      },
      {
        headerName: 'Image',
        floatingFilter: false,
        field: 'imgFile',
        cellRenderer: ImageCellRenderer,
        sortable: false,
        suppressMovable: true,
        suppressNavigable: true,
      },
    ];
  }

  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
    imageCellRenderer: ImageCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: 'carImgUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
