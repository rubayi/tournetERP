export interface IMenuSearchForm {
  role: string | null;
}

export class MenuSearchForm implements IMenuSearchForm {
  public role: string | null;

  constructor(obj?: IMenuSearchForm) {
    this.role = (obj && obj.role) || "";
  }
}
