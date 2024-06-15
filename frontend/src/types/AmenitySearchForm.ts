export interface IAmenitySearchForm {
  searchTourUuid: number | 0;
  searchAmenityUuid: number | 0;
}
export class AmenitySearchForm implements IAmenitySearchForm {
  public searchTourUuid: number | 0;
  public searchAmenityUuid: number | 0;

  constructor(obj?: IAmenitySearchForm) {
    this.searchTourUuid = (obj && obj.searchTourUuid) || 0;
    this.searchAmenityUuid = (obj && obj.searchAmenityUuid) || 0;
  }
}
