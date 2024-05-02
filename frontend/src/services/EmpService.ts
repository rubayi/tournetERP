import api from './api';
import { EmpForm } from 'src/types/EmpForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/empTn/';

export class EmpService {
  static getAll(): Promise<EmpForm[]> {
    return api
      .get<EmpForm[]>(API_URL + 'selectEmps', {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getEmpForm(empUuid: number): Promise<EmpForm> {
    return api
      .get<EmpForm>(API_URL + `searchEmpBy/${empUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveEmpForm(empForm: EmpForm): Promise<EmpForm> {
    return api
      .post<EmpForm>(API_URL + 'updateEmp', empForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteEmpForm(empUuid: number): Promise<void> {
    return api
      .delete<void>(API_URL + `deleteEmp/${empUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
