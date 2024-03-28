import api from "./api";
import authHeader from "src/services/auth-header";

class ContactService {

  searchContactList(cmpReq) {
    return api
      .post(`/contact/searchContactByCondition`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getContactById(id) {
    return api
      .get(`/contact/searchContactBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteContact(id) {
    return api
      .delete(`/contact/deleteContact/${id}`, { headers: authHeader() });
  }

  updateContact(cmpReq) {
    return api.post(`/contact/updateContact`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }

  createContact(cmpReq) {
    return api.post(`/contact/createContact`, cmpReq, { headers: authHeader() });
  }
}

export default new ContactService();
