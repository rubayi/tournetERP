import { ColDef, ValueFormatterParams } from 'ag-grid-community';
import OpenButtonCellRenderer from 'src/components/table/OpenButtonCellRenderer.vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import i18n from 'src/i18n';

export class CodeFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  // Add a new property to store uprName
  static uprName: { codeUuid: number; codeEn: string }[] = [];
  // Add a new method to set uprName
  static setUprName(uprName: { codeUuid: number; codeEn: string }[]) {
    CodeFormTableConfig.uprName = uprName;
  }

  static getColumns(locale: string): ColDef[] {
    return [
      {
        headerName: i18n.global.t('num'),
        field: 'codeUuid',
        cellClass: 'no-border',
        floatingFilter: false,
        cellRenderer: OpenButtonCellRenderer,
        minWidth: 70,
        maxWidth: 80,
        sortable: false,
        suppressMovable: true,
        suppressNavigable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'Group',
        field: 'uprCodeUuid',
        floatingFilter: false,
        maxWidth: 120,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
        valueGetter: (params) => {
          // Find the uprName object where codeUuid equals uprCodeUuid
          const uprNameObj = CodeFormTableConfig.uprName.find(
            (uprName: { codeUuid: number }) =>
              uprName.codeUuid == params.data.uprCodeUuid
          );
          // If found, return the codeEn value, otherwise return an empty string
          return uprNameObj ? uprNameObj.codeEn : 'Root';
        },
      },
      {
        headerName: 'Code Name(Kr)',
        field: 'codeKr',
        filter: true,
        sortable: true,
        resizable: true,
        flex: 1,
      },
      {
        headerName: 'Code Name(Eng)',
        field: 'codeEn',
        filter: true,
        sortable: true,
        resizable: true,
        flex: 1,
      },
      {
        headerName: 'Abbreviation',
        field: 'codeAbb',
        filter: true,
        minWidth: 110,
        maxWidth: 140,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'Order',
        field: 'codeOrd',
        floatingFilter: false,
        minWidth: 70,
        maxWidth: 100,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'Code Lvl',
        field: 'codeLvl',
        filter: true,
        minWidth: 70,
        maxWidth: 110,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'Use Y/N',
        field: 'useYn',
        floatingFilter: false,
        minWidth: 70,
        maxWidth: 100,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'ModifiedByName',
        field: 'modifiedByName',
        floatingFilter: false,
        minWidth: 170,
        maxWidth: 200,
        flex: 1,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
      },
      {
        headerName: 'LastUpdated',
        field: 'modifiedDt',
        floatingFilter: false,
        minWidth: 220,
        maxWidth: 220,
        flex: 1,
        sortable: true,
        resizable: true,
        cellStyle: {'text-align': 'center'},
        valueFormatter: (params: ValueFormatterParams): string => {
          return params.value
            ? DateHelper.formatISOStringToDateTimeString(params.value)
            : '';
        },
      },
    ];
  }
  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: 'codeUuid',
      sort: 'desc',
      sortIndex: 0,
    },
  ];
}
