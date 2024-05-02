import { ColDef, ValueFormatterParams } from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import DateHelper from "src/utils/helpers/DateHelper";
import TooltipComponent from "src/components/table/TooltipComponent.vue";
export class CompFormTableConfig {
  static reportHeaderMap = {
    callid: "NO",
  };

  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "compUuid",
      cellClass: "no-border",
      floatingFilter: false,
      cellRenderer: "openButtonCellRenderer",
      maxWidth: 50,
      minWidth: 50,
      pinned: "left",
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      headerName: "",
      field: "compSector",
      filter: true,
      pinned: "left",
      minWidth: 95,
      maxWidth: 95,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Company",
      field: "compKor",
      filter: true,
      pinned: "left",
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Company(En)",
      field: "compEng",
      filter: true,
      pinned: "left",
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Establish Date",
      field: "estDate",
      filter: true,
      pinned: "left",
      minWidth: 110,
      maxWidth: 110,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return DateHelper.formatISOStringToShortDateString(params.value);
      },
    },
    {
      headerName: "compRate",
      field: "compRate",
      filter: true,
      pinned: "left",
      minWidth: 90,
      maxWidth: 90,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "hotelRate",
      field: "hotelRate",
      filter: true,
      minWidth: 180,
      maxWidth: 180,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Group",
      field: "compGroup",
      filter: true,
      minWidth: 420,
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
      headerName: "Posted",
      field: "workorderlogform.posteddate",
      filter: true,
      suppressMenu: true,
      minWidth: 110,
      maxWidth: 110,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return DateHelper.formatISOStringToShortDateString(params.value);
      },
    },
    {
      headerName: "Modified Date",
      field: "modifiedDt",
      filter: true,
      suppressMenu: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
  ];

  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
    customTooltip: TooltipComponent,
  };

  static defaultSortModel = [
    {
      colId: "modifiedDt",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
