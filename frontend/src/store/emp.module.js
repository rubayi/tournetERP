import EmpService from "../services/emp.service";

const emp =
{ empUuid: 0,
  username: "", empEmail: "", password: "",
  empKor: "", empEng: "", empWorkType: "",
  empDiv: "", empTitle: "", empPhone: "",
  empWorkPhone: "", empEmailBook: "",
  empAddress1: "", empAddress2: "", empCity: "",
  empState: "", empCountry: "", empZip: "",
  empDob: "", empDobType: "",
  empStatus:"",
  empMemo: ""
};

const initialState = emp
  ? { message: "", emp }
  : { message: "", emp: null };

export const empTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchEmpList({ commit }, empReq) {
      return EmpService.searchEmpList(empReq).then(
        data => {
          commit('searchEmps', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );
    },

    getEmpById({ commit }, id) {
      return EmpService.getEmpById(id).then(
        data => {
          commit('searchEmp', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );
    },

    deleteEmp({ commit }, id) {
      return EmpService.deleteEmp(id).then(
        data => {
          commit('deleteEmp', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );

    },

    updateEmp({ commit }, comreq) {
      return EmpService.updateEmp(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    searchEmps(state, data) {
      state.emp = data;
    },
    searchMainEmps(state, data) {
      state.emp = data;
    },
    createEmp(state, data) {
      state.emp = data;
    },
    searchEmp(state, data) {
      state.emp = data;
    },
    deleteEmp(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    empError(state) {
    },
  }
};
