import api from './api';
import { EmergencyForm } from 'src/types/EmergencyForm';
import { EmergencySearchForm } from 'src/types/EmergencySearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/employeeEmergency/';

export class EmergencyService {
  static getEmergencyList(
    emerSearchForm: EmergencySearchForm
  ): Promise<EmergencyForm[]> {
    return api
      .post<EmergencyForm[]>(
        API_URL + 'selectEmployeeEmergencys',
        emerSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getEmerForm(id: number): Promise<EmergencyForm[]> {
    return api
      .get<EmergencyForm[]>(
        API_URL + `selectEmployeeEmergencyByEmpUuid/${id}`,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getByEmerid(id: number): Promise<EmergencyForm> {
    return api
      .get<EmergencyForm>(API_URL + `selectByEmergencyUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveEmerForm(emerForm: EmergencyForm): Promise<EmergencyForm> {
    if (emerForm.emergencyUuid !== 0) {
      // If emergencyUuid is not zero, update the existing record
      return api
        .post<EmergencyForm>(API_URL + 'updateEmployeeEmergency', emerForm, {
          headers: authHeader(),
        })
        .then((response) => response.data);
    } else {
      // If emergencyUuid is zero, create a new record
      return api
        .post<EmergencyForm>(API_URL + 'createEmployeeEmergency', emerForm, {
          headers: authHeader(),
        })
        .then((response) => response.data);
    }
  }

  static deleteEmerForm(empUuid: number): Promise<void> {
    return api
      .delete<void>(API_URL + `deleteemployeeEmergency/${empUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
