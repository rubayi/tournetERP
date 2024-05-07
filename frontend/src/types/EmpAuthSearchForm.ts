export interface IEmpAuthSearchForm {
  empAuthUuid: number | 0;
  empUuid: number | 0;
  menuAuthUuid: number | 0;
}

export class EmpAuthSearchForm implements IEmpAuthSearchForm {
  public empAuthUuid: number | 0;
  public empUuid: number | 0;
  public menuAuthUuid: number | 0;

  constructor(obj?: IEmpAuthSearchForm) {
    this.empAuthUuid = (obj && obj.empAuthUuid) || 0;
    this.empUuid = (obj && obj.empUuid) || 0;
    this.menuAuthUuid = (obj && obj.menuAuthUuid) || 0;
  }
}
