import api from "./api";
import authHeader from "src/services/auth-header";

class EmpMenuAuthService {

  searchAuthList(cmpReq) {
    return api
      .post(`/menuAuth/selectMenuAuths`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  searchAuthListByEmpId(cmpReq) {
    return api
      .post(`/empEmpMenuAuth/selectEmpMenuAuths`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }
  deleteEmpAuth(id) {
    return api
      .post(`/empEmpMenuAuth/deleteEmpMenuAuth`, cmpReq, { headers: authHeader() });
  }

  updateEmpAuth(cmpReq) {
    console.log(cmpReq);
    return api.post(`/empEmpMenuAuth/updateEmpMenuAuth`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }
}

export default new EmpMenuAuthService();
