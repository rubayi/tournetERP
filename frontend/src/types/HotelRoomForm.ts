import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IHotelRoomForm {
  roomUuid: number | 0;
  serviceUuid: number | 0;
  infoType: number | 0;
  roomInfo: number | 0;

}

export class HotelRoomForm implements IHotelRoomForm {
  public roomUuid: number | 0;
  public serviceUuid: number | 0;
  public infoType: number | 0;
  public roomInfo: number | 0;


  constructor(obj?: IHotelRoomForm) {
    this.roomUuid = getNumberMemberValue(obj && obj.roomUuid);
    this.infoType = (obj && obj.infoType) || 0;
    this.roomInfo = (obj && obj.roomInfo) || 0;
    this.serviceUuid = (obj && obj.serviceUuid) || 0;
  }
}
