import api from "./api";
import authHeader from "src/services/auth-header";
class CdcdMngService {

  searchCdcdMngList(cmpReq) {
    return api
      .post(`/cdCdMng/selectCreditCardMngs`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getCdcdMngById(id) {
    return api
      .get(`/cdCdMng/searchCreditCardMngBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteCdcdMng(id) {
    return api
      .post(`/cdCdMng/deleteCreditCardMng/${id}`, { headers: authHeader() });
  }

  updateCdcdMng(cdcdMngReq) {

    return api
        .post(`/cdCdMng/updateCreditCardMng`, cdcdMngReq,{ headers: authHeader() });
  }

}

export default new CdcdMngService();