export interface IMenuSearchForm {
  role: string | '';
}

export class MenuSearchForm implements IMenuSearchForm {
  public role: string | '';

  constructor(obj?: IMenuSearchForm) {
    this.role = (obj && obj.role) || '';
  }
}
