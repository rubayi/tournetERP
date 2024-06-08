import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IInfoForm {
  infoUuid: number | 0;
  tourUuid: number | 0;
  infoType: number | 0;

  contentKor: string | '';
  contentEng: string | '';

  applyBeginDt: string | '';
  applyEndDt: string | '';
  applyStandard: string | '';
}

export class InfoForm implements IInfoForm {
  public infoUuid: number | 0;
  public tourUuid: number | 0;
  public infoType: number | 0;

  public contentKor: string | '';
  public contentEng: string | '';

  public applyBeginDt: string | '';
  public applyEndDt: string | '';
  public applyStandard: string | '';

  constructor(obj?: IInfoForm) {
    this.infoUuid = getNumberMemberValue(obj && obj.infoUuid);
    this.tourUuid = getNumberMemberValue(obj && obj.tourUuid);
    this.infoType = getNumberMemberValue(obj && obj.infoType);

    this.contentKor = (obj && obj.contentKor) || '';
    this.contentEng = (obj && obj.contentEng) || '';

    this.applyBeginDt = (obj && obj.applyBeginDt) || '';
    this.applyEndDt = (obj && obj.applyEndDt) || '';
    this.applyStandard = (obj && obj.applyStandard) || '';
  }
}
