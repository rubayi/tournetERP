import api from "./api";
import { HotelRoomForm } from "src/types/HotelRoomForm";
import { HotelRoomSearchForm } from "src/types/HotelRoomSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/hotelRoom/";
export class HotelRoomService {


  static getHotelRoomList(hotelroomearchform: HotelRoomSearchForm): Promise<HotelRoomForm[]> {
    return api
      .post<HotelRoomForm[]>(API_URL + "selectHotelRooms", hotelroomearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneHotelRoomForm(roomUuid: number): Promise<HotelRoomForm> {
    return api
      .get<HotelRoomForm>(API_URL + `searchHotelRoom/${roomUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveHotelRoomForm(tourform: HotelRoomForm): Promise<HotelRoomForm> {
    return api
      .post<HotelRoomForm>(API_URL + "updateHotelRoom", tourform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteHotelRoomForm(roomUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteHotelRoom/${roomUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
