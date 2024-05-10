export interface ICdcdSearchForm {
  searchCdCdUuid: number | 0;
  searchMngNameKor: string | '';
  searchMngNameEng: string | '';
  searchCardNumber: string | '';
}

export class CdcdSearchForm implements ICdcdSearchForm {
  public searchCdCdUuid: number | 0;
  public searchMngNameKor: string | '';
  public searchMngNameEng: string | '';
  public searchCardNumber: string | '';

  constructor(obj?: ICdcdSearchForm) {
    this.searchCdCdUuid = (obj && obj.searchCdCdUuid) || 0;
    this.searchMngNameKor = (obj && obj.searchMngNameKor) || '';
    this.searchMngNameEng = (obj && obj.searchMngNameEng) || '';
    this.searchCardNumber = (obj && obj.searchCardNumber) || '';
  }
}
