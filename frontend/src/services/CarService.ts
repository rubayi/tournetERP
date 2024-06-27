import api from './api';
import { CarForm } from 'src/types/CarForm';
import { CarSearchForm } from 'src/types/CarSearchForm';
import authHeader from 'src/services/auth-header';
import {CdcdForm} from "@/types/CdcdForm";

const API_URL = '/tournetCar/';
export class CarService {
  static getAllCar(casesearchform: CarSearchForm): Promise<CarForm[]> {
    return api
      .post<CarForm[]>(API_URL + 'selectTrnCars', casesearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneCarForm(compUuid: number): Promise<CarForm> {
    return api
      .get<CarForm>(API_URL + `searchTrnCar/${compUuid}`, {
        headers: authHeader(),
      })
      .then((response) => {
        return response.data;
      });
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static updateCarForm(
    carForm: CarForm
  ): Promise<CarForm> {
    return api.post<CarForm>('/tournetCar/updateTrnCar', carForm,
      { headers: authHeader() })
      .then((response) => response.data);

  }

  static deleteCarForm(compUuid: number): Promise<void> {
    return api
      .get(API_URL + `deleteTrnCar/${compUuid}`, {
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
