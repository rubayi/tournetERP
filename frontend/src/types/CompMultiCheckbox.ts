export interface ICompMultiCheckbox {
  idx: number | 0;
  codevalue: number | 0;
  fullValue: string | '';
}

export class CompMultiCheckbox implements ICompMultiCheckbox {
  public idx: number | 0;
  public codevalue: number | 0;
  public fullValue: string | '';
  constructor(obj?: ICompMultiCheckbox) {
    this.idx = (obj && obj.idx) || 0;
    this.codevalue = (obj && obj.codevalue) || 0;
    this.fullValue = (obj && obj.fullValue) || '';
  }
}
