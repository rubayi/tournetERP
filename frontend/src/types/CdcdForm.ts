import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";

export interface ICdcdForm {
  cdCdUuid: number | 0;
  cardNumber: number | 0;
  mngNameKor: string | null;
  mngNameEng: string | null;
  expMonth: string | null;
  expYear: string | null;
  nameOnCard: string | null;
}

export class CdcdForm implements ICdcdForm {
  public cdCdUuid: number | 0;
  public cardNumber: number | 0;
  public mngNameKor: string | null;
  public mngNameEng: string | null;
  public expMonth: string | null;
  public expYear: string | null;
  public nameOnCard: string | null;

  constructor(obj?: ICdcdForm) {
    this.cdCdUuid = getNumberMemberValue(obj && obj.cdCdUuid);
    this.cardNumber = (obj && obj.cardNumber) || 0;
    this.mngNameKor = (obj && obj.mngNameKor) || "";
    this.mngNameEng = (obj && obj.mngNameEng) || "";
    this.expMonth = (obj && obj.mngNameKor) || "";
    this.expYear = (obj && obj.mngNameEng) || "";
    this.nameOnCard = (obj && obj.nameOnCard) || "";
  }
}
