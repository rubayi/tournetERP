import { ColDef } from "ag-grid-community";
import OpenButtonCellRenderer from "@/components/table/OpenButtonCellRenderer.vue";
export class OfficeFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "officeid",
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
      headerName: "Island",
      field: "officeIslandname.codename",
      filter: true,
      suppressMenu: true,
      minWidth: 80,
      maxWidth: 80,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Department",
      field: "officedepartmentname.codename",
      filter: true,
      minWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Office Name",
      field: "officename",
      filter: true,
      suppressMenu: true,
      minWidth: 130,
      maxWidth: 130,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Address",
      field: "officeaddress",
      filter: true,
      minWidth: 350,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Tel",
      field: "officetel",
      filter: true,
      suppressMenu: true,
      minWidth: 160,
      maxWidth: 160,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Fax",
      field: "officefax",
      filter: true,
      minWidth: 160,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Description",
      field: "officedescription",
      filter: true,
      suppressMenu: true,
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
  };

  static defaultSortModel = [
    {
      colId: "officeid",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
