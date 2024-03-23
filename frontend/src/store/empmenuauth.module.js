import EmpMenuAuthService from "../services/empmenuauth.service";

const Auth =
{ menuAuthUuid: 0,
  menuAuthName: "",
  menuAuthNameEng: "",
  empUuid: ""
};

const initialState = Auth
  ? { message: "", Auth }
  : { message: "", Auth: null };

export const empMenuAuth = {
  namespaced: true,
  state: initialState,
  actions: {
    searchAuthList({ commit }) {
      return EmpMenuAuthService.searchAuthList().then(
        data => {
          commit('searchAuthList', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );
    },

    searchAuthListByEmpId({ commit }, empReq) {
      return EmpMenuAuthService.searchAuthListByEmpId(empReq).then(
        data => {
          commit('searchAuthListByEmpId', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );
    },


    deleteEmpAuth({ commit }, comreq) {
      return EmpMenuAuthService.deleteEmpAuth(comreq).then(
        data => {
          commit('deleteEmpAuth', data);
          return Promise.resolve(data);
        },
        error => {
          commit('empError');
          return Promise.reject(error);
        }
      );

    },

    updateEmpAuth({ commit }, comreq) {
      return EmpMenuAuthService.updateEmpAuth(comreq).then(
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
    searchAuthList(state, data) {
      state.Auth = data;
    },
    searchAuthListByEmpId(state, data) {
      state.Auth = data;
    },
    updateEmpAuth(state, data) {
      state.Auth = data;
    },
    deleteEmpAuth(state) {
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
