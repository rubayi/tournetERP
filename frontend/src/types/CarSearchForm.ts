export interface ICarSearchForm {
  searchTrnCarUuid: number | 0;
  searchCarBrand: number | 0;
  searchPeople: number | 0;
  searchCarManager: number | 0;
  searchCarVin: string | '';
  searchCarType: number | 0;
}

export class CarSearchForm implements ICarSearchForm {
  public searchTrnCarUuid: number | 0;
  public searchCarBrand: number | 0;
  public searchPeople: number | 0;
  public searchCarManager: number | 0;
  public searchCarVin: string | '';
  public searchCarType: number | 0;

  constructor(obj?: ICarSearchForm) {
    this.searchTrnCarUuid = (obj && obj.searchTrnCarUuid) || 0;
    this.searchCarBrand = (obj && obj.searchCarBrand) || 0;
    this.searchPeople = (obj && obj.searchPeople) || 0;
    this.searchCarManager = (obj && obj.searchCarManager) || 0;
    this.searchCarVin = (obj && obj.searchCarVin) || '';
    this.searchCarType = (obj && obj.searchCarType) || 0;
  }
}
