export interface IInfoSearchForm {
  tourUuid: number | 0;
}
export class InfoSearchForm implements IInfoSearchForm {
  public tourUuid: number | 0;

  constructor(obj?: IInfoSearchForm) {
    this.tourUuid = (obj && obj.tourUuid) || 0;
  }
}
