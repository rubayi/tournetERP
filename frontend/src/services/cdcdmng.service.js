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

  updateCdcdMng(attachFile, cdcdMngReq) {

    let formData = new FormData();

    formData.append("cdcdMngReq", JSON.stringify(cdcdMngReq));
    formData.append("file", attachFile);
    // for (let pair of formData.entries()) {
    //   console.log(pair[0] + ', ' + pair[1]);
    // }
    return api.post("/cdCdMng/updateCreditCardMng", formData,
      { headers: {...authHeader(), "Content-Type": "multipart/form-data"}  });

  }


}

export default new CdcdMngService();
