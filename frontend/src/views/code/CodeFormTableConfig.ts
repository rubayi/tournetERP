import { ColDef, ICellRendererParams } from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";

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
      field: "uprNameKr",
      floatingFilter: false,
      minWidth: 90,
      maxWidth: 90,
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
      minWidth: 140,
      maxWidth: 140,
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
