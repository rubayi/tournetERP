import api from "./api";
import authHeader from "src/services/auth-header";

class EmpEmergencyService {

    searchEmpEmergencyList(cmpReq) {
        return api
            .post(`/employeeEmergency/selectEmployeeEmergencys`, cmpReq, { headers: authHeader() })
            .then((response) => {
                return response.data;
            });
    }

    selectEmployeeEmergencyByEmpUuid(id) {
        return api
            .get(`/employeeEmergency/selectEmployeeEmergencyByEmpUuid/${id}`, { headers: authHeader() })
            .then((response) => {
                return response.data;
            });
    }

    deleteEmpEmergency(id) {
        return api
            .delete(`/employeeEmergency/deleteemployeeEmergency/${id}`, { headers: authHeader() });
    }

    updateEmpEmergency(cmpReq) {
        return api.post(`/employeeEmergency/updateEmployeeEmergency`, cmpReq, { headers: authHeader() });
    }

    createEmpEmergency(cmpReq) {
        return api.post(`/employeeEmergency/createEmployeeEmergency`, cmpReq, { headers: authHeader() });
    }
}

export default new EmpEmergencyService();