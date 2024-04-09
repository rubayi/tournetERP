import CdcdMngService from "../services/cdcdmng.service";

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
    searchCdcdmngList({ commit }, cdcdmngReq) {
      return CdcdMngService.searchCdcdMngList(cdcdmngReq).then(
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

    updateCdcdmng({ commit }, attachFile, cdcdmngreq) {

      return CdcdMngService.updateCdcdMng(attachFile, cdcdmngreq).then(
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
