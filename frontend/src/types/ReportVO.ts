import { HawaiifoodyForm } from "@/types/HawaiifoodyForm";

export interface IReportVO {
  title: string;
}

export interface IListReportVO {
  sort: string;
  filter: string;
}

export class HawaiifoodyListReportVO implements IReportVO, IListReportVO {
  public title: string;
  public sort: string;
  public filter: string;
  public data: HawaiifoodyForm[];

  constructor(obj?: HawaiifoodyListReportVO) {
    this.title = (obj && obj.title) || "";
    this.sort = (obj && obj.sort) || "";
    this.filter = (obj && obj.filter) || "";
    this.data = (obj && obj.data) || [];
  }
}
