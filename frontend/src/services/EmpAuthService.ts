import api from './api';
import { EmpAuthForm } from 'src/types/EmpAuthForm';
import { MenuAuthForm } from 'src/types/MenuAuthForm';
import { EmpAuthSearchForm } from 'src/types/EmpAuthSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/empEmpMenuAuth/';

export interface EmpAuthResponse {
  menuAuths: EmpAuthForm[];
  maxNumber: number;
}

export interface MenuAuthResponse {
  menuAuths: MenuAuthForm[];
  maxNumber: number;
}

export class EmpAuthService {
  static selectMenuAuths(): Promise<MenuAuthResponse> {
    return api
      .post<MenuAuthResponse>('/menuAuth/' + 'selectMenuAuths', {
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
