import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';
import { CodeForm } from 'src/types/CodeForm';

export interface IReportForm {
  reportId: number | 0;
  reportName: string | '';
  reporttitle: string | '';
  reportType: number | 0;
  reportTypeName: CodeForm;
  sortord: number | 0;
  expired: boolean | false;
  reportlevel: number | 0;
  reportlevelName: CodeForm;
  methodName: string | '';
  userNameId: string | '';
  lastUpdated: string | '';
  reportvarname: string | '';
  reportvarname02: string | '';
}

export class ReportForm implements IReportForm {
  public reportId: number | 0;
  public reportName: string | '';
  public reporttitle: string | '';
  public reportType: number | 0;
  public reportTypeName: CodeForm;
  public sortord: number | 0;
  public expired: boolean | false;
  public reportlevel: number | 0;
  public reportlevelName: CodeForm;
  public methodName: string | '';
  public userNameId: string | '';
  public lastUpdated: string | '';
  public reportvarname: string | '';
  public reportvarname02: string | '';

  constructor(obj?: IReportForm) {
    this.reportId = getNumberMemberValue(obj && obj.reportId);
    this.reportName = (obj && obj.reportName) || '';
    this.reporttitle = (obj && obj.reporttitle) || '';
    this.reportType = (obj && obj.reportType) || 0;
    this.reportTypeName = (obj && obj.reportTypeName) || new CodeForm();
    this.sortord = (obj && obj.sortord) || 0;
    this.expired = (obj && obj.expired) || false;
    this.reportlevel = (obj && obj.reportlevel) || 0;
    this.reportlevelName = (obj && obj.reportlevelName) || new CodeForm();
    this.methodName = (obj && obj.methodName) || '';
    this.userNameId = (obj && obj.userNameId) || '';
    this.lastUpdated = (obj && obj.lastUpdated) || '';
    this.reportvarname = (obj && obj.reportvarname) || '';
    this.reportvarname02 = (obj && obj.reportvarname02) || '';
  }
}
