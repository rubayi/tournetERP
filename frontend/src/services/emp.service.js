import api from "./api";
import authHeader from "src/services/auth-header";

class EmpService {

  searchEmpList(cmpReq) {
    return api
      .post(`/empTn/searchEmpByCondition`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getEmpById(id) {
    return api
      .get(`/empTn/searchEmpBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteEmp(id) {
    return api
      .delete(`/empTn/deleteEmp/${id}`, { headers: authHeader() });
  }

  updateEmp(cmpReq) {
    return api.put(`/empTn/updateEmp`, cmpReq, { headers: authHeader() });
  }
}

export default new EmpService();
