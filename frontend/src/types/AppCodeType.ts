import {
  getNumberMemberValue,
  getStringMemberValue,
} from 'src/utils/helpers/ConstructorHelper';

export interface IAppCodeType {
  id: number;
  namekr: string | '';
  nameen: string | '';
  namejp: string | '';
}

export class AppCodeType implements IAppCodeType {
  public id: number;
  public namekr: string | '';
  public nameen: string | '';
  public namejp: string | '';

  constructor(obj?: IAppCodeType) {
    this.id = getNumberMemberValue(obj && obj.id) || 0;
    this.namekr = getStringMemberValue(obj?.namekr);
    this.nameen = getStringMemberValue(obj?.nameen);
    this.namejp = getStringMemberValue(obj?.namejp);
  }
}
