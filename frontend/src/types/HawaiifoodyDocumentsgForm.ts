export interface IHawaiifoodyDocumentsgForm {
  idx: number | 0;
  workcopies: number | 0;
  workdescription: string | null;
  userNameID: string | null;
}

export class HawaiifoodyDocumentsgForm implements IHawaiifoodyDocumentsgForm {
  public idx: number | 0;
  public workcopies: number | 0;
  public workdescription: string | null;
  public userNameID: string | null;

  constructor(obj?: IHawaiifoodyDocumentsgForm) {
    this.idx = (obj && obj.idx) || 0;
    this.workcopies = (obj && obj.workcopies) || 2;
    this.workdescription =
      (obj && obj.workdescription) || "Plans with work highlighted in yellow";
    this.userNameID = (obj && obj.userNameID) || "";
  }
}
