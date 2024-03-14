import { getNumberMemberValue } from "../utils/helpers/ConstructorHelper";
import { CodeForm } from "../types/CodeForm";

export interface IOfficeForm {
  officeid: number | null;
  officeIsland: number | 0;
  officeIslandname: CodeForm;
  officedepartment: number | 0;
  officedepartmentname: CodeForm;
  officename: string | null;
  officedescription: string | null;
  officeaddress: string | null;
  officetel: string | null;
  officefax: string | null;
  UserName: string | null;
  lastUpdated: string | null;
}

export class OfficeForm implements IOfficeForm {
  public officeid: number | null;
  public officeIsland: number | 0;
  public officeIslandname: CodeForm;
  public officedepartment: number | 0;
  public officedepartmentname: CodeForm;
  public officename: string | null;
  public officedescription: string | null;
  public officeaddress: string | null;
  public officetel: string | null;
  public officefax: string | null;
  public UserName: string | null;
  public lastUpdated: string | null;

  constructor(obj?: IOfficeForm) {
    this.officeid = getNumberMemberValue(obj && obj.officeid);
    this.officeIsland = (obj && obj.officeIsland) || 0;
    this.officeIslandname = (obj && obj.officeIslandname) || new CodeForm();
    this.officedepartment = (obj && obj.officedepartment) || 0;
    this.officedepartmentname =
      (obj && obj.officedepartmentname) || new CodeForm();
    this.officename = (obj && obj.officename) || "";
    this.officedescription = (obj && obj.officedescription) || "";
    this.officeaddress = (obj && obj.officeaddress) || "";
    this.officetel = (obj && obj.officetel) || "";
    this.officefax = (obj && obj.officefax) || "";
    this.UserName = (obj && obj.UserName) || "";
    this.lastUpdated = (obj && obj.lastUpdated) || "";
  }
}
