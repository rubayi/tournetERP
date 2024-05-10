export interface ITournetSearchForm {
  searchyeartype: number | 0;
  searchsdate: string | '';
  searchedate: string | '';
  searchsubject: string | '';
  searchremarks: string | '';
  searchcomment: string | '';
  searchworkno: string | '';
  searchletterto: string | '';
  searchletterfrom: string | '';
  searchreceiveddate: string | '';
  searchdatecrewassigned: string | '';
  searchdatecompleted: string | '';
  searchposteddate: string | '';
  searchdatesentbacktotraffic: string | '';
  searchworkstatus: number[] | [];
  searchletterrouteno: string | '';
  searchworkdep: string[] | [];
  searchcrewassigned: number[] | [];
  searchreceivedby: string[] | [];
  searchpostedby: string[] | [];
  searchcount: number | 0;
}

export class TournetSearchForm implements ITournetSearchForm {
  public searchyeartype: number | 0;
  public searchsdate: string | '';
  public searchedate: string | '';
  public searchsubject: string | '';
  public searchremarks: string | '';
  public searchcomment: string | '';
  public searchworkno: string | '';
  public searchletterto: string | '';
  public searchletterfrom: string | '';
  public searchreceiveddate: string | '';
  public searchdatecrewassigned: string | '';
  public searchdatecompleted: string | '';
  public searchposteddate: string | '';
  public searchdatesentbacktotraffic: string | '';
  public searchworkstatus: number[] | [];
  public searchletterrouteno: string | '';
  public searchworkdep: string[] | [];
  public searchcrewassigned: number[] | [];
  public searchreceivedby: string[] | [];
  public searchpostedby: string[] | [];
  public searchcount: number | 0;

  constructor(obj?: ITournetSearchForm) {
    this.searchyeartype = (obj && obj.searchyeartype) || 0;
    this.searchsdate = (obj && obj.searchsdate) || '';
    this.searchedate = (obj && obj.searchedate) || '';
    this.searchsubject = (obj && obj.searchsubject) || '';
    this.searchremarks = (obj && obj.searchremarks) || '';
    this.searchcomment = (obj && obj.searchcomment) || '';
    this.searchworkno = (obj && obj.searchworkno) || '';
    this.searchletterto = (obj && obj.searchletterto) || '';
    this.searchletterfrom = (obj && obj.searchletterfrom) || '';
    this.searchreceiveddate = (obj && obj.searchreceiveddate) || '';
    this.searchdatecrewassigned = (obj && obj.searchdatecrewassigned) || '';
    this.searchdatecompleted = (obj && obj.searchdatecompleted) || '';
    this.searchposteddate = (obj && obj.searchposteddate) || '';
    this.searchdatesentbacktotraffic =
      (obj && obj.searchdatesentbacktotraffic) || '';
    this.searchworkstatus = (obj && obj.searchworkstatus) || [];
    this.searchletterrouteno = (obj && obj.searchletterrouteno) || '';
    this.searchworkdep = (obj && obj.searchworkdep) || [];
    this.searchcrewassigned = (obj && obj.searchcrewassigned) || [];
    this.searchreceivedby = (obj && obj.searchreceivedby) || [];
    this.searchpostedby = (obj && obj.searchpostedby) || [];
    this.searchcount = (obj && obj.searchcount) || 0;
  }
}
