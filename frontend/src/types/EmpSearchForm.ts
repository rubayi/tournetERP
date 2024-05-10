export interface IEmpSearchForm {
  searchEmpUuid: number | 0;
  searchEmpKor: string | '';
  searchEmpEng: string | '';
  searchEmpDiv: number | 0;
  searchEmpStatus: number | 0;
  searchUsername: string | '';
}

export class EmpSearchForm implements IEmpSearchForm {
  public searchEmpUuid: number | 0;
  public searchEmpKor: string | '';
  public searchEmpEng: string | '';
  public searchEmpDiv: number | 0;
  public searchEmpStatus: number | 0;
  public searchUsername: string | '';

  constructor(obj?: IEmpSearchForm) {
    this.searchEmpUuid = (obj && obj.searchEmpUuid) || 0;
    this.searchEmpKor = (obj && obj.searchEmpKor) || '';
    this.searchEmpEng = (obj && obj.searchEmpEng) || '';
    this.searchEmpDiv = (obj && obj.searchEmpDiv) || 0;
    this.searchEmpStatus = (obj && obj.searchEmpStatus) || 0;
    this.searchUsername = (obj && obj.searchUsername) || '';
  }
}
