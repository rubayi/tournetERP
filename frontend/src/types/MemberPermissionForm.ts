export interface IMemberPermissionForm {
  logresponseauthR: boolean | false;
  logresponseauthA: boolean | false;
  logresponseauthE: boolean | false;
  logresponseauthD: boolean | false;

  eleclogauthR: boolean | false;
  eleclogauthA: boolean | false;
  eleclogauthE: boolean | false;
  eleclogauthD: boolean | false;

  tunnellogauthR: boolean | false;
  tunnellogauthA: boolean | false;
  tunnellogauthE: boolean | false;
  tunnellogauthD: boolean | false;

  officeauthR: boolean | false;
  officeauthE: boolean | false;
  officeauthA: boolean | false;
  officeauthD: boolean | false;
  userauthR: boolean | false;
  userauthE: boolean | false;
  userauthA: boolean | false;
  userauthD: boolean | false;
  reportauthR: boolean | false;
  reportauthE: boolean | false;
  reportauthA: boolean | false;
  reportauthD: boolean | false;
  codeauthR: boolean | false;
  codeauthE: boolean | false;
  codeauthA: boolean | false;
  codeauthD: boolean | false;
}

export class MemberPermissionForm implements IMemberPermissionForm {
  public logresponseauthR: boolean | false;
  public logresponseauthA: boolean | false;
  public logresponseauthE: boolean | false;
  public logresponseauthD: boolean | false;

  public eleclogauthR: boolean | false;
  public eleclogauthA: boolean | false;
  public eleclogauthE: boolean | false;
  public eleclogauthD: boolean | false;

  public tunnellogauthR: boolean | false;
  public tunnellogauthA: boolean | false;
  public tunnellogauthE: boolean | false;
  public tunnellogauthD: boolean | false;

  public officeauthR: boolean | false;
  public officeauthE: boolean | false;
  public officeauthA: boolean | false;
  public officeauthD: boolean | false;
  public userauthR: boolean | false;
  public userauthE: boolean | false;
  public userauthA: boolean | false;
  public userauthD: boolean | false;
  public reportauthR: boolean | false;
  public reportauthE: boolean | false;
  public reportauthA: boolean | false;
  public reportauthD: boolean | false;
  public codeauthR: boolean | false;
  public codeauthE: boolean | false;
  public codeauthA: boolean | false;
  public codeauthD: boolean | false;

  constructor(obj?: IMemberPermissionForm) {
    this.logresponseauthR = (obj && obj.logresponseauthR) || false;
    this.logresponseauthA = (obj && obj.logresponseauthA) || false;
    this.logresponseauthE = (obj && obj.logresponseauthE) || false;
    this.logresponseauthD = (obj && obj.logresponseauthD) || false;

    this.eleclogauthR = (obj && obj.eleclogauthR) || false;
    this.eleclogauthA = (obj && obj.eleclogauthA) || false;
    this.eleclogauthE = (obj && obj.eleclogauthE) || false;
    this.eleclogauthD = (obj && obj.eleclogauthD) || false;

    this.tunnellogauthR = (obj && obj.tunnellogauthR) || false;
    this.tunnellogauthA = (obj && obj.tunnellogauthA) || false;
    this.tunnellogauthE = (obj && obj.tunnellogauthE) || false;
    this.tunnellogauthD = (obj && obj.tunnellogauthD) || false;

    this.officeauthR = (obj && obj.officeauthR) || false;
    this.officeauthA = (obj && obj.officeauthA) || false;
    this.officeauthE = (obj && obj.officeauthE) || false;
    this.officeauthD = (obj && obj.officeauthD) || false;

    this.userauthR = (obj && obj.userauthR) || false;
    this.userauthA = (obj && obj.userauthA) || false;
    this.userauthE = (obj && obj.userauthE) || false;
    this.userauthD = (obj && obj.userauthD) || false;

    this.reportauthR = (obj && obj.reportauthR) || false;
    this.reportauthA = (obj && obj.reportauthA) || false;
    this.reportauthE = (obj && obj.reportauthE) || false;
    this.reportauthD = (obj && obj.reportauthD) || false;

    this.codeauthR = (obj && obj.codeauthR) || false;
    this.codeauthA = (obj && obj.codeauthA) || false;
    this.codeauthE = (obj && obj.codeauthE) || false;
    this.codeauthD = (obj && obj.codeauthD) || false;
  }
}
