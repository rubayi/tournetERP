import api from './api';
import { EmpAuthForm } from 'src/types/EmpAuthForm';
import { EmpAuthSearchForm } from 'src/types/EmpAuthSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/empEmpMenuAuth/';

export class EmpAuthService {
  static searchAuthList(): Promise<EmpAuthForm[]> {
    return api
      .get<EmpAuthForm[]>(API_URL + 'searchAuthList', {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static searchAuthListByEmpId(
    empReq: EmpAuthSearchForm
  ): Promise<EmpAuthForm[]> {
    return api
      .post<EmpAuthForm[]>(API_URL + 'selectEmpMenuAuths', empReq, {
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
