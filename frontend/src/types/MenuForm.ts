import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IMenuForm {
  menuUuid: number | 0;
  upperMenuUuid: number | 0;
  menuLvl: number | 0;
  menuKor: string | '';
  menuEng: string | '';
  menuUrl: string | '';
  useYn: string | '';
  menuOrd: number | 1;
  menuIcon: string | 'label';
  menuDesc: string | '';
  exUrl: string | '';
}

export class MenuForm implements IMenuForm {
  public menuUuid: number | 0;
  public upperMenuUuid: number | 0;
  public menuLvl: number | 0;
  public menuKor: string | '';
  public menuEng: string | '';
  public menuUrl: string | '';
  public useYn: string | '';
  public menuOrd: number | 1;
  public menuIcon: string | 'label';
  public menuDesc: string | '';
  public exUrl: string | '';

  constructor(obj?: IMenuForm) {
    this.menuUuid = getNumberMemberValue(obj && obj.menuUuid);
    this.upperMenuUuid = (obj && obj.upperMenuUuid) || 0;
    this.menuLvl = (obj && obj.menuLvl) || 0;
    this.menuKor = (obj && obj.menuKor) || '';
    this.menuEng = (obj && obj.menuEng) || '';
    this.menuUrl = (obj && obj.menuUrl) || '';
    this.useYn = (obj && obj.useYn) || '';
    this.menuOrd = (obj && obj.menuOrd) || 1;
    this.menuIcon = (obj && obj.menuIcon) || 'label';
    this.menuDesc = (obj && obj.menuDesc) || '';
    this.exUrl = (obj && obj.exUrl) || '';
  }
}
