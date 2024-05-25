import api from './api';
import { EmpForm } from 'src/types/EmpForm';
import { EmpSearchForm } from 'src/types/EmpSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/empTn/';

export class EmpService {
  static getAll(empSearchForm: EmpSearchForm): Promise<EmpForm[]> {
    return api
      .post<EmpForm[]>(API_URL + 'selectEmps', empSearchForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static searchEmpByCondition(
    empSearchForm: EmpSearchForm
  ): Promise<Map<string, unknown>> {
    return api
      .post<Map<string, unknown>>(
        API_URL + 'searchEmpByCondition',
        empSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static selectEmpsByCondition(
    empSearchForm: EmpSearchForm
  ): Promise<EmpForm[]> {
    return api
      .post<EmpForm[]>(API_URL + 'selectEmpsByCondition', empSearchForm, {
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

  static createEmpForm(empForm: EmpForm): Promise<EmpForm> {
    return api
      .post<EmpForm>(API_URL + 'createEmp', empForm, {
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
