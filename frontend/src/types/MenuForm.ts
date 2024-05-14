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
  icon: string | '';
  link: string | '';
  label: string | '';
  hide: boolean | false;
  focused: boolean | false;
  expandChildren: boolean | false;
  headerLabel: string | '';
  children?: MenuForm[];
  openMenuOnHover: boolean | false;
  replace: boolean | false;
  expanded?: boolean | false;
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
  public icon: string | '';
  public link: string | '';
  public label: string | '';
  public hide: boolean | false;
  public focused: boolean | false;
  public expandChildren: boolean | false;
  public headerLabel: string | '';
  public children?: MenuForm[];
  public openMenuOnHover: boolean | false;
  public replace: boolean | false;
  public expanded?: boolean | false;

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
    this.icon = (obj && obj.icon) || '';
    this.link = (obj && obj.link) || '';
    this.label = (obj && obj.label) || '';
    this.hide = (obj && obj.hide) || false;
    this.focused = (obj && obj.focused) || false;
    this.expandChildren = (obj && obj.expandChildren) || false;
    this.headerLabel = (obj && obj.headerLabel) || '';
    this.children = (obj && obj.children) || [];
    this.openMenuOnHover = (obj && obj.openMenuOnHover) || false;
    this.replace = (obj && obj.replace) || false;
    this.expanded = (obj && obj.expanded) || false;
  }
}
