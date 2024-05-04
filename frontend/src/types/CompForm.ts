
import { getNumberMemberValue } from "src/utils/helpers/ConstructorHelper";

export interface ICompForm {
  compUuid: number | 0;
  compSector: number | 0;
  compKor: string | null;
  compEng: string | null;
  compGroup: string | null;
  compAbb: string | null;
  compColor: string | null;
  logoFile: string | null;
  compRate: number | 0;
  hotelRate: number | 0;
  optionRate: number | 0;
  rentcarRate: number | 0;
  restaurantRate: number | 0;
  packRegRate: number | 0;
  packRate: number | 0;
  honeymoonRegRate: number | 0;
  honeymoonRate: number | 0;
  estDate: string | null;
  beginDt: string | null;
  endDt: string | null;
  minAge: string | null;
  childAge: string | null;
  youthAge: string | null;
  couponYn: number | 0;
  prepaidHow: number | 0;
  compMemo: string | null;

  modifiedDt: string | null;

}

export class CompForm implements ICompForm {
  public compUuid: number | 0;
  public compSector: number | 0;
  public compKor: string | null;
  public compEng: string | null;
  public compGroup: string | null;
  public compAbb: string | null;
  public compColor: string | null;
  public logoFile: string | null;
  public compRate: number | 0;
  public hotelRate: number | 0;
  public optionRate: number | 0;
  public rentcarRate: number | 0;
  public restaurantRate: number | 0;
  public packRegRate: number | 0;
  public packRate: number | 0;
  public honeymoonRegRate: number | 0;
  public honeymoonRate: number | 0;
  public estDate: string | null;
  public beginDt: string | null;
  public endDt: string | null;
  public minAge: string | null;
  public childAge: string | null;
  public youthAge: string | null;
  public couponYn: number | 0;
  public prepaidHow: number | 0;
  public compMemo: string | null;

  public modifiedDt: string | null;

  constructor(obj?: ICompForm) {
    this.compUuid = getNumberMemberValue(obj && obj.compUuid);
    this.compSector = (obj && obj.compSector) || 0;
    this.compKor = (obj && obj?.compKor) || "";
    this.compEng = (obj && obj?.compEng) || "";
    this.compGroup = (obj && obj.compGroup) || "";
    this.compAbb = (obj && obj.compAbb) || "";
    this.compColor = (obj && obj.compColor) || "";
    this.logoFile = (obj && obj.logoFile) || "";
    this.estDate = (obj && obj.estDate) || "";
    this.compRate = (obj?.optionRate) || 0;
    this.hotelRate = (obj && obj.hotelRate) || 0;
    this.optionRate = (obj?.optionRate) || 0;
    this.rentcarRate = (obj?.rentcarRate) || 0;
    this.restaurantRate = (obj?.restaurantRate) || 0;
    this.packRegRate = (obj && obj.packRegRate) || 0;
    this.packRate = (obj && obj.packRate) || 0;
    this.honeymoonRegRate = (obj && obj.packRegRate) || 0;
    this.honeymoonRate = (obj && obj.packRate) || 0;
    this.beginDt = (obj && obj.beginDt) || "";
    this.endDt = (obj && obj.endDt) || "";
    this.minAge = (obj && obj.minAge) || "";
    this.childAge = (obj && obj.childAge) || "";
    this.youthAge = (obj && obj.youthAge) || "";
    this.couponYn = (obj && obj.couponYn) || 0;
    this.prepaidHow = (obj?.prepaidHow) || 0;
    this.compMemo = (obj && obj.compMemo) || "";

    this.modifiedDt = (obj && obj.modifiedDt) || "";

  }
}
