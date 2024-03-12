import { ColDef } from "ag-grid-community";
import OpenButtonCellRenderer from "@/components/table/OpenButtonCellRenderer.vue";
import CheckIconCellRenderer from "@/components/table/CheckIconCellRenderer.vue";
export class MemberFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "memID",
      cellClass: "no-border",
      floatingFilter: false,
      cellRenderer: "openButtonCellRenderer",
      maxWidth: 50,
      minWidth: 50,
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      headerName: "User ID",
      field: "userID",
      filter: true,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "First Name",
      field: "firstName",
      filter: true,
      minWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Mid Name",
      field: "midName",
      filter: true,
      suppressMenu: true,
      minWidth: 130,
      maxWidth: 130,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Last Name",
      field: "lastName",
      filter: true,
      minWidth: 350,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Office",
      field: "officename.officename",
      filter: true,
      suppressMenu: true,
      minWidth: 160,
      maxWidth: 160,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Position",
      field: "position",
      filter: true,
      suppressMenu: true,
      minWidth: 160,
      maxWidth: 160,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Phone",
      field: "phoneNumber",
      filter: true,
      minWidth: 160,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Email",
      field: "email",
      filter: true,
      suppressMenu: true,
      minWidth: 220,
      maxWidth: 220,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Administrator",
      field: "administrator",
      filter: true,
      suppressMenu: true,
      minWidth: 120,
      maxWidth: 120,
      sortable: true,
      resizable: true,
      cellRenderer: "checkIconCellRenderer",
    },
    {
      headerName: "Expired",
      field: "expiredUser",
      filter: true,
      suppressMenu: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
      cellRenderer: "checkIconCellRenderer",
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
      colId: "officeid",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
