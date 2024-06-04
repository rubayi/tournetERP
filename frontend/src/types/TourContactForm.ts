import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ITourContactForm {
  contactUuid: number | 0;
  contactCont: string | '';
  contactType: number | 0;
  repYn: number | 0;
  tourUuid: number | 0;
}

export class TourContactForm implements ITourContactForm {
  public contactUuid: number | 0;
  public contactCont: string | '';
  public contactType: number | 0;
  public repYn: number | 0;
  public tourUuid: number | 0;

  constructor(obj?: ITourContactForm) {
    this.contactUuid = getNumberMemberValue(obj && obj.contactUuid);
    this.contactCont = (obj && obj.contactCont) || '';
    this.contactType = getNumberMemberValue(obj && obj.contactType);
    this.repYn = getNumberMemberValue(obj && obj.repYn);
    this.tourUuid = getNumberMemberValue(obj && obj.tourUuid);
  }
}
