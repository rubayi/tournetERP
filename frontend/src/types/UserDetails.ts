export class OrgPermission {
  public id: number;
  public username: string;
  public accessToken: string;
  public permission: string;

  constructor(obj?: OrgPermission) {
    this.id = (obj && obj.id) || 0;
    this.username = (obj && obj.username) || '';
    this.accessToken = (obj && obj.accessToken) || '';
    this.permission = (obj && obj.permission) || '';
  }
}

export interface IUserDetails {
  accessToken: string;
  username: string;
  empKor: string;
  empEng: string;
  enabled: boolean | '';
  accountNonExpired: boolean | '';
  accountNonLocked: boolean | '';
  credentialsNonExpired: boolean | '';
  authorities: any[];

  ipAddress: string;
  sessionId: string;
  id: string;
  orgLevelPermissions: OrgPermission[];
}

export class UserDetails implements IUserDetails {
  public accessToken: string;
  public username: string;
  public empKor: string;
  public empEng: string;
  public enabled: boolean;
  public accountNonExpired: boolean;
  public accountNonLocked: boolean;
  public credentialsNonExpired: boolean;
  public authorities: any[];

  public ipAddress: string;
  public sessionId: string;
  public id: string;
  public orgLevelPermissions: OrgPermission[];

  constructor(obj?: IUserDetails) {
    this.accessToken = (obj && obj.accessToken) || '';
    this.username = (obj && obj.username) || '';
    this.empKor = (obj && obj.empKor) || '';
    this.empEng = (obj && obj.empEng) || '';
    this.enabled = (obj && obj.enabled) || false;
    this.accountNonExpired = (obj && obj.accountNonExpired) || false;
    this.accountNonLocked = (obj && obj.accountNonLocked) || false;
    this.credentialsNonExpired = (obj && obj.credentialsNonExpired) || false;
    this.authorities = (obj && obj.authorities) || [];

    this.ipAddress = (obj && obj.ipAddress) || '';
    this.sessionId = (obj && obj.sessionId) || '';
    this.id = (obj && obj.id) || '';
    this.orgLevelPermissions = (obj && obj.orgLevelPermissions) || [];
  }
}
