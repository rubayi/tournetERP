import { getNumberMemberValue } from "@/utils/helpers/ConstructorHelper";

export interface ICodeForm {
  codeSeq: number | null;
  code1: number | 0;
  code2: number | 0;
  codegroupname: string | null;
  codename: string | null;
  desciption: string | null;
  certduration: string | null;
  UserName: string | null;
  codeord: number | 1;
  lastUpdated: string | null;
}

export class CodeForm implements ICodeForm {
  public codeSeq: number | null;
  public code1: number | 0;
  public code2: number | 0;
  public codegroupname: string | null;
  public codename: string | null;
  public desciption: string | null;
  public certduration: string | null;
  public UserName: string | null;
  public codeord: number | 1;
  public lastUpdated: string | null;

  constructor(obj?: ICodeForm) {
    this.codeSeq = getNumberMemberValue(obj && obj.codeSeq);
    this.code1 = (obj && obj.code1) || 0;
    this.code2 = (obj && obj.code2) || 0;
    this.codegroupname = (obj && obj.codegroupname) || "";
    this.codename = (obj && obj.codename) || "";
    this.desciption = (obj && obj.desciption) || "";
    this.certduration = (obj && obj.certduration) || "";
    this.UserName = (obj && obj.UserName) || "";
    this.codeord = (obj && obj.codeord) || 1;
    this.lastUpdated = (obj && obj.lastUpdated) || "";
  }
}
