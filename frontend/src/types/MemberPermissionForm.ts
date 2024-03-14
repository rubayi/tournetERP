import {getNumberMemberValue} from "@/utils/helpers/ConstructorHelper";

export interface IMemberPermissionForm {
  menuAuthUuid: number | null;
  menuUuid: number | null;
  menuRead: string | null;
  menuWrite: string | null;
  menuUpdate: string | null;
  menuDelete: string | null;
  }

export class MemberPermissionForm implements IMemberPermissionForm {
  public menuAuthUuid: number | null;
  public menuUuid: number | null;
  public menuRead: string | null;
  public menuWrite: string | null;
  public menuUpdate: string | null;
  public menuDelete: string | null;


  constructor(obj?: IMemberPermissionForm) {
    this.menuAuthUuid = getNumberMemberValue(obj && obj.menuAuthUuid);
    this.menuUuid = getNumberMemberValue(obj && obj.menuUuid);
    this.menuRead = (obj && obj.menuRead) || null;
    this.menuUpdate = (obj && obj.menuUpdate) || null;
    this.menuWrite = (obj && obj.menuWrite) || null;
    this.menuDelete = (obj && obj.menuDelete) || null;
  }
}
