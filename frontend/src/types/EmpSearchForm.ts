export interface IEmpSearchForm {
  searchEmpUuid: number | 0;
  searchEmpKor: string | null;
  searchEmpEng: string | null;
  searchEmpDiv: number | 0;
}

export class EmpSearchForm implements IEmpSearchForm {
  public searchEmpUuid: number | 0;
  public searchEmpKor: string | null;
  public searchEmpEng: string | null;
  public searchEmpDiv: number | 0;

  constructor(obj?: IEmpSearchForm) {
    this.searchEmpUuid = (obj && obj.searchEmpUuid) || 0;
    this.searchEmpKor = (obj && obj.searchEmpKor) || '';
    this.searchEmpEng = (obj && obj.searchEmpEng) || '';
    this.searchEmpDiv = (obj && obj.searchEmpDiv) || 0;
  }
}
