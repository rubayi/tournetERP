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
      .delete(`/menuAuth/deleteEmp/${id}`, { headers: authHeader() });
  }

  updateEmpAuth(cmpReq) {
    return api.put(`/menuAuth/updateEmpMenuAuth`, cmpReq, { headers: authHeader() });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }
}

export default new EmpMenuAuthService();
