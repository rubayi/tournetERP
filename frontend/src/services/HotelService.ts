import api from './api';
import { HotelForm } from 'src/types/HotelForm';
import { HotelSearchForm } from 'src/types/HotelSearchForm';
import authHeader from 'src/services/auth-header';

const API_URL = '/hotel/';

export class HotelService {
  static selectHotels(hotelSearchForm: HotelSearchForm): Promise<HotelForm[]> {
    return api
      .post<HotelForm[]>(API_URL + 'selectHotels', hotelSearchForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static searchHotelByTour(tourUuid: number): Promise<HotelForm> {
    return api
      .get<HotelForm>(API_URL + `searchHotelByTour/${tourUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveHotelForm(hotelForm: HotelForm): Promise<HotelForm> {
    return api
      .post<HotelForm>(API_URL + 'updateHotel', hotelForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteHotelForm(hotelUuid: number): Promise<void> {
    return api
      .get<void>(API_URL + `deleteHotel/${hotelUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
