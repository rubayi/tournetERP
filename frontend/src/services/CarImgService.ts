import api from './api';
import { CarImgForm } from 'src/types/CarImgForm';
import { CarImgSearchForm } from 'src/types/CarImgSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/carImg/';

export class CarImgService {
  static getCarImgList(
    contactSearchForm: CarImgSearchForm
  ): Promise<CarImgForm[]> {
    return api
      .post<CarImgForm[]>(API_URL + 'selectCarImgs', contactSearchForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getCarImgForm(id: number): Promise<CarImgForm> {
    return api
      .get<CarImgForm>(API_URL + `selectByCarImgUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveCarImgForm(
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    attachFile: any,
    h3caseform: CarImgForm
  ): Promise<CarImgForm> {
    const formData = new FormData();

    formData.append('companyReq', JSON.stringify(h3caseform));
    if (attachFile) {
      formData.append('file', attachFile);
    }

    return api
      .post<CarImgForm>(API_URL + 'updateCarImg', formData, {
        headers: { ...authHeader(), 'Content-Type': 'multipart/form-data' },
      })
      .then((response) => response.data);
  }

  static deleteCarImgForm(id: number): Promise<void> {
    return api
      .delete<void>(API_URL + `deleteCarImg/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
