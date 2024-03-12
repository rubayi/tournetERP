import { getNumberMemberValue } from "@/utils/helpers/ConstructorHelper";
import { MemberPermissionForm } from "@/types/MemberPermissionForm";
import { OfficeForm } from "@/types/OfficeForm";

export interface IMemberForm {
  memID: number | null;
  userID: string | null;
  firstName: string | null;
  midName: string | null;
  lastName: string | null;
  position: string | null;
  password: string | null;
  oldPassword: string | null;
  phoneNumber: string | null;
  email: string | null;
  administrator: boolean | false;
  expiredUser: boolean | false;
  displayuser: boolean | false;
  officeid: number | 0;
  officename: OfficeForm;
  reportLevelList: string[];
  userNameID: string | null;
  lastUpdated: string | null;

  securityForm: MemberPermissionForm;
}

export class MemberForm implements IMemberForm {
  public memID: number | null;
  public userID: string | null;
  public firstName: string | null;
  public midName: string | null;
  public lastName: string | null;
  public position: string | null;
  public password: string | null;
  public oldPassword: string | null;
  public phoneNumber: string | null;
  public email: string | null;
  public administrator: boolean | false;

  public expiredUser: boolean | false;
  public displayuser: boolean | false;
  public officeid: number | 0;
  public officename: OfficeForm;
  public reportLevelList: string[];
  public userNameID: string | null;
  public lastUpdated: string | null;
  public securityForm: MemberPermissionForm;

  constructor(obj?: IMemberForm) {
    this.memID = getNumberMemberValue(obj && obj.memID);
    this.userID = (obj && obj.userID) || "";
    this.firstName = (obj && obj.firstName) || "";
    this.midName = (obj && obj.midName) || "";
    this.lastName = (obj && obj.lastName) || "";
    this.position = (obj && obj.position) || "";
    this.password = (obj && obj.password) || "";
    this.oldPassword = (obj && obj.oldPassword) || "";
    this.phoneNumber = (obj && obj.phoneNumber) || "";
    this.email = (obj && obj.email) || "";

    this.administrator = (obj && obj.administrator) || false;
    this.expiredUser = (obj && obj.expiredUser) || false;
    this.displayuser = (obj && obj.displayuser) || false;
    this.officeid = (obj && obj.officeid) || 0;
    this.officename = (obj && obj.officename) || new OfficeForm();
    this.reportLevelList = (obj && obj.reportLevelList) || [];
    this.userNameID = (obj && obj.userNameID) || "";
    this.lastUpdated = (obj && obj.lastUpdated) || "";

    this.securityForm = (obj && obj.securityForm) || new MemberPermissionForm();
  }
}
