import { getNumberMemberValue } from 'src/utils/helpers/ConstructorHelper';

export interface IEmergencyForm {
  emergencyUuid: number | 0;
  emerAddress: string | '';
  emerName: string | '';
  emerPhone1: string | '';
  emerRelation: string | '';
  emerWorkPhone: string | '';
  empUuid: number | 0;
}

export class EmergencyForm implements IEmergencyForm {
  public emergencyUuid: number | 0;
  public emerAddress: string | '';
  public emerName: string | '';
  public emerPhone1: string | '';
  public emerRelation: string | '';
  public emerWorkPhone: string | '';
  public empUuid: number | 0;

  constructor(obj?: IEmergencyForm) {
    this.emergencyUuid = getNumberMemberValue(obj && obj.emergencyUuid);
    this.emerAddress = (obj && obj.emerAddress) || '';
    this.emerName = (obj && obj.emerName) || '';
    this.emerPhone1 = (obj && obj.emerPhone1) || '';
    this.emerRelation = (obj && obj.emerRelation) || '';
    this.emerWorkPhone = (obj && obj.emerWorkPhone) || '';
    this.empUuid = getNumberMemberValue(obj && obj.empUuid);
  }
}
