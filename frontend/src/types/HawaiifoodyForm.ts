import {
  getDateMemberValue,
  getStringMemberValue,
} from "@/utils/helpers/ConstructorHelper";
import { getNumberMemberValue } from "@/utils/helpers/ConstructorHelper";
import { CodeForm } from "@/types/CodeForm";
import { HawaiifoodyAttachmentForm } from "@/types/HawaiifoodyAttachmentForm";
import { HawaiifoodyDocumentsgForm } from "@/types/HawaiifoodyDocumentsgForm";
import { HawaiifoodyWorkorderlogForm } from "@/types/HawaiifoodyWorkorderlogForm";
import { HawaiifoodyMultiCheckbox } from "@/types/HawaiifoodyMultiCheckbox";
import { AppCodeType } from "@/types/AppCodeType";


export interface IHawaiifoodyForm {
  worknum: number | null;
  workno: string | null;
  workdate: string | null;
  langtype: AppCodeType;
  workdep: string | null;
  letterto: string | null;
  letterattn: string | null;
  letterfrom: string | null;
  lettersubject: string | null;
  attached: boolean | true;
  separatesheet: boolean | false;
  workstatusname: CodeForm;
  resubmitcopies: number | 0;
  suspensedate: string | null;
  trafficengineer: string | null;
  copiessendto: string | null;
  remarks: string | null;
  workrouteno: string | null;

  userNameID: string | null;
  lastUpdated: string | null;

  workorderlogform: HawaiifoodyWorkorderlogForm;
  attachmentlist: HawaiifoodyAttachmentForm[];
  documentslist: HawaiifoodyDocumentsgForm[];
  crewassignlist: HawaiifoodyMultiCheckbox[];
  memostatueslist: HawaiifoodyMultiCheckbox[];

  fullStatues: string;
  fullCrewassign: string;
}

export class HawaiifoodyForm implements IHawaiifoodyForm {
  public worknum: number | null;
  public workno: string | null;
  public workdate: string | null;
  public langtype: AppCodeType;
  public workdep: string | null;
  public letterto: string | null;
  public letterattn: string | null;
  public letterfrom: string | "HWY-T";
  public lettersubject: string | null;
  public attached: boolean | true;
  public separatesheet: boolean | false;
  public workstatusname: CodeForm;
  public resubmitcopies: number | 0;
  public suspensedate: string | null;
  public trafficengineer: string | null;
  public copiessendto: string | null;
  public remarks: string | null;
  public workrouteno: string | null;
  public userNameID: string | null;
  public lastUpdated: string | null;

  public workorderlogform: HawaiifoodyWorkorderlogForm;
  public attachmentlist: HawaiifoodyAttachmentForm[];
  public documentslist: HawaiifoodyDocumentsgForm[];
  public crewassignlist: HawaiifoodyMultiCheckbox[];
  public memostatueslist: HawaiifoodyMultiCheckbox[];
  public fullStatues: string;
  public fullCrewassign: string;

  constructor(obj?: IHawaiifoodyForm) {
    this.worknum = getNumberMemberValue(obj && obj.worknum);
    this.workno = (obj && obj.workno) || "";
    this.workdep = (obj && obj.workdep) || "";
    this.langtype = (obj && obj.langtype) || new AppCodeType();
    this.letterto = (obj && obj.letterto) || "HAWAII-OP";
    this.letterattn = (obj && obj.letterattn) || "";
    this.letterfrom = (obj && obj.letterfrom) || "HAWAII-IT";
    this.lettersubject = (obj && obj.lettersubject) || "";
    this.attached = (obj && obj.attached) || true;
    this.separatesheet = (obj && obj.separatesheet) || false;
    this.resubmitcopies = (obj && obj.resubmitcopies) || 0;
    this.suspensedate = getDateMemberValue(obj?.suspensedate);
    this.trafficengineer = (obj && obj.trafficengineer) || "";
    this.copiessendto = (obj && obj.copiessendto) || "";
    this.remarks = (obj && obj.remarks) || "";
    this.workrouteno = (obj && obj.workrouteno) || "";

    this.userNameID = (obj && obj.userNameID) || "";
    this.lastUpdated = (obj && obj.lastUpdated) || "";

    this.workdate = getDateMemberValue(obj?.workdate);
    this.workstatusname = (obj && obj.workstatusname) || new CodeForm();
    this.workorderlogform =
      (obj && obj.workorderlogform) || new HawaiifoodyWorkorderlogForm();
    this.attachmentlist = (obj && obj.attachmentlist) || [];
    this.documentslist = (obj && obj.documentslist) || [];
    this.crewassignlist = (obj && obj.crewassignlist) || [];
    this.memostatueslist = (obj && obj.memostatueslist) || [];
    this.fullStatues = getStringMemberValue(obj?.fullStatues);
    this.fullCrewassign = getStringMemberValue(obj?.fullCrewassign);
  }
}
