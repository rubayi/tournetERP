import CompService from "../services/comp.service";

const comp =
{ compUuid: 0,
  compSector: "", compGroup: "", compAbb: "",
  compKor: "", compEng: "", compColor: "",
  logoFile: "", estDate: "", compRate: "",
  minAge: "", childAge: "",
  youthAge: "", couponYn: "", prepaidHow: "",
  beginDt: "", endDt: "", createdDt: "",
  createUserName: "", modifyUserName: "",
  modifiedDt:"",
};

const initialState = comp
  ? { message: "", comp }
  : { message: "", comp: null };

export const compTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchCompList({ commit }, compReq) {
      return CompService.searchCompList(compReq).then(
        data => {
          commit('searchComps', data);
          return Promise.resolve(data);
        },
        error => {
          commit('compError');
          return Promise.reject(error);
        }
      );
    },

    getCompById({ commit }, id) {
      return CompService.getCompById(id).then(
        data => {
          commit('searchComp', data);
          return Promise.resolve(data);
        },
        error => {
          commit('compError');
          return Promise.reject(error);
        }
      );
    },

    deleteComp({ commit }, id) {
      return CompService.deleteComp(id).then(
        data => {
          commit('deleteComp', data);
          return Promise.resolve(data);
        },
        error => {
          commit('compError');
          return Promise.reject(error);
        }
      );

    },

    updateComp({ commit }, attachFile, compreq) {

      return CompService.updateComp(attachFile, compreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('compError');
          return Promise.reject(error);
        }
      );
    },

    createComp({ commit }, comreq) {
      return CompService.createComp(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('compError');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    searchComps(state, data) {
      state.comp = data;
    },
    searchMainComps(state, data) {
      state.comp = data;
    },
    createComp(state, data) {
      state.comp = data;
    },
    searchComp(state, data) {
      state.comp = data;
    },
    deleteComp(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    compError(state) {
    },
  }
};
