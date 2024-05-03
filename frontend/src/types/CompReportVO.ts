import { CompForm } from "src/types/CompForm";

export interface IReportVO {
  title: string;
}

export interface IListReportVO {
  sort: string;
  filter: string;
}

export class CompListReportVO implements IReportVO, IListReportVO {
  public title: string;
  public sort: string;
  public filter: string;
  public data: CompForm[];

  constructor(obj?: CompListReportVO) {
    this.title = (obj && obj.title) || "";
    this.sort = (obj && obj.sort) || "";
    this.filter = (obj && obj.filter) || "";
    this.data = (obj && obj.data) || [];
  }
}
