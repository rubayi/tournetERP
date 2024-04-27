import {
  getNumberMemberValue,
  getStringMemberValue,
} from "src/utils/helpers/ConstructorHelper";

export interface IAppCodeType {
  id: number;
  namekr: string | null;
  nameen: string | null;
  namejp: string | null;
}

export class AppCodeType implements IAppCodeType {
  public id: number;
  public namekr: string | null;
  public nameen: string | null;
  public namejp: string | null;

  constructor(obj?: IAppCodeType) {
    this.id = (obj && obj.id) || 0;
    this.namekr = getStringMemberValue(obj?.namekr);
    this.nameen = getStringMemberValue(obj?.nameen);
    this.namejp = getStringMemberValue(obj?.namejp);
  }
}
