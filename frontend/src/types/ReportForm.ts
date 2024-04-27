import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";
import { CodeForm } from "src/types/CodeForm";

export interface IReportForm {
  reportId: number | 0;
  reportName: string | null;
  reporttitle: string | null;
  reportType: number | 0;
  reportTypeName: CodeForm;
  sortord: number | 0;
  expired: boolean | false;
  reportlevel: number | 0;
  reportlevelName: CodeForm;
  methodName: string | null;
  userNameId: string | null;
  lastUpdated: string | null;
  reportvarname: string | null;
  reportvarname02: string | null;
}

export class ReportForm implements IReportForm {
  public reportId: number | 0;
  public reportName: string | null;
  public reporttitle: string | null;
  public reportType: number | 0;
  public reportTypeName: CodeForm;
  public sortord: number | 0;
  public expired: boolean | false;
  public reportlevel: number | 0;
  public reportlevelName: CodeForm;
  public methodName: string | null;
  public userNameId: string | null;
  public lastUpdated: string | null;
  public reportvarname: string | null;
  public reportvarname02: string | null;

  constructor(obj?: IReportForm) {
    this.reportId = getNumberMemberValue(obj && obj.reportId);
    this.reportName = (obj && obj.reportName) || "";
    this.reporttitle = (obj && obj.reporttitle) || "";
    this.reportType = (obj && obj.reportType) || 0;
    this.reportTypeName = (obj && obj.reportTypeName) || new CodeForm();
    this.sortord = (obj && obj.sortord) || 0;
    this.expired = (obj && obj.expired) || false;
    this.reportlevel = (obj && obj.reportlevel) || 0;
    this.reportlevelName = (obj && obj.reportlevelName) || new CodeForm();
    this.methodName = (obj && obj.methodName) || "";
    this.userNameId = (obj && obj.userNameId) || "";
    this.lastUpdated = (obj && obj.lastUpdated) || "";
    this.reportvarname = (obj && obj.reportvarname) || "";
    this.reportvarname02 = (obj && obj.reportvarname02) || "";
  }
}
