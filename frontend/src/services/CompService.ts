import api from './api';
import { CompForm } from 'src/types/CompForm';
import { CompSearchForm } from 'src/types/CompSearchForm';
import authHeader from 'src/services/auth-header';

const API_URL = '/comp/';
export class CompService {
  static getAllComp(casesearchform: CompSearchForm): Promise<CompForm[]> {
    return api
      .post<CompForm[]>(API_URL + 'searchCompByCondition', casesearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneCompForm(compUuid: number): Promise<CompForm> {
    return api
      .get<CompForm>(API_URL + `getComp/${compUuid}`, {
        headers: authHeader(),
      })
      .then((response) => {
        return response.data;
      });
  }

  static saveCompForm(
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    attachFile: any,
    h3caseform: CompForm
  ): Promise<CompForm> {
    const formData = new FormData();

    formData.append('companyReq', JSON.stringify(h3caseform));
    if (attachFile) {
      formData.append('file', attachFile);
    }

    console.log(formData);
    return api.post('/comp/updateComp', formData, {
      headers: { ...authHeader(), 'Content-Type': 'multipart/form-data' },
    });
  }

  static deleteCompForm(compUuid: number): Promise<void> {
    return api
      .get(API_URL + `deleteComp/${compUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getGroupreceivedbyEntryForm(): Promise<string[]> {
    return api
      .get<string[]>(API_URL + 'apientryreceivedbylist', {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getGrouppostedbyEntryForm(): Promise<string[]> {
    return api
      .get<string[]>(API_URL + 'apientrypostedbylist', {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
