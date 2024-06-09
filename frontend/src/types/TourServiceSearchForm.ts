export interface ITourServiceSearchForm {
  tourUuid: number | 0;
}
export class TourServiceSearchForm implements ITourServiceSearchForm {
  public tourUuid: number | 0;

  constructor(obj?: ITourServiceSearchForm) {
    this.tourUuid = (obj && obj.tourUuid) || 0;
  }
}
