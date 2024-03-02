import ComMenuService from "../services/commenu.service";

const commMenu =
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
const initialState = commMenu
  ? { message: "", commMenu }
  : { message: "", commMenu: null };

export const comMenu = {
  namespaced: true,
  state: initialState,
  actions: {
    getComMenuList({ commit }) {
      console.log("getComMenuList");
      return ComMenuService.getComMenuList().then(
        data => {
          commit('searchComMenus', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comMenuError');
          return Promise.reject(error);
        }
      );
    },
    getMainComMenuList({ commit }, comreq) {
      return ComMenuService.getMainComMenuList(comreq).then(
        data => {
          commit('searchMainComMenus', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comMenuError');
          return Promise.reject(error);
        }
      );
    },

    deleteComMenu({ commit }, id) {
      return ComMenuService.deleteComMenu(id).then(
        data => {
          commit('deleteComMenu', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comMenuError');
          return Promise.reject(error);
        }
      );

    },
    createComMenu({ commit }, comreq) {
      return ComMenuService.createComMenu(comreq).then(
        data => {
          commit('createSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comMenuError');
          return Promise.reject(error);
        }
      );
    },
    updateComMenu({ commit }, comreq) {
      return ComMenuService.updateComMenu(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('comMenuError');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    searchComMenus(state, data) {
      state.comMenu = data;
    },
    searchMainComMenus(state, data) {
      state.comMenu = data;
    },
    createComMenu(state, data) {
      state.comMenu = data;
    },
    searchComMenu(state, data) {
      state.comMenu = data;
    },
    deleteComMenu(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    comMenuError(state) {
    },
  }
};
