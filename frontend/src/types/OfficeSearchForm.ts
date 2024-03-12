export interface IOfficeSearchForm {
  searchIsland: number | 0;
  searchType: number | 0;
  searchName: string | null;
}

export class OfficeSearchForm implements IOfficeSearchForm {
  public searchIsland: number | 0;
  public searchType: number | 0;
  public searchName: string | null;

  constructor(obj?: IOfficeSearchForm) {
    this.searchIsland = (obj && obj.searchIsland) || 0;
    this.searchType = (obj && obj.searchType) || 0;
    this.searchName = (obj && obj.searchName) || "";
  }
}
