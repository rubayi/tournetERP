import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ITourServiceForm {
  serviceUuid: number | 0;
  tourUuid: number | 0;

  pickupYn: number | 0;
  mealYn: number | 0;

  serviceNameKor: string | '';
  serviceNameEng: string | '';
  servicePrice: string | '';
  serviceColor: string | '';

  adults: number | 0;
  children: number | 0;
  maxPeople: number | 0;

  beginDt: string | '';
  endDt: string | '';
  bookBeginDt: string | '';
  bookEndDt: string | '';
  api: string | '';

  departureArea: string | '';
  returnArea: string | '';
}

export class TourServiceForm implements ITourServiceForm {
  public serviceUuid: number | 0;
  public tourUuid: number | 0;

  public pickupYn: number | 0;
  public mealYn: number | 0;

  public serviceNameKor: string | '';
  public serviceNameEng: string | '';
  public servicePrice: string | '';
  public serviceColor: string | '';

  public adults: number | 0;
  public children: number | 0;
  public maxPeople: number | 0;

  public beginDt: string | '';
  public endDt: string | '';
  public bookBeginDt: string | '';
  public bookEndDt: string | '';
  public api: string | '';

  public departureArea: string | '';
  public returnArea: string | '';

  constructor(obj?: ITourServiceForm) {
    this.serviceUuid = getNumberMemberValue(obj && obj.serviceUuid);
    this.tourUuid = getNumberMemberValue(obj && obj.tourUuid);

    this.pickupYn = getNumberMemberValue(obj && obj.pickupYn);
    this.mealYn = getNumberMemberValue(obj && obj.mealYn);

    this.serviceNameKor = (obj && obj.serviceNameKor) || '';
    this.serviceNameEng = (obj && obj.serviceNameEng) || '';
    this.servicePrice = (obj && obj.servicePrice) || '';
    this.serviceColor = (obj && obj.serviceColor) || '';

    this.adults = getNumberMemberValue(obj && obj.adults);
    this.children = getNumberMemberValue(obj && obj.children);
    this.maxPeople = getNumberMemberValue(obj && obj.maxPeople);

    this.beginDt = (obj && obj.beginDt) || '';
    this.endDt = (obj && obj.endDt) || '';
    this.bookBeginDt = (obj && obj.beginDt) || '';
    this.bookEndDt = (obj && obj.endDt) || '';
    this.api = (obj && obj.api) || '';

    this.departureArea = (obj && obj.departureArea)|| '';
    this.returnArea = (obj && obj.returnArea)|| '';
  }
}
