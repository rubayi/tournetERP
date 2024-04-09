import CdcdMngService from "src/services/cdcdmng.service";
import EmpService from "src/services/emp.service";

const cdcdmng =
{ creditCardUuid: 0,
  nameOnCard: "", cardNumber: "", expMonth: "",
  mngNameKor: "", mngNameEng: "", expYear: "",
  createdDt: "", createUserName: "",
  modifyUserName: "", modifiedDt:"",
};

const initialState = cdcdmng
  ? { message: "", cdcdmng }
  : { message: "", cdcdmng: null };

export const cdcdmngTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchCdcdmngList({ commit }, comreq) {
      return CdcdMngService.searchCdcdMngList(comreq).then(
        data => {
          commit('searchCdcdmngs', data);
          return Promise.resolve(data);
        },
        error => {
          commit('cdcdmngError');
          return Promise.reject(error);
        }
      );
    },

    getCdcdmngById({ commit }, id) {
      return CdcdMngService.getCdcdMngById(id).then(
        data => {
          commit('searchCdcdmng', data);
          return Promise.resolve(data);
        },
        error => {
          commit('cdcdmngError');
          return Promise.reject(error);
        }
      );
    },

    deleteCdcdmng({ commit }, id) {
      return CdcdMngService.deleteCdcdMng(id).then(
        data => {
          commit('deleteCdcdmng', data);
          return Promise.resolve(data);
        },
        error => {
          commit('cdcdmngError');
          return Promise.reject(error);
        }
      );

    },

    updateCdcdmng({ commit }, comreq) {

      return CdcdMngService.updateCdcdMng(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('cdcdmngError');
          return Promise.reject(error);
        }
      );
    },

    // updateCdcdmng({ commit }, comreq) {
    //   console.log(comreq);
    //   return EmpService.updateEmp(comreq).then(
    //       data => {
    //         commit('updateSuccess', data);
    //         return Promise.resolve(data);
    //       },
    //       error => {
    //         commit('empError');
    //         return Promise.reject(error);
    //       }
    //   );
    // }
  },
  mutations: {
    searchCdcdmngs(state, data) {
      state.cdcdmng = data;
    },
    searchMainCdcdmngs(state, data) {
      state.cdcdmng = data;
    },

    searchCdcdmng(state, data) {
      state.cdcdmng = data;
    },
    deleteCdcdmng(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    cdcdmngError(state) {
    },
  }
};
