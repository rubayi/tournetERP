import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface ITourForm {
  tourUuid: number | 0;
  tourCategory: number | 0;
  tourArea: number | 0;
  tourAreaSub: number | 0;
  prepaidMethod: number | 0;
  tourKor: string | '';
  tourEng: string | '';
  tourOrd: number | 0;

  minAge: number | 0;
  childAge: number | 0;
  youthAge: number | 0;

  beginDt: string | '';
  endDt: string | '';
}

export class TourForm implements ITourForm {
  public tourUuid: number | 0;
  public tourCategory: number | 0;
  public tourArea: number | 0;
  public tourAreaSub: number | 0;
  public prepaidMethod: number | 0;
  public tourKor: string | '';
  public tourEng: string | '';
  public tourOrd: number | 0;

  public minAge: number | 0;
  public childAge: number | 0;
  public youthAge: number | 0;

  public beginDt: string | '';
  public endDt: string | '';

  constructor(obj?: ITourForm) {
    this.tourUuid = getNumberMemberValue(obj && obj.tourUuid);
    this.tourCategory = (obj && obj.tourCategory) || 0;
    this.tourArea = (obj && obj.tourArea) || 0;
    this.tourAreaSub = (obj && obj.tourAreaSub) || 0;
    this.prepaidMethod = (obj && obj.prepaidMethod) || 0;
    this.tourKor = (obj && obj.tourKor) || '';
    this.tourEng = (obj && obj.tourEng) || '';
    this.tourOrd = (obj && obj.tourOrd) || 0;

    this.minAge = (obj && obj.minAge) || 0;
    this.childAge = (obj && obj.childAge) || 0;
    this.youthAge = (obj && obj.youthAge) || 0;

    this.beginDt = (obj && obj.beginDt) || '';
    this.endDt = (obj && obj.endDt) || '';

  }
}
