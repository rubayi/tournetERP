import {
  getDateMemberValue,
  getStringMemberValue,
} from "src/utils/helpers/ConstructorHelper";
import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";
import { CodeForm } from "src/types/CodeForm";
import { TournetAttachmentForm } from "src/types/TournetAttachmentForm";
import { TournetDocumentsgForm } from "src/types/TournetDocumentsgForm";
import { TournetWorkorderlogForm } from "src/types/TournetWorkorderlogForm";
import { TournetMultiCheckbox } from "src/types/TournetMultiCheckbox";
import { AppCodeType } from "src/types/AppCodeType";


export interface ITournetForm {
  worknum: number | 0;
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

  workorderlogform: TournetWorkorderlogForm;
  attachmentlist: TournetAttachmentForm[];
  documentslist: TournetDocumentsgForm[];
  crewassignlist: TournetMultiCheckbox[];
  memostatueslist: TournetMultiCheckbox[];

  fullStatues: string;
  fullCrewassign: string;
}

export class TournetForm implements ITournetForm {
  public worknum: number | 0;
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

  public workorderlogform: TournetWorkorderlogForm;
  public attachmentlist: TournetAttachmentForm[];
  public documentslist: TournetDocumentsgForm[];
  public crewassignlist: TournetMultiCheckbox[];
  public memostatueslist: TournetMultiCheckbox[];
  public fullStatues: string;
  public fullCrewassign: string;

  constructor(obj?: ITournetForm) {
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
      (obj && obj.workorderlogform) || new TournetWorkorderlogForm();
    this.attachmentlist = (obj && obj.attachmentlist) || [];
    this.documentslist = (obj && obj.documentslist) || [];
    this.crewassignlist = (obj && obj.crewassignlist) || [];
    this.memostatueslist = (obj && obj.memostatueslist) || [];
    this.fullStatues = getStringMemberValue(obj?.fullStatues);
    this.fullCrewassign = getStringMemberValue(obj?.fullCrewassign);
  }
}
