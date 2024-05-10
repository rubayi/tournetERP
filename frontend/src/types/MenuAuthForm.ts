import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IMenuAuthForm {
  menuAuthUuid: number | 0;
  authYn: string | '';
  menuAuthName: string | '';
  menuAuthNameEng: string | '';
  groupCode: number | 0;
}

export class MenuAuthForm implements IMenuAuthForm {
  public menuAuthUuid: number | 0;
  public authYn: string | '';
  public menuAuthName: string | '';
  public menuAuthNameEng: string | '';
  public groupCode: number | 0;

  constructor(obj?: IMenuAuthForm) {
    this.menuAuthUuid = getNumberMemberValue(obj && obj.menuAuthUuid);
    this.authYn = (obj && obj.authYn) || '';
    this.menuAuthName = (obj && obj.menuAuthName) || '';
    this.menuAuthNameEng = (obj && obj.menuAuthNameEng) || '';
    this.groupCode = getNumberMemberValue(obj && obj.groupCode);
  }
}
