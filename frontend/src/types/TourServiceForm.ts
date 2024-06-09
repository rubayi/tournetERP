import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ITourServiceForm {
  serviceUuid: number | 0;
  tourUuid: number | 0;
  departureArea: string | '';
  returnArea: string | '';

  serviceNameKor: string | '';
  serviceNameEng: string | '';
  serviceColor: string | '';

  beginDt: string | '';
  endDt: string | '';
  bookBeginDt: string | '';
  bookEndDt: string | '';
  api: string | '';
}

export class TourServiceForm implements ITourServiceForm {
  public serviceUuid: number | 0;
  public tourUuid: number | 0;
  public departureArea: string | '';
  public returnArea: string | '';

  public serviceNameKor: string | '';
  public serviceNameEng: string | '';
  public serviceColor: string | '';

  public beginDt: string | '';
  public endDt: string | '';
  public bookBeginDt: string | '';
  public bookEndDt: string | '';
  public api: string | '';

  constructor(obj?: ITourServiceForm) {
    this.serviceUuid = getNumberMemberValue(obj && obj.serviceUuid);
    this.tourUuid = getNumberMemberValue(obj && obj.tourUuid);
    this.departureArea = (obj && obj.departureArea)|| '';
    this.returnArea = (obj && obj.returnArea)|| '';

    this.serviceNameKor = (obj && obj.serviceNameKor) || '';
    this.serviceNameEng = (obj && obj.serviceNameEng) || '';
    this.serviceColor = (obj && obj.serviceColor) || '';

    this.beginDt = (obj && obj.beginDt) || '';
    this.endDt = (obj && obj.endDt) || '';
    this.bookBeginDt = (obj && obj.beginDt) || '';
    this.bookEndDt = (obj && obj.endDt) || '';
    this.api = (obj && obj.api) || '';
  }
}
