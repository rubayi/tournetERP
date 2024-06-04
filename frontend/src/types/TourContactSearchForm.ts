export interface ITourContactSearchForm {
  tourUuid: number | 0;
}
export class TourContactSearchForm implements ITourContactSearchForm {
  public tourUuid: number | 0;

  constructor(obj?: ITourContactSearchForm) {
    this.tourUuid = (obj && obj.tourUuid) || 0;
  }
}
