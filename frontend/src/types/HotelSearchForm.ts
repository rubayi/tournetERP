export interface IHotelSearchForm {
  tourUuid: number | 0;
  hotelUuid: number | 0;
  hotelGrp: number | 0;
}
export class HotelSearchForm implements IHotelSearchForm {
  public tourUuid: number | 0;
  public hotelUuid: number | 0;
  public hotelGrp: number | 0;

  constructor(obj?: IHotelSearchForm) {
    this.tourUuid = (obj && obj.tourUuid) || 0;
    this.hotelUuid = (obj && obj.hotelUuid) || 0;
    this.hotelGrp = (obj && obj.hotelGrp) || 0;
  }
}
