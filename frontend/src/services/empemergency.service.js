import api from "./api";
import authHeader from "src/services/auth-header";

class EmpEmergencyService {

    searchEmpEmergencyList(empEmergencyReq) {
        return api
            .post(`/employeeEmergency/selectEmployeeEmergencys`, empEmergencyReq, { headers: authHeader() })
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

    updateEmpEmergency(empEmergencyReq) {
        return api.post(`/employeeEmergency/updateEmployeeEmergency`, empEmergencyReq, { headers: authHeader() });
    }

    createEmpEmergency(empEmergencyReq) {
        return api.post(`/employeeEmergency/createEmployeeEmergency`, empEmergencyReq, { headers: authHeader() });
    }
}

export default new EmpEmergencyService();