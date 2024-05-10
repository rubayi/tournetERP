export interface ICompSearchForm {
  searchCompSector: number | 0;
  searchBeginDt: string | '';
  searchEndDt: string | '';
  searchCompKor: string | '';
  searchCompEng: string | '';
  searchCompMemo: string | '';
  searchCompUuid: string | '';
  searchCompRate: string | '';
  searchCouponYn: string | '';
  searchCompGroup: string | '';
}

export class CompSearchForm implements ICompSearchForm {
  public searchCompSector: number | 0;
  public searchBeginDt: string | '';
  public searchEndDt: string | '';
  public searchCompKor: string | '';
  public searchCompEng: string | '';
  public searchCompMemo: string | '';
  public searchCompUuid: string | '';
  public searchCompRate: string | '';
  public searchCouponYn: string | '';
  public searchCompGroup: string | '';

  constructor(obj?: ICompSearchForm) {
    this.searchCompSector = (obj && obj.searchCompSector) || 0;
    this.searchBeginDt = (obj && obj.searchBeginDt) || '';
    this.searchEndDt = (obj && obj.searchEndDt) || '';
    this.searchCompKor = (obj && obj.searchCompKor) || '';
    this.searchCompEng = (obj && obj.searchCompEng) || '';
    this.searchCompMemo = (obj && obj.searchCompMemo) || '';
    this.searchCompUuid = (obj && obj.searchCompUuid) || '';
    this.searchCompRate = (obj && obj.searchCompRate) || '';
    this.searchCouponYn = (obj && obj.searchCouponYn) || '';
    this.searchCompGroup = (obj && obj.searchCompGroup) || '';
  }
}
