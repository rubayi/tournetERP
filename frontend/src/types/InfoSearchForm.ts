export interface IInfoSearchForm {
  searchTourUuid: number | 0;
}
export class InfoSearchForm implements IInfoSearchForm {
  public searchTourUuid: number | 0;

  constructor(obj?: IInfoSearchForm) {
    this.searchTourUuid = (obj && obj.searchTourUuid) || 0;
  }
}
