import api from './api';
import { EmpAuthForm } from 'src/types/EmpAuthForm';
import { EmpAuthSearchForm } from 'src/types/EmpAuthSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/empEmpMenuAuth/';

export interface EmpAuthResponse {
  menuAuths: EmpAuthForm[];
  maxNumber: number;
}

export class EmpAuthService {
  static selectMenuAuths(): Promise<EmpAuthResponse> {
    return api
      .post<EmpAuthResponse>('/menuAuth/' + 'selectMenuAuths', {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static searchAuthListByEmpId(
    empReq: EmpAuthSearchForm
  ): Promise<EmpAuthResponse> {
    return api
      .post<EmpAuthResponse>(API_URL + 'selectEmpMenuAuths', empReq, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteEmpAuth(comreq: EmpAuthSearchForm): Promise<EmpAuthForm[]> {
    return api
      .post<EmpAuthForm[]>(API_URL + 'deleteEmpAuth', comreq, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static updateEmpAuth(comreq: EmpAuthSearchForm): Promise<EmpAuthForm> {
    return api
      .post<EmpAuthForm>(API_URL + 'updateEmpAuth', comreq, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
