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

  updateTour(attachFile, tourReq) {

    let formData = new FormData();

    formData.append("touranyReq", JSON.stringify(tourReq));
    formData.append("file", attachFile);
    // for (let pair of formData.entries()) {
    //   console.log(pair[0] + ', ' + pair[1]);
    // }
    return api.post("/tour/updateTour", formData,
      { headers: {...authHeader(), "Content-Type": "multipart/form-data"}  });

  }


}

export default new TourService();
