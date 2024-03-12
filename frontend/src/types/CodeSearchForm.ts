export interface ICodeSearchForm {
  searchcode1: number | 0;
  searchname: string | null;
}

export class CodeSearchForm implements ICodeSearchForm {
  public searchcode1: number | 0;
  public searchname: string | null;

  constructor(obj?: ICodeSearchForm) {
    this.searchcode1 = (obj && obj.searchcode1) || 0;
    this.searchname = (obj && obj.searchname) || "";
  }
}
