import api from "./api";
import authHeader from "src/services/auth-header";
class HotelService {

  searchHotelList(cmpReq) {
    return api
      .post(`/hotel/selectHotels`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getHotelById(id) {
    return api
      .get(`/hotel/searchHotelBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getHotelByTourUuid(id) {
    return api
        .get(`/hotel/searchHotelByTour/${id}`, { headers: authHeader() })
        .then((response) => {
          return response.data;
        });
  }
  deleteHotel(id) {
    return api
      .post(`/hotel/deleteHotel/${id}`, { headers: authHeader() });
  }


  updateHotel(cdcdMngReq) {

    return api
        .post(`/hotel/updateHotel`, cdcdMngReq,{ headers: authHeader() });
  }


}

export default new HotelService();
