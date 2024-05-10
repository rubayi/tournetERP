import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';
import { CodeForm } from 'src/types/CodeForm';

export interface ITournetAttachmentForm {
  fildid: number | 0;
  worknum: number | 0;
  attachtype: number | 0;
  attachtypename: CodeForm;
  fileattachmentlocation: string | '';
  origfilename: string | '';
  filename: string | '';
  filenameext: string | '';
  userNameID: string | '';
  lastUpdated: string | '';
}

export class TournetAttachmentForm implements ITournetAttachmentForm {
  public fildid: number | 0;
  public worknum: number | 0;
  public attachtype: number | 0;
  public attachtypename: CodeForm;
  public fileattachmentlocation: string | '';
  public origfilename: string | '';
  public filename: string | '';
  public filenameext: string | '';
  public userNameID: string | '';
  public lastUpdated: string | '';

  constructor(obj?: ITournetAttachmentForm) {
    this.fildid = getNumberMemberValue(obj && obj.fildid);
    this.worknum = (obj && obj.worknum) || 0;
    this.attachtype = (obj && obj.attachtype) || 0;
    this.attachtypename = (obj && obj.attachtypename) || new CodeForm();
    this.fileattachmentlocation = (obj && obj.fileattachmentlocation) || '';
    this.origfilename = (obj && obj.origfilename) || '';
    this.filename = (obj && obj.filename) || '';
    this.filenameext = (obj && obj.filenameext) || '';
    this.userNameID = (obj && obj.userNameID) || '';
    this.lastUpdated = (obj && obj.lastUpdated) || '';
  }
}
