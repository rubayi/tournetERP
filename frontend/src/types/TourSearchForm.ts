export interface ITourSearchForm {
  searchTourUuid: number | 0;
  searchTourCategory: number | 0;
  searchTourKor: string | '';
  searchTourEng: string | '';
  searchTourArea: number | 0;
  searchTourAreaSub: number | 0;
  searchPrepaidMethod: number | 0;
}

export class TourSearchForm implements ITourSearchForm {
  public searchTourUuid: number | 0;
  public searchTourCategory: number | 0;
  public searchTourKor: string | '';
  public searchTourEng: string | '';
  public searchTourArea: number | 0;
  public searchTourAreaSub: number | 0;
  public searchPrepaidMethod: number | 0;

  constructor(obj?: ITourSearchForm) {
    this.searchTourUuid = (obj && obj.searchTourUuid) || 0;
    this.searchTourCategory = (obj && obj.searchTourCategory) || 0;
    this.searchTourKor = (obj && obj.searchTourKor) || '';
    this.searchTourEng = (obj && obj.searchTourEng) || '';
    this.searchTourArea = (obj && obj.searchTourArea) || 0;
    this.searchTourAreaSub = (obj && obj.searchTourAreaSub) || 0;
    this.searchPrepaidMethod = (obj && obj.searchPrepaidMethod) || 0;
  }
}
