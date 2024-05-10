export interface IEmpAuthSearchForm {
  empAuthUuid?: number | 0;
  empUuid?: number | 0;
  menuAuthUuid?: number | 0;
  menuAuthUuids?: number[];
}

export class EmpAuthSearchForm implements IEmpAuthSearchForm {
  public empAuthUuid: number | 0;
  public empUuid: number | 0;
  public menuAuthUuid: number | 0;
  public menuAuthUuids: number[];

  constructor(obj?: IEmpAuthSearchForm) {
    this.empAuthUuid = (obj && obj.empAuthUuid) || 0;
    this.empUuid = (obj && obj.empUuid) || 0;
    this.menuAuthUuid = (obj && obj.menuAuthUuid) || 0;
    this.menuAuthUuids = (obj && obj.menuAuthUuids) || [];
  }
}
