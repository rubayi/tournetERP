import { ColDef } from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import CheckIconCellRenderer from "src/components/table/CheckIconCellRenderer.vue";
export class ReportFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "reportId",
      cellClass: "no-border",
      floatingFilter: false,
      cellRenderer: OpenButtonCellRenderer,
      maxWidth: 50,
      minWidth: 50,
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      headerName: "Type",
      field: "reportTypeName.codename",
      filter: true,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Report Name",
      field: "reportName",
      filter: true,
      minWidth: 250,
      maxWidth: 250,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Report Title",
      field: "reporttitle",
      filter: true,
      minWidth: 220,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Level",
      field: "reportlevelName.codename",
      filter: true,
      minWidth: 220,
      maxWidth: 220,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Sort",
      field: "sortord",
      filter: true,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Expired",
      field: "expired",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
      cellRenderer: "checkIconCellRenderer",
    },
    {
      headerName: "Method",
      field: "methodName",
      filter: true,
      minWidth: 300,
      maxWidth: 300,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "LastUpdated",
      field: "lastUpdated",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 180,
      maxWidth: 180,
      flex: 1,
      sortable: true,
      resizable: true,
    },
  ];

  static frameworkComponents = {
    openButtonCellRenderer: OpenButtonCellRenderer,
    checkIconCellRenderer: CheckIconCellRenderer,
  };

  static defaultSortModel = [
    {
      colId: "reportId",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
