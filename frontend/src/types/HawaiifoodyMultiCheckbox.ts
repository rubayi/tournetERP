export interface IHawaiifoodyMultiCheckbox {
  idx: number | 0;
  codevalue: number | 0;
  fullValue: string | null;
}

export class HawaiifoodyMultiCheckbox implements IHawaiifoodyMultiCheckbox {
  public idx: number | 0;
  public codevalue: number | 0;
  public fullValue: string | null;
  constructor(obj?: IHawaiifoodyMultiCheckbox) {
    this.idx = (obj && obj.idx) || 0;
    this.codevalue = (obj && obj.codevalue) || 0;
    this.fullValue = (obj && obj.fullValue) || null;
  }
}
