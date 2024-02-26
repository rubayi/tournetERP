import CommonCodeService from "../services/comcode.service";

const commCode =
{
  codeUuid: null,
  codeKr: "",
  codeEn: "",
  codeAbb: "",
  codeValue: "",
  codeLvl: "",
  codeOrd: null,
  uprCodeUuid: null,
  useYn: "",
  etc: ""
};
const initialState = commCode
  ? { message: "", commCode }
  : { message: "", commCode: null };

export const comCode = {
  namespaced: true,
  state: initialState,
  actions: {
    getComCodeList({ commit }) {
      console.log("getComCodeList");
      return CommonCodeService.getComCodeList().then(
        data => {
          commit('searchCommonCodes', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comCodeError');
          return Promise.reject(error);
        }
      );
    },
    useComCode({ commit }, comreq) {
      return CommonCodeService.useComCode(comreq).then(
        data => {
          commit('searchCommonCode', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comCodeError');
          return Promise.reject(error);
        }
      );
    },
    deleteComcode({ commit }, id) {
      return CommonCodeService.deleteComcode(id).then(
        data => {
          commit('deleteCommonCode', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comCodeError');
          return Promise.reject(error);
        }
      );

    },
    createComcode({ commit }, comreq) {
      return CommonCodeService.createComcode(id).then(
        data => {
          commit('createSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comCodeError');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    searchCommonCodes(state, data) {
      state.comCode = data;
    },
    searchCommonCode(state, data) {
      state.comCode = data;
    },
    deleteCommonCode(state) {
      state.success = true;
    },
    createSuccess(state) {
    },
    comCodeError(state) {
    },
  }
};
