import { ColDef, ValueFormatterParams } from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import DateHelper from "src/utils/helpers/DateHelper";
import TooltipComponent from "src/components/table/TooltipComponent.vue";
export class TournetFormTableConfig {
  static reportHeaderMap = {
    callid: "NO",
  };

  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "worknum",
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
      field: "workdep",
      filter: true,
      pinned: "left",
      minWidth: 95,
      maxWidth: 95,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Order #",
      field: "workno",
      filter: true,
      pinned: "left",
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Date",
      field: "workdate",
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
      headerName: "To",
      field: "letterto",
      filter: true,
      pinned: "left",
      minWidth: 90,
      maxWidth: 90,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Route No",
      field: "workrouteno",
      filter: true,
      minWidth: 180,
      maxWidth: 180,
      sortable: true,
      resizable: true,
      tooltipComponent: "customTooltip",
      tooltipField: "workrouteno",
      tooltipComponentParams: { toolTipContentKey: "workrouteno" },
    },
    {
      headerName: "Subject",
      field: "lettersubject",
      filter: true,
      minWidth: 420,
      sortable: true,
      resizable: true,
      tooltipComponent: "customTooltip",
      tooltipField: "lettersubject",
      tooltipComponentParams: { toolTipContentKey: "lettersubject" },
    },
    {
      headerName: "Status",
      field: "fullStatues",
      filter: true,
      minWidth: 260,
      maxWidth: 260,
      sortable: true,
      resizable: true,
      tooltipComponent: "customTooltip",
      tooltipField: "fullStatues",
      tooltipComponentParams: { toolTipContentKey: "fullStatues" },
    },
    {
      headerName: "Received",
      field: "workorderlogform.receiveddate",
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
      headerName: "Received by",
      field: "workorderlogform.receivedby",
      filter: true,
      suppressMenu: true,
      minWidth: 220,
      maxWidth: 220,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Date Assigned",
      field: "workorderlogform.datecrewassigned",
      filter: true,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return params.value
          ? DateHelper.formatISOStringToShortDateString(params.value)
          : "";
      },
    },
    {
      headerName: "CrewAssigned",
      field: "fullCrewassign",
      filter: true,
      suppressMenu: true,
      minWidth: 220,
      maxWidth: 220,
      sortable: true,
      resizable: true,
      tooltipComponent: "customTooltip",
      tooltipField: "fullCrewassign",
      tooltipComponentParams: { toolTipContentKey: "fullCrewassign" },
    },
    {
      headerName: "Completed",
      field: "workorderlogform.datecompleted",
      filter: true,
      suppressMenu: true,
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
        return params.value
          ? DateHelper.formatISOStringToShortDateString(params.value)
          : "";
      },
    },
    {
      headerName: "Posted By",
      field: "workorderlogform.postedby",
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
      colId: "workdate",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
