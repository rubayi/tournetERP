import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";

export interface ICdcdForm {
  cdCdUuid: number | 0;
  cardNumber: string | null;
  mngNameKor: string | null;
  mngNameEng: string | null;
  expMonth: string | null;
  expYear: string | null;
  nameOnCard: string | null;
  beginDt: string | null;
  endDt: string | null;
  useYn: number | 0;
}

export class CdcdForm implements ICdcdForm {
  public cdCdUuid: number | 0;
  public cardNumber: string | null;
  public mngNameKor: string | null;
  public mngNameEng: string | null;
  public expMonth: string | null;
  public expYear: string | null;
  public nameOnCard: string | null;
  public beginDt: string | null;
  public endDt: string | null;
  public useYn: number | 0;

  constructor(obj?: ICdcdForm) {
    this.cdCdUuid = getNumberMemberValue(obj && obj.cdCdUuid);
    this.cardNumber = (obj && obj.cardNumber) || "";
    this.mngNameKor = (obj && obj.mngNameKor) || "";
    this.mngNameEng = (obj && obj.mngNameEng) || "";
    this.expMonth = (obj && obj.expMonth) || "";
    this.expYear = (obj && obj.expYear) || "";
    this.nameOnCard = (obj && obj.nameOnCard) || "";
    this.beginDt = (obj && obj.beginDt) || "";
    this.endDt = (obj && obj.endDt) || "";
    this.useYn = (obj && obj.useYn) || 0;
  }
}
