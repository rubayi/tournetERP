import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IContactForm {
  contactUuid: number | 0;
  contactCont: string | '';
  contactType: number | 0;
  repYn: number | 0;
  compUuid: number | 0;
}

export class ContactForm implements IContactForm {
  public contactUuid: number | 0;
  public contactCont: string | '';
  public contactType: number | 0;
  public repYn: number | 0;
  public compUuid: number | 0;

  constructor(obj?: IContactForm) {
    this.contactUuid = getNumberMemberValue(obj && obj.contactUuid);
    this.contactCont = (obj && obj.contactCont) || '';
    this.contactType = getNumberMemberValue(obj && obj.contactType);
    this.repYn = getNumberMemberValue(obj && obj.repYn);
    this.compUuid = getNumberMemberValue(obj && obj.compUuid);
  }
}
