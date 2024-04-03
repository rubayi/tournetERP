import EmpEmergencyService from "../services/empemergency.service";

const empEmergency =
{
    emergencyUuid: 0,
    empUuid: 0, emerName: "", emerRelation: "",
    emerAddress: "", emerAddress2: "", emerCity: "",
    emerState: "", emerZip: "", emerWork: "",
    emerPhone1: "", emerPhone2: "", emerWorkPhone: "",
};

const initialState = empEmergency
    ? { message: "", empEmergency }
    : { message: "", empEmergency: null };

export const empEmergencyTn = {
    namespaced: true,
    state: initialState,
    actions: {
        searchEmpEmergencyList({ commit }, empEmergencyReq) {
            return EmpEmergencyService.searchEmpEmergencyList(empEmergencyReq).then(
                data => {
                    commit('searchEmpEmergencys', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('empEmergencyError');
                    return Promise.reject(error);
                }
            );
        },

        selectEmployeeEmergencyByEmpUuid({ commit }, id) {
            return EmpEmergencyService.selectEmployeeEmergencyByEmpUuid(id).then(
                data => {
                    commit('getEmpEmergency', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('empEmergencyError');
                    return Promise.reject(error);
                }
            );
        },

        deleteEmpEmergency({ commit }, id) {
            return EmpEmergencyService.deleteEmpEmergency(id).then(
                data => {
                    commit('deleteEmpEmergency', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('empEmergencyError');
                    return Promise.reject(error);
                }
            );
        },

        updateEmpEmergency({ commit }, empEmergencyReq) {
            return EmpEmergencyService.updateEmpEmergency(empEmergencyReq).then(
                data => {
                    commit('updateEmpEmergency', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('empEmergencyError');
                    return Promise.reject(error);
                }
            );
        },

        createEmpEmergency({ commit }, empEmergencyReq) {
            return EmpEmergencyService.createEmpEmergency(empEmergencyReq).then(
                data => {
                    commit('createEmpEmergency', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('empEmergencyError');
                    return Promise.reject(error);
                }
            );
        },
    },
    mutations: {
        searchEmpEmergencys(state, data) {
            state.empEmergency = data;
        },
        getEmpEmergency(state, data) {
            state.empEmergency = data;
        },
        deleteEmpEmergency(state, data) {
            state.empEmergency = data;
        },
        updateEmpEmergency(state, data) {
            state.empEmergency = data;
        },
        createEmpEmergency(state, data) {
            state.empEmergency = data;
        },
        empEmergencyError(state) {

        },
    }
};