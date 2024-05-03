export interface ICdcdSearchForm {
  searchCdCdUuid: number | 0;
  searchMngNameKor: string | null;
  searchCardNumber: number | 0;
}

export class CdcdSearchForm implements ICdcdSearchForm {
  public searchCdCdUuid: number | 0;
  public searchMngNameKor: string | null;
  public searchCardNumber: number | 0;

  constructor(obj?: ICdcdSearchForm) {
    this.searchCdCdUuid = (obj && obj.searchCdCdUuid) || 0;
    this.searchMngNameKor = (obj && obj.searchMngNameKor) || "";
    this.searchCardNumber = (obj && obj.searchCardNumber) || 0;
  }
}
