import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ICodeForm {
  codeUuid: number | 0;
  uprCodeUuid: number | 0;
  codeLvl: number | 0;
  codeKr: string | '';
  codeEn: string | '';
  codeAbb: string | '';
  uprNameKr: string | '';
  uprNameEn: string | '';
  etc: string | '';
  useYn: string | 'Y';
  codeOrd: number | 1;
}

export class CodeForm implements ICodeForm {
  public codeUuid: number | 0;
  public uprCodeUuid: number | 0;
  public codeLvl: number | 0;
  public codeKr: string | '';
  public codeEn: string | '';
  public codeAbb: string | '';
  public uprNameKr: string | '';
  public uprNameEn: string | '';
  public etc: string | '';
  public useYn: string | 'Y';
  public codeOrd: number | 1;

  constructor(obj?: ICodeForm) {
    this.codeUuid = getNumberMemberValue(obj && obj.codeUuid);
    this.uprCodeUuid = (obj && obj.uprCodeUuid) || 0;
    this.codeLvl = (obj && obj.codeLvl) || 0;
    this.codeKr = (obj && obj.codeKr) || '';
    this.codeEn = (obj && obj.codeEn) || '';
    this.codeAbb = (obj && obj.codeAbb) || '';
    this.uprNameKr = (obj && obj.codeKr) || '';
    this.uprNameEn = (obj && obj.codeEn) || '';
    this.etc = (obj && obj.etc) || '';
    this.useYn = (obj && obj.useYn) || 'Y';
    this.codeOrd = (obj && obj.codeOrd) || 1;
  }
}
