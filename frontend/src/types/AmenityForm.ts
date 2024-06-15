import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IAmenityForm {
  amenityUuid: number | 0;
  tourUuid: number | 0;
  amenityType: number | 0;
  useYn: number | 0;
  amenityKor: string | '';
  amenityEng: string | '';
  amenityDescKor: string | '';
  amenityDescEng: string | '';
}

export class AmenityForm implements IAmenityForm {
  public amenityUuid: number | 0;
  public tourUuid: number | 0;
  public amenityType: number | 0;
  public useYn: number | 0;
  public amenityKor: string | '';
  public amenityEng: string | '';
  public amenityDescKor: string | '';
  public amenityDescEng: string | '';

  constructor(obj?: IAmenityForm) {
    this.amenityUuid = getNumberMemberValue(obj && obj.amenityUuid);
    this.tourUuid = (obj && obj.tourUuid) || 0;
    this.amenityType = (obj && obj.amenityType) || 0;
    this.useYn = (obj && obj.useYn) || 0;
    this.amenityKor = (obj && obj.amenityKor) || '';
    this.amenityEng = (obj && obj.amenityEng) || '';
    this.amenityDescKor = (obj && obj.amenityDescKor) || '';
    this.amenityDescEng = (obj && obj.amenityDescEng) || '';
  }
}
