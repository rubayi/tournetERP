export interface ITourServiceSearchForm {
  searchTourUuid: number | 0;
  searchServiceUuid: number | 0;
  searchServiceNameKor: string | '';
  searchServiceNameEng: string | '';
  searchPickupYn: number | 0;
  searchMealYn: number | 0;
}
export class TourServiceSearchForm implements ITourServiceSearchForm {
  public searchTourUuid: number | 0;
  public searchServiceUuid: number | 0;
  public searchServiceNameKor: string | '';
  public searchServiceNameEng: string | '';
  public searchPickupYn: number | 0;
  public searchMealYn: number | 0;

  constructor(obj?: ITourServiceSearchForm) {
    this.searchTourUuid = (obj && obj.searchTourUuid) || 0;
    this.searchServiceUuid = (obj && obj.searchServiceUuid) || 0;
    this.searchServiceNameKor = (obj && obj.searchServiceNameKor) || '';
    this.searchServiceNameEng = (obj && obj.searchServiceNameEng) || '';
    this.searchPickupYn = (obj && obj.searchPickupYn) || 0;
    this.searchMealYn = (obj && obj.searchMealYn) || 0;
  }
}
