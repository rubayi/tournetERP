import api from './api';
import { TourServiceForm } from 'src/types/TourServiceForm';
import { TourServiceSearchForm } from 'src/types/TourServiceSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/info/';

export class TourServiceService {
  static getTourServiceList(
    contactSearchForm: TourServiceSearchForm
  ): Promise<TourServiceForm[]> {
    return api
      .post<TourServiceForm[]>(
        API_URL + 'searchTourServiceByCondition',
        contactSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getTourServiceForm(id: number): Promise<TourServiceForm> {
    return api
      .get<TourServiceForm>(API_URL + `selectByTourServiceUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveTourServiceForm(
    contactForm: TourServiceForm
  ): Promise<TourServiceForm> {
    return api
      .post<TourServiceForm>(API_URL + 'updateTourService', contactForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteTourServiceForm(id: number): Promise<void> {
    return api
      .get<void>(API_URL + `deleteTourService/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
