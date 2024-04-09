import api from "./api";
import authHeader from "src/services/auth-header";
class TourService {

  searchTourList(cmpReq) {
    return api
      .post(`/tour/selectTours`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getTourById(id) {
    return api
      .get(`/tour/searchTourBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteTour(id) {
    return api
      .post(`/tour/deleteTour/${id}`, { headers: authHeader() });
  }


  updateTour(cdcdMngReq) {

    return api
        .post(`/tour/updateTour`, cdcdMngReq,{ headers: authHeader() });
  }


}

export default new TourService();
