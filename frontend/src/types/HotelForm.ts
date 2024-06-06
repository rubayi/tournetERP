import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IHotelForm {
  hotelUuid: number | 0;
  tourUuid: number | 0;
  checkinTime: string | '';
  checkoutTime: string | '';
  resortFee: number | 0;
  childAgeBreakfast: number | 0;
  couponYn: number | 0;
}

export class HotelForm implements IHotelForm {
  public hotelUuid: number | 0;
  public tourUuid: number | 0;
  public checkinTime: string | '';
  public checkoutTime: string | '';
  public resortFee: number | 0;

  public childAgeBreakfast: number | 0;
  public couponYn: number | 0;

  constructor(obj?: IHotelForm) {
    this.hotelUuid = getNumberMemberValue(obj && obj.hotelUuid);
    this.tourUuid = (obj && obj.tourUuid) || 0;
    this.checkinTime = (obj && obj.checkinTime) || '';
    this.checkoutTime = (obj && obj.checkoutTime) || '';
    this.resortFee = (obj && obj.resortFee) || 0;

    this.childAgeBreakfast = (obj && obj.childAgeBreakfast) || 0;
    this.couponYn = (obj && obj.couponYn) || 0;

  }
}
