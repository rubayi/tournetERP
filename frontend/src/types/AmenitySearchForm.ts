export interface IAmenitySearchForm {
  tourUuid: number | 0;
  amenityUuid: number | 0;
}
export class AmenitySearchForm implements IAmenitySearchForm {
  public tourUuid: number | 0;
  public amenityUuid: number | 0;

  constructor(obj?: IAmenitySearchForm) {
    this.tourUuid = (obj && obj.tourUuid) || 0;
    this.amenityUuid = (obj && obj.amenityUuid) || 0;
  }
}
