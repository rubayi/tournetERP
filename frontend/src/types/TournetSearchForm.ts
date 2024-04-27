export interface ITournetSearchForm {
  searchyeartype: number | 0;
  searchsdate: string | null;
  searchedate: string | null;
  searchsubject: string | null;
  searchremarks: string | null;
  searchcomment: string | null;
  searchworkno: string | null;
  searchletterto: string | null;
  searchletterfrom: string | null;
  searchreceiveddate: string | null;
  searchdatecrewassigned: string | null;
  searchdatecompleted: string | null;
  searchposteddate: string | null;
  searchdatesentbacktotraffic: string | null;
  searchworkstatus: number[] | [];
  searchletterrouteno: string | null;
  searchworkdep: string[] | [];
  searchcrewassigned: number[] | [];
  searchreceivedby: string[] | [];
  searchpostedby: string[] | [];
  searchcount: number | 0;
}

export class TournetSearchForm implements ITournetSearchForm {
  public searchyeartype: number | 0;
  public searchsdate: string | null;
  public searchedate: string | null;
  public searchsubject: string | null;
  public searchremarks: string | null;
  public searchcomment: string | null;
  public searchworkno: string | null;
  public searchletterto: string | null;
  public searchletterfrom: string | null;
  public searchreceiveddate: string | null;
  public searchdatecrewassigned: string | null;
  public searchdatecompleted: string | null;
  public searchposteddate: string | null;
  public searchdatesentbacktotraffic: string | null;
  public searchworkstatus: number[] | [];
  public searchletterrouteno: string | null;
  public searchworkdep: string[] | [];
  public searchcrewassigned: number[] | [];
  public searchreceivedby: string[] | [];
  public searchpostedby: string[] | [];
  public searchcount: number | 0;

  constructor(obj?: ITournetSearchForm) {
    this.searchyeartype = (obj && obj.searchyeartype) || 0;
    this.searchsdate = (obj && obj.searchsdate) || "";
    this.searchedate = (obj && obj.searchedate) || "";
    this.searchsubject = (obj && obj.searchsubject) || "";
    this.searchremarks = (obj && obj.searchremarks) || "";
    this.searchcomment = (obj && obj.searchcomment) || "";
    this.searchworkno = (obj && obj.searchworkno) || "";
    this.searchletterto = (obj && obj.searchletterto) || "";
    this.searchletterfrom = (obj && obj.searchletterfrom) || "";
    this.searchreceiveddate = (obj && obj.searchreceiveddate) || "";
    this.searchdatecrewassigned = (obj && obj.searchdatecrewassigned) || "";
    this.searchdatecompleted = (obj && obj.searchdatecompleted) || "";
    this.searchposteddate = (obj && obj.searchposteddate) || "";
    this.searchdatesentbacktotraffic =
      (obj && obj.searchdatesentbacktotraffic) || "";
    this.searchworkstatus = (obj && obj.searchworkstatus) || [];
    this.searchletterrouteno = (obj && obj.searchletterrouteno) || "";
    this.searchworkdep = (obj && obj.searchworkdep) || [];
    this.searchcrewassigned = (obj && obj.searchcrewassigned) || [];
    this.searchreceivedby = (obj && obj.searchreceivedby) || [];
    this.searchpostedby = (obj && obj.searchpostedby) || [];
    this.searchcount = (obj && obj.searchcount) || 0;
  }
}
