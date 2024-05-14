import { CompForm } from "src/types/CompForm";

export interface IReport {
  title: string;
}

export interface IListReport {
  sort: string;
  filter: string;
}

export class CompListReport implements IReport, IListReport {
  public title: string;
  public sort: string;
  public filter: string;
  public data: CompForm[];

  constructor(obj?: CompListReport) {
    this.title = (obj && obj.title) || "";
    this.sort = (obj && obj.sort) || "";
    this.filter = (obj && obj.filter) || "";
    this.data = (obj && obj.data) || [];
  }
}
