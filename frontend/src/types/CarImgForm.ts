import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ICarImgForm {
  carImgUuid: number | 0;
  trnCarUuid: number | 0;
  previewImage: string | '';
  imgFile: string | '';
}

export class CarImgForm implements ICarImgForm {
  public carImgUuid: number | 0;
  public trnCarUuid: number | 0;
  public previewImage: string | '';
  public imgFile: string | '';

  constructor(obj?: ICarImgForm) {
    this.carImgUuid = getNumberMemberValue(obj && obj.carImgUuid);
    this.trnCarUuid = getNumberMemberValue(obj && obj.trnCarUuid);
    this.previewImage = (obj && obj.previewImage) || '';
    this.imgFile = (obj && obj.imgFile) || '';
  }
}
