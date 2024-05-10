export interface ICompSearchForm {
  searchCompSector: number | 0;
  searchBeginDt: string | null;
  searchEndDt: string | null;
  searchCompKor: string | null;
  searchCompEng: string | null;
  searchCompMemo: string | null;
  searchCompUuid: string | null;
  searchCompRate: string | null;
  searchCouponYn: number | 0;
  searchCompGroup: string | null;
}

export class CompSearchForm implements ICompSearchForm {
  public searchCompSector: number | 0;
  public searchBeginDt: string | null;
  public searchEndDt: string | null;
  public searchCompKor: string | null;
  public searchCompEng: string | null;
  public searchCompMemo: string | null;
  public searchCompUuid: string | null;
  public searchCompRate: string | null;
  public searchCouponYn: string | null;
  public searchCompGroup: string | null;

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
