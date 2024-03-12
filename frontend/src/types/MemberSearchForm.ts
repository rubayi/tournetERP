export interface IMemberSearchForm {
  searchComid: number | 0;
  searchName: string | null;
  searchfirstname: string | null;
  searchlastname: string | null;
  searchposition: string | null;
  searchemail: string | null;
  searchphone: string | null;
}

export class MemberSearchForm implements IMemberSearchForm {
  public searchComid: number | 0;
  public searchName: string | null;
  public searchfirstname: string | null;
  public searchlastname: string | null;
  public searchposition: string | null;
  public searchemail: string | null;
  public searchphone: string | null;

  constructor(obj?: IMemberSearchForm) {
    this.searchComid = (obj && obj.searchComid) || 0;
    this.searchName = (obj && obj.searchName) || "";
    this.searchfirstname = (obj && obj.searchfirstname) || "";
    this.searchlastname = (obj && obj.searchlastname) || "";
    this.searchposition = (obj && obj.searchposition) || "";
    this.searchemail = (obj && obj.searchemail) || "";
    this.searchphone = (obj && obj.searchphone) || "";
  }
}
