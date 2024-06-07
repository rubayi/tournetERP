import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IAmenityForm {
  amenityUuid: number | 0;
  tourUuid: number | 0;
  amenityKor: string | '';
  amenityEng: string | '';
  amenityDescKor: string | '';
  amenityDescEng: string | '';
}

export class AmenityForm implements IAmenityForm {
  public amenityUuid: number | 0;
  public tourUuid: number | 0;
  public amenityKor: string | '';
  public amenityEng: string | '';
  public amenityDescKor: string | '';
  public amenityDescEng: string | '';

  constructor(obj?: IAmenityForm) {
    this.amenityUuid = getNumberMemberValue(obj && obj.amenityUuid);
    this.tourUuid = (obj && obj.tourUuid) || 0;
    this.amenityKor = (obj && obj.amenityKor) || '';
    this.amenityEng = (obj && obj.amenityEng) || '';
    this.amenityDescKor = (obj && obj.amenityDescKor) || '';
    this.amenityDescEng = (obj && obj.amenityDescEng) || '';
  }
}
