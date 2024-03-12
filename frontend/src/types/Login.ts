export interface ILogin {
  username: string;
  password: string;
}

export class Login implements ILogin {
  public username: string;
  public password: string;

  constructor(obj?: ILogin) {
    this.username = (obj && obj.username) || "";
    this.password = (obj && obj.password) || "";
  }
}
