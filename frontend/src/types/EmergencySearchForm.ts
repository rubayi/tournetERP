export interface IEmergencySearchForm {
  empUuid: number | 0;
}
export class EmergencySearchForm implements IEmergencySearchForm {
  public empUuid: number | 0;

  constructor(obj?: IEmergencySearchForm) {
    this.empUuid = (obj && obj.empUuid) || 0;
  }
}
