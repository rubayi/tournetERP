import api from './api';
import { TourContactForm } from 'src/types/TourContactForm';
import { TourContactSearchForm } from 'src/types/TourContactSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/trContact/';

export class TourContactService {
  static getTourContactList(
    contactSearchForm: TourContactSearchForm
  ): Promise<TourContactForm[]> {
    return api
      .post<TourContactForm[]>(
        API_URL + 'searchContactByCondition',
        contactSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getTourContactForm(id: number): Promise<TourContactForm> {
    return api
      .get<TourContactForm>(API_URL + `selectByContactUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveTourContactForm(contactForm: TourContactForm): Promise<TourContactForm> {
    return api
      .post<TourContactForm>(API_URL + 'updateContact', contactForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteTourContactForm(id: number): Promise<void> {
    return api
      .delete<void>(API_URL + `deleteContact/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}