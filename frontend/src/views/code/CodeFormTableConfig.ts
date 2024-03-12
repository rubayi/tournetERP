import { ColDef } from "ag-grid-community";
import OpenButtonCellRenderer from "@/components/table/OpenButtonCellRenderer.vue";
export class CodeFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns: ColDef[] = [
    {
      headerName: "",
      field: "codeSeq",
      cellClass: "no-border",
      floatingFilter: false,
      cellRenderer: "openButtonCellRenderer",
      maxWidth: 80,
      minWidth: 80,
      sortable: false,
      suppressMovable: true,
      suppressNavigable: true,
    },
    {
      headerName: "Group",
      field: "code1",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 90,
      maxWidth: 90,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code Group Name",
      field: "codegroupname",
      filter: true,
      minWidth: 250,
      maxWidth: 250,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code",
      field: "code2",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Code Name",
      field: "codename",
      filter: true,
      minWidth: 250,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Order",
      field: "codeord",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Desciption",
      field: "desciption",
      filter: true,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "CertDuration",
      field: "certduration",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 140,
      maxWidth: 140,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "LastUpdated",
      field: "lastUpdated",
      floatingFilter: false,
      suppressMenu: true,
      minWidth: 220,
      maxWidth: 220,
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
      colId: "codeSeq",
      sort: "desc",
      sortIndex: 0,
    },
  ];
}
