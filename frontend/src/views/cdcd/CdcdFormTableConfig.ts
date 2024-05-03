import {ColDef, ValueFormatterParams} from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import DateHelper from "src/utils/helpers/DateHelper";
const moment = require('moment');
export class CdcdFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';

  static columns: ColDef[] = [
    {
      headerName: "",
      field: "cdCdUuid",
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
      headerName: "Manage Name",
      field: "mngNameKor",
      floatingFilter: false,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Manage Name(En)",
      field: "mngNameEng",
      filter: true,
      minWidth: 100,
      sortable: true,
      resizable: true,
    },

    {
      headerName: "Card Number",
      field: "cardNumber",
      floatingFilter: false,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Name On Card",
      field: "nameOnCard",
      filter: true,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Begin Date",
      field: "beginDt",
      filter: true,
      minWidth: 110,
      maxWidth: 110,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return params.value
          ? DateHelper.formatISOStringToShortDateString(params.value)
          : "";
      },
    },
    {
      headerName: "End Date",
      field: "endDt",
      filter: true,
      minWidth: 110,
      maxWidth: 110,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return params.value
          ? DateHelper.formatISOStringToShortDateString(params.value)
          : "";
      },
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
      colId: "cdCdUuid",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
