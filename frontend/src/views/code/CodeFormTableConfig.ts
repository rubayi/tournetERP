import {ColDef, ValueFormatterParams} from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import DateHelper from "src/utils/helpers/DateHelper";
const moment = require('moment');
export class CodeFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static columns: ColDef[] = [
    {
      headerName: "",
      field: "codeUuid",
      cellClass: "no-border",
      floatingFilter: false,
      cellRenderer: OpenButtonCellRenderer,
      maxWidth: 80,
      minWidth: 80,
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      headerName: "Group",
      field: "uprCodeUuid",
      floatingFilter: false,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code Name(Kr)",
      field: "codeKr",
      filter: true,
      minWidth: 250,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code Name(Eng)",
      field: "codeEn",
      filter: true,
      minWidth: 250,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Order",
      field: "codeOrd",
      floatingFilter: false,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code Level",
      field: "codeLvl",
      filter: true,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "USE YN",
      field: "useYn",
      floatingFilter: false,
      minWidth: 80,
      maxWidth: 80,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "ModifiedByName",
      field: "modifiedByName",
      floatingFilter: false,
      minWidth: 220,
      maxWidth: 220,
      flex: 1,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "LastUpdated",
      field: "modifiedDt",
      floatingFilter: false,
      minWidth: 220,
      maxWidth: 220,
      flex: 1,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return params.value
          ? DateHelper.formatISOStringToDateTimeString(params.value)
          : "";
      },
    },
  ];

  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: "codeUuid",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
