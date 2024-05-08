import { ColDef, ValueFormatterParams } from "ag-grid-community";
import OpenButtonCellRenderer from "src/components/table/OpenButtonCellRenderer.vue";
import DateHelper from "src/utils/helpers/DateHelper";
import TooltipComponent from "src/components/table/TooltipComponent.vue";
import i18n from 'src/i18n';

export class CompFormTableConfig {
  static reportHeaderMap = {
    callid: "NO",
  };

  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static getColumns(locale: string): ColDef[] {
    return [
    {
      headerName: "",
      field: "compUuid",
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
      headerName: i18n.global.t('compgroup'),
      field: "compGroupName",
      filter: true,
      minWidth: 50,
      sortable: true,
      resizable: true,
    },
    {
      headerName: i18n.global.t('compsector'),
      field: "CompSectorName",
      filter: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Company",
      field: "compKor",
      filter: true,
      minWidth: 150,
      maxWidth: 150,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Company(En)",
      field: "compEng",
      filter: true,
      minWidth: 100,
      maxWidth: 100,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Establish Date",
      field: "estDate",
      filter: true,
      minWidth: 110,
      maxWidth: 110,
      sortable: true,
      resizable: true,
      valueFormatter: (params: ValueFormatterParams): string => {
        return DateHelper.formatISOStringToShortDateString(params.value);
      },
    },
    {
      headerName: "Comp Rate",
      field: "compRate",
      filter: true,
      minWidth: 90,
      maxWidth: 90,
      sortable: true,
      resizable: true,
    },
    {
      headerName: "Hotel Rate",
      field: "hotelRateName",
      filter: true,
      minWidth: 180,
      maxWidth: 180,
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
      headerName: "Created Date",
      field: "createdDt",
      filter: true,
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
  }
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
