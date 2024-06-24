export interface IHotelRoomSearchForm {
  searchRoomUuid: number | 0;
  searchInfoType: number | 0;
  searchRoomInfo: number | 0;
  searchServiceUuid: number | 0;
}

export class HotelRoomSearchForm implements IHotelRoomSearchForm {
  public searchRoomUuid: number | 0;
  public searchInfoType: number | 0;
  public searchRoomInfo: number | 0;
  public searchServiceUuid: number | 0;

  constructor(obj?: IHotelRoomSearchForm) {
    this.searchRoomUuid = (obj && obj.searchRoomUuid) || 0;
    this.searchInfoType = (obj && obj.searchInfoType) || 0;
    this.searchRoomInfo = (obj && obj.searchRoomInfo) || 0;
    this.searchServiceUuid = (obj && obj.searchServiceUuid) || 0;
  }
}
