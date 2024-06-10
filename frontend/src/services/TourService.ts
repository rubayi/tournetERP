import api from "./api";
import { TourForm } from "src/types/TourForm";
import { TourSearchForm } from "src/types/TourSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/tour/";
export class TourService {


  static getAll(toursearchform: TourSearchForm): Promise<TourForm[]> {
    return api
      .post<TourForm[]>(API_URL + "selectTours", toursearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneTourForm(tourUuid: number): Promise<TourForm> {
    return api
      .get<TourForm>(API_URL + `searchTour/${tourUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveTourForm(tourform: TourForm): Promise<TourForm> {
    return api
      .post<TourForm>(API_URL + "updateTour", tourform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteTourForm(tourUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteTour/${tourUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
