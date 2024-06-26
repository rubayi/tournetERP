export interface ICarImgSearchForm {
  trnCarUuid: number | 0;
}
export class CarImgSearchForm implements ICarImgSearchForm {
  public trnCarUuid: number | 0;

  constructor(obj?: ICarImgSearchForm) {
    this.trnCarUuid = (obj && obj.trnCarUuid) || 0;
  }
}
