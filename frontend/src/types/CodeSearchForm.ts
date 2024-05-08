export interface ICodeSearchForm {
  searchUprCodeUuid: number | 0;
  searchCodeKr: string | '';
  searchCodeEn: string | '';
  searchCodeLvl: number | 0;
}

export class CodeSearchForm implements ICodeSearchForm {
  public searchUprCodeUuid: number | 0;
  public searchCodeKr: string | '';
  public searchCodeEn: string | '';
  public searchCodeLvl: number | 0;

  constructor(obj?: ICodeSearchForm) {
    this.searchUprCodeUuid = (obj && obj.searchUprCodeUuid) || 0;
    this.searchCodeKr = (obj && obj.searchCodeKr) || '';
    this.searchCodeEn = (obj && obj.searchCodeEn) || '';
    this.searchCodeLvl = (obj && obj.searchCodeLvl) || 0;
  }
}
