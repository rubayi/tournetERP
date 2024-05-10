import { CodeForm } from 'src/types/CodeForm';
import { getDateMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ICompWorkorderlogForm {
  worknum: number | null;
  receiveddate: string | null;
  receivedby: string | null;
  datecrewassigned: string | null;
  datecompleted: string | null;
  crewassignedname: CodeForm;
  comments: string | null;
  posteddate: string | null;
  postedby: string | null;
  datesentbacktotraffic: string | null;
  userNameID: string | null;
  lastUpdated: string | null;
}

export class CompWorkorderlogForm implements ICompWorkorderlogForm {
  public worknum: number | 0;
  public receiveddate: string | null;
  public receivedby: string | null;
  public datecrewassigned: string | null;
  public datecompleted: string | null;
  public crewassignedname: CodeForm;
  public comments: string | null;
  public posteddate: string | null;
  public postedby: string | null;
  public datesentbacktotraffic: string | null;
  public userNameID: string | null;
  public lastUpdated: string | null;

  constructor(obj?: ICompWorkorderlogForm) {
    this.worknum = (obj && obj.worknum) || 0;
    this.receiveddate = getDateMemberValue(obj?.receiveddate);
    this.receivedby = (obj && obj.receivedby) || '';
    this.datecrewassigned = getDateMemberValue(obj?.datecrewassigned);
    this.datecompleted = getDateMemberValue(obj?.datecompleted);
    this.crewassignedname = (obj && obj.crewassignedname) || new CodeForm();
    this.comments = (obj && obj.comments) || '';
    this.posteddate = getDateMemberValue(obj?.posteddate);
    this.postedby = (obj && obj.postedby) || '';
    this.datesentbacktotraffic = getDateMemberValue(obj?.datesentbacktotraffic);
    this.userNameID = (obj && obj.userNameID) || '';
    this.lastUpdated = (obj && obj.lastUpdated) || '';
  }
}
