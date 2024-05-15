import api from './api';
import { ContactForm } from 'src/types/ContactForm';
import { ContactSearchForm } from 'src/types/ContactSearchForm';

import authHeader from 'src/services/auth-header';

const API_URL = '/contact/';

export class ContactService {
  static getContactList(
    contactSearchForm: ContactSearchForm
  ): Promise<ContactForm[]> {
    return api
      .post<ContactForm[]>(
        API_URL + 'searchContactByCondition',
        contactSearchForm,
        {
          headers: authHeader(),
        }
      )
      .then((response) => response.data);
  }

  static getContactForm(id: number): Promise<ContactForm[]> {
    return api
      .get<ContactForm[]>(API_URL + `selectByContactUuid/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveContactForm(contactForm: ContactForm): Promise<ContactForm> {
    return api
      .post<ContactForm>(API_URL + 'updateContact', contactForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteContactForm(id: number): Promise<void> {
    return api
      .delete<void>(API_URL + `deleteContact/${id}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
