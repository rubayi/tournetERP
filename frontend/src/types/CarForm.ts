import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ICarForm {
  trnCarUuid: number | 0;
  carType: number | 0;
  carBrand: number | 0;
  carVin: string | '';

  licensePlate: string | '';
  people: number | 0;
  carYear: string | '';
  carManager: number | 0;

  carEndDt: string | '';

  memo: string | '';

}

export class CarForm implements ICarForm {
  public trnCarUuid: number | 0;
  public carType: number | 0;
  public carBrand: number | 0;
  public carVin: string | '';

  public licensePlate: string | '';
  public people: number | 0;
  public carYear: string | '';
  public carManager: number | 0;

  public carEndDt: string | '';

  public memo: string | '';

  constructor(obj?: ICarForm) {
    this.trnCarUuid = getNumberMemberValue(obj && obj.trnCarUuid);
    this.carType = (obj && obj.carType) || 0;
    this.carBrand = obj?.carBrand || 0;
    this.carVin = (obj && obj.carVin) || '';

    this.licensePlate = (obj && obj?.licensePlate) || '';
    this.people = obj?.people || 0;
    this.carYear = (obj && obj?.carYear) || '';
    this.carManager = (obj && obj.carManager) || 0;

    this.carEndDt = (obj && obj.carEndDt) || '';

    this.memo = (obj && obj.memo) || '';
  }
}
