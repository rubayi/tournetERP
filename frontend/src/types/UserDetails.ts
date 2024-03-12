export class OrgPermission {
  public id: number;
  public appUserUuid: string;
  public accessToken: string;
  public permission: string;

  constructor(obj?: OrgPermission) {
    this.id = (obj && obj.id) || 0;
    this.appUserUuid = (obj && obj.appUserUuid) || "";
    this.accessToken = (obj && obj.accessToken) || "";
    this.permission = (obj && obj.permission) || "";
  }
}

export interface IUserDetails {
  accessToken: string;
  username: string;
  firstName: string;
  lastName: string;
  enabled: boolean | null;
  accountNonExpired: boolean | null;
  accountNonLocked: boolean | null;
  credentialsNonExpired: boolean | null;
  authorities: any[];

  ipAddress: string;
  sessionId: string;
  uuid: string;
  orgLevelPermissions: OrgPermission[];
}

export class UserDetails implements IUserDetails {
  public accessToken: string;
  public username: string;
  public firstName: string;
  public lastName: string;
  public enabled: boolean;
  public accountNonExpired: boolean;
  public accountNonLocked: boolean;
  public credentialsNonExpired: boolean;
  public authorities: any[];

  public ipAddress: string;
  public sessionId: string;
  public uuid: string;
  public orgLevelPermissions: OrgPermission[];

  constructor(obj?: IUserDetails) {
    this.accessToken = (obj && obj.accessToken) || "";
    this.username = (obj && obj.username) || "";
    this.firstName = (obj && obj.firstName) || "";
    this.lastName = (obj && obj.lastName) || "";
    this.enabled = (obj && obj.enabled) || false;
    this.accountNonExpired = (obj && obj.accountNonExpired) || false;
    this.accountNonLocked = (obj && obj.accountNonLocked) || false;
    this.credentialsNonExpired = (obj && obj.credentialsNonExpired) || false;
    this.authorities = (obj && obj.authorities) || [];

    this.ipAddress = (obj && obj.ipAddress) || "";
    this.sessionId = (obj && obj.sessionId) || "";
    this.uuid = (obj && obj.uuid) || "";
    this.orgLevelPermissions = (obj && obj.orgLevelPermissions) || [];
  }
}
