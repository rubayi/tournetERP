import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ICdcdForm {
  cdCdUuid: number | 0;
  cardNumber: string | '';
  mngNameKor: string | '';
  mngNameEng: string | '';
  expMonth: string | '';
  expYear: string | '';
  nameOnCard: string | '';
  beginDt: string | '';
  endDt: string | '';
  useYn: number | 0;
}

export class CdcdForm implements ICdcdForm {
  public cdCdUuid: number | 0;
  public cardNumber: string | '';
  public mngNameKor: string | '';
  public mngNameEng: string | '';
  public expMonth: string | '';
  public expYear: string | '';
  public nameOnCard: string | '';
  public beginDt: string | '';
  public endDt: string | '';
  public useYn: number | 0;

  constructor(obj?: ICdcdForm) {
    this.cdCdUuid = getNumberMemberValue(obj && obj.cdCdUuid);
    this.cardNumber = (obj && obj.cardNumber) || '';
    this.mngNameKor = (obj && obj.mngNameKor) || '';
    this.mngNameEng = (obj && obj.mngNameEng) || '';
    this.expMonth = (obj && obj.expMonth) || '';
    this.expYear = (obj && obj.expYear) || '';
    this.nameOnCard = (obj && obj.nameOnCard) || '';
    this.beginDt = (obj && obj.beginDt) || '';
    this.endDt = (obj && obj.endDt) || '';
    this.useYn = (obj && obj.useYn) || 0;
  }
}
