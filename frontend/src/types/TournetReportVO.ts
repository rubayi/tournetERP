import { TournetForm } from "src/types/TournetForm";

export interface IReportVO {
  title: string;
}

export interface IListReportVO {
  sort: string;
  filter: string;
}

export class TournetListReportVO implements IReportVO, IListReportVO {
  public title: string;
  public sort: string;
  public filter: string;
  public data: TournetForm[];

  constructor(obj?: TournetListReportVO) {
    this.title = (obj && obj.title) || "";
    this.sort = (obj && obj.sort) || "";
    this.filter = (obj && obj.filter) || "";
    this.data = (obj && obj.data) || [];
  }
}
