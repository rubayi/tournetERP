import api from './api';
import { InfoForm } from 'src/types/InfoForm';
import { InfoSearchForm } from 'src/types/InfoSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/info/';

export class InfoService {
  static getInfoList(
    contactSearchForm: InfoSearchForm
  ): Promise<InfoForm[]> {
    return api
      .post<InfoForm[]>(
        API_URL + 'searchInfoByCondition',
        contactSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getInfoForm(id: number): Promise<InfoForm> {
    return api
      .get<InfoForm>(API_URL + `searchInfoByInfoUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveInfoForm(
    contactForm: InfoForm
  ): Promise<InfoForm> {
    return api
      .post<InfoForm>(API_URL + 'updateInfo', contactForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteInfoForm(id: number): Promise<void> {
    return api
      .get<void>(API_URL + `deleteInfo/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
