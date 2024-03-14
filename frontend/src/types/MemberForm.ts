// MemberFormClass.ts
import { MemberFormInterface } from './MemberFormInterface';
import { getNumberMemberValue } from "@/utils/helpers/ConstructorHelper";
import { MemberPermissionForm } from "@/types/MemberPermissionForm";

export class MemberForm implements MemberFormInterface {
  public empUuid: number | null;
  public username: string | null;
  public empKor: string | null;
  public empEng: string | null;
  public empWorkType: string | null;
  public empDiv: string | null;
  public password: string | null;
  public empEmail: string | null;
  public empEmailBook: string | null;
  public empPhone: string | null;
  public empWorkPhone: string | null;
  public email: string | null;
  public empStatus: string | null;
  public empDob: string | null;
  public empDobType: string | false;
  public empAddress1: string | null;
  public empAddress2: string | null;
  public empState: string | null;
  public empCountry: string | null;
  public empZip: string | null;
  public empTitle: string | null;
  public empRole: string | null;
  public empMemo: string | null;

  public securityForm: MemberPermissionForm;

  constructor(obj?: MemberFormInterface) {
    this.empUuid = getNumberMemberValue(obj && obj.empUuid);
    this.username = (obj && obj.username) || "";
    this.empKor = (obj && obj.empKor) || "";
    this.empEng = (obj && obj.empEng) || "";
    this.empWorkType = (obj && obj.empWorkType) || "";
    this.empDiv = (obj && obj.empDiv) || "";
    this.password = (obj && obj.password) || "";
    this.empEmail = (obj && obj.empEmail) || "";
    this.empPhone = (obj && obj.empPhone) || "";
    this.empWorkPhone = (obj && obj.empWorkPhone) || "";
    this.email = (obj && obj.email) || "";
    this.empEmailBook = (obj && obj.empEmailBook) || "";
    this.empStatus = (obj && obj.empStatus) || "";
    this.empDob = (obj && obj.empDob) || "";
    this.empDobType = (obj && obj.empDobType) || "";
    this.empAddress1 = (obj && obj.empAddress1) || "";
    this.empAddress2 = (obj && obj.empAddress2) || "";

    this.empState = (obj && obj.empState) || "";
    this.empCountry = (obj && obj.empCountry) || "";

    this.empZip = (obj && obj.empZip) || "";
    this.empTitle = (obj && obj.empTitle) || "";
    this.empRole = (obj && obj.empRole) || "";

    this.empMemo = (obj && obj.empMemo) || "";

    this.securityForm = (obj && obj.securityForm) || new MemberPermissionForm();
  }
}
