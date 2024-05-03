export interface ICdcdSearchForm {
  searchCdCdUuid: number | 0;
  searchMngNameKor: string | null;
  searchMngNameEng: string | null;
  searchCardNumber: string | null;
}

export class CdcdSearchForm implements ICdcdSearchForm {
  public searchCdCdUuid: number | 0;
  public searchMngNameKor: string | null;
  public searchMngNameEng: string | null;
  public searchCardNumber: string | null;

  constructor(obj?: ICdcdSearchForm) {
    this.searchCdCdUuid = (obj && obj.searchCdCdUuid) || 0;
    this.searchMngNameKor = (obj && obj.searchMngNameKor) || "";
    this.searchMngNameEng = (obj && obj.searchMngNameEng) || "";
    this.searchCardNumber = (obj && obj.searchCardNumber) || "";
  }
}
