import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";

export interface ICodeForm {
  codeUuid: number | 0;
  uprCodeUuid: number | 0;
  codeLvl: number | 0;
  codeKr: string | null;
  codeEn: string | null;
  etc: string | null;
  useYn: string | null;
  codeOrd: number | 1;
}

export class CodeForm implements ICodeForm {
  public codeUuid: number | 0;
  public uprCodeUuid: number | 0;
  public codeLvl: number | 0;
  public codeKr: string | null;
  public codeEn: string | null;
  public etc: string | null;
  public useYn: string | null;
  public codeOrd: number | 1;

  constructor(obj?: ICodeForm) {
    this.codeUuid = getNumberMemberValue(obj && obj.codeUuid);
    this.uprCodeUuid = (obj && obj.uprCodeUuid) || 0;
    this.codeLvl = (obj && obj.codeLvl) || 0;
    this.codeKr = (obj && obj.codeKr) || "";
    this.codeEn = (obj && obj.codeEn) || "";
    this.etc = (obj && obj.etc) || "";
    this.useYn = (obj && obj.useYn) || "";
    this.codeOrd = (obj && obj.codeOrd) || 1;
  }
}
