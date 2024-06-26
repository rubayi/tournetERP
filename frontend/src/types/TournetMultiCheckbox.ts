export interface ITournetMultiCheckbox {
  idx: number | 0;
  codevalue: number | 0;
  fullValue: string | '';
}

export class TournetMultiCheckbox implements ITournetMultiCheckbox {
  public idx: number | 0;
  public codevalue: number | 0;
  public fullValue: string | '';
  constructor(obj?: ITournetMultiCheckbox) {
    this.idx = (obj && obj.idx) || 0;
    this.codevalue = (obj && obj.codevalue) || 0;
    this.fullValue = (obj && obj.fullValue) || '';
  }
}
