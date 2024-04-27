export interface IReportSearchForm {
  searchReportGroup: number | 0;
  searchReportType: number | 0;
  searchName: string | null;
}

export class ReportSearchForm implements IReportSearchForm {
  public searchReportGroup: number | 0;
  public searchReportType: number | 0;
  public searchName: string | null;

  constructor(obj?: IReportSearchForm) {
    this.searchReportGroup = (obj && obj.searchReportGroup) || 0;
    this.searchReportType = (obj && obj.searchReportType) || 0;
    this.searchName = (obj && obj.searchName) || "";
  }
}
