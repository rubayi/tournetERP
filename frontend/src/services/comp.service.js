import api from "./api";
import authHeader from "src/services/auth-header";

class CompService {

  searchCompList(cmpReq) {
    return api
      .post(`/comp/searchCompByCondition`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getCompById(id) {
    return api
      .get(`/comp/searchCompBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteComp(id) {
    return api
      .post(`/comp/deleteComp/${id}`, { headers: authHeader() });
  }

  updateComp(cmpReq) {
    return api.post(`/comp/updateComp`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }

  createComp(cmpReq) {
    return api.post(`/comp/createComp`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }
}

export default new CompService();
