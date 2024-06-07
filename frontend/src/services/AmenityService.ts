import api from './api';
import { AmenityForm } from 'src/types/AmenityForm';
import { AmenitySearchForm } from 'src/types/AmenitySearchForm';
import authHeader from 'src/services/auth-header';

const API_URL = '/amenity/';

export class AmenityService {
  static selectAmenities(
    amenitySearchForm: AmenitySearchForm
  ): Promise<AmenityForm[]> {
    return api
      .post<AmenityForm[]>(API_URL + 'selectAmenities', amenitySearchForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static searchAmenityByTour(tourUuid: number): Promise<AmenityForm> {
    return api
      .get<AmenityForm>(API_URL + `searchAmenityByTour/${tourUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveAmenityForm(amenityForm: AmenityForm): Promise<AmenityForm> {
    return api
      .post<AmenityForm>(API_URL + 'updateAmenity', amenityForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteAmenityForm(amenityUuid: number): Promise<void> {
    return api
      .get<void>(API_URL + `deleteamenity/${amenityUuid}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
}
