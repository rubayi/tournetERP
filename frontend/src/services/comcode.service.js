import api from "./api";
import authHeader from "src/services/auth-header";

class ComcodeService {
  getComCodeList() {
    return api
      .get("/comCodes/comCodes", { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getMainComCodeList() {
    return api
      .get("/comCodes/GrpComCodes", { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getComCodeListByGrp(comreq) {
    return api
      .post("/comCodes/SearchComCodesByGrp", comreq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  useComCode(comreq) {
    return api
      .post("/comCodes/useComCodeByGrp", comreq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteComcode(id) {
    return api
      .delete(`/comCodes/deleteComcode/${id}`, { headers: authHeader() });
  }

  createComcode(comreq) {
    return api.post("/comCodes/createComCode", comreq, { headers: authHeader() });
  }

  updateComcode(comreq) {
    return api.put(`/comCodes/updateComCode`, comreq, { headers: authHeader() });
  }
}

export default new ComcodeService();
