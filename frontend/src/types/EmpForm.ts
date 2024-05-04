import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IEmpForm {
  empUuid: number | 0;
  empEmail: string | '';
  empKor: string | '';
  password: string | '';
  username: string | '';
  empEng: string | '';
  empImg: string | '';
  empDiv: number | 0;
  empRole: number | 0;
  empTitle: number | 0;
  empPhone: string | '';
  empWorkPhone: string | '';
  empAddress: string | '';
  empDob: string | '';
  empEmailBook: string | '';
  empBeginDt: string | '';
  empMemo: string | '';
  empStatus: number | 0;
  empEndDt: string | '';
  empOffice: number | 0;
  empComp: number | 0;
  modifiedBy: number | 0;
  modifiedDt: Date | '';
  backColor: string | '';
  fontColor: string | '';
}

export class EmpForm implements IEmpForm {
  public empUuid: number | 0;
  public empEmail: string | '';
  public empKor: string | '';
  public password: string | '';
  public username: string | '';
  public empEng: string | '';
  public empImg: string | '';
  public empDiv: number | 0;
  public empRole: number | 0;
  public empTitle: number | 0;
  public empPhone: string | '';
  public empWorkPhone: string | '';
  public empAddress: string | '';
  public empDob: string | '';
  public empEmailBook: string | '';
  public empBeginDt: string | '';
  public empMemo: string | '';
  public empStatus: number | 0;
  public empEndDt: string | '';
  public empOffice: number | 0;
  public empComp: number | 0;
  public modifiedBy: number | 0;
  public modifiedDt: Date | '';
  public backColor: string | '';
  public fontColor: string | '';

  constructor(obj?: IEmpForm) {
    this.empUuid = getNumberMemberValue(obj && obj.empUuid);
    this.empEmail = (obj && obj.empEmail) || '';
    this.empKor = (obj && obj.empKor) || '';
    this.password = (obj && obj.password) || '';
    this.username = (obj && obj.username) || '';
    this.empEng = (obj && obj.empEng) || '';
    this.empImg = (obj && obj.empImg) || '';
    this.empDiv = (obj && obj.empDiv) || 0;
    this.empRole = (obj && obj.empRole) || 0;
    this.empTitle = (obj && obj.empTitle) || 0;
    this.empPhone = (obj && obj.empPhone) || '';
    this.empWorkPhone = (obj && obj.empWorkPhone) || '';
    this.empAddress = (obj && obj.empAddress) || '';
    this.empDob = (obj && obj.empDob) || '';
    this.empEmailBook = (obj && obj.empEmailBook) || '';
    this.empBeginDt = (obj && obj.empBeginDt) || '';
    this.empMemo = (obj && obj.empMemo) || '';
    this.empStatus = (obj && obj.empStatus) || 0;
    this.empEndDt = (obj && obj.empEndDt) || '';
    this.empOffice = (obj && obj.empOffice) || 0;
    this.empComp = (obj && obj.empComp) || 0;
    this.modifiedBy = (obj && obj.modifiedBy) || 0;
    this.modifiedDt = (obj && obj.modifiedDt) || '';
    this.backColor = (obj && obj.backColor) || '';
    this.fontColor = (obj && obj.fontColor) || '';
  }
}
