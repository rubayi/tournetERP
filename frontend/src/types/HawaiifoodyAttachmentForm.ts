import { getNumberMemberValue } from "@/utils/helpers/ConstructorHelper";
import { CodeForm } from "@/types/CodeForm";

export interface IHawaiifoodyAttachmentForm {
  fildid: number | null;
  worknum: number | 0;
  attachtype: number | 0;
  attachtypename: CodeForm;
  fileattachmentlocation: string | null;
  origfilename: string | null;
  filename: string | null;
  filenameext: string | null;
  userNameID: string | null;
  lastUpdated: string | null;
}

export class HawaiifoodyAttachmentForm implements IHawaiifoodyAttachmentForm {
  public fildid: number | null;
  public worknum: number | 0;
  public attachtype: number | 0;
  public attachtypename: CodeForm;
  public fileattachmentlocation: string | null;
  public origfilename: string | null;
  public filename: string | null;
  public filenameext: string | null;
  public userNameID: string | null;
  public lastUpdated: string | null;

  constructor(obj?: IHawaiifoodyAttachmentForm) {
    this.fildid = getNumberMemberValue(obj && obj.fildid);
    this.worknum = (obj && obj.worknum) || 0;
    this.attachtype = (obj && obj.attachtype) || 0;
    this.attachtypename = (obj && obj.attachtypename) || new CodeForm();
    this.fileattachmentlocation = (obj && obj.fileattachmentlocation) || "";
    this.origfilename = (obj && obj.origfilename) || "";
    this.filename = (obj && obj.filename) || "";
    this.filenameext = (obj && obj.filenameext) || "";
    this.userNameID = (obj && obj.userNameID) || "";
    this.lastUpdated = (obj && obj.lastUpdated) || "";
  }
}
