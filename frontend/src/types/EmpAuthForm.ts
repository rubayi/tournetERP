import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IEmpAuthForm {
  empAuthUuid: number | 0;
  empUuid: number | 0;
  menuAuthUuid: number | 0;
}

export class EmpAuthForm implements IEmpAuthForm {
  public empAuthUuid: number | 0;
  public empUuid: number | 0;
  public menuAuthUuid: number | 0;

  constructor(obj?: IEmpAuthForm) {
    this.empAuthUuid = getNumberMemberValue(obj && obj.empAuthUuid);
    this.empUuid = getNumberMemberValue(obj && obj.empUuid);
    this.menuAuthUuid = getNumberMemberValue(obj && obj.menuAuthUuid);
  }
}
