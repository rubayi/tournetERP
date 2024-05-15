export interface IContactSearchForm {
  compUuid: number | 0;
}
export class ContactSearchForm implements IContactSearchForm {
  public compUuid: number | 0;

  constructor(obj?: IContactSearchForm) {
    this.compUuid = (obj && obj.compUuid) || 0;
  }
}
