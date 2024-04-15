import api from "./api";
import authHeader from "src/services/auth-header";

class TrContactService {

  searchContactList(cmpReq) {
    return api
      .post(`/trContact/searchContactByCondition`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getContactById(id) {
    return api
      .get(`/trContact/searchContactBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteContact(id) {
    return api
      .post(`/trContact/deleteContact/${id}`, { headers: authHeader() });
  }

  updateContact(cmpReq) {
    return api.post(`/trContact/updateContact`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }

  createContact(cmpReq) {
    return api.post(`/trContact/createContact`, cmpReq, { headers: authHeader() });
  }
}

export default new TrContactService();
