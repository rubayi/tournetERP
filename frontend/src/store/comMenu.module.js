import ComMenuService from "../services/commenu.service";

const commMenu =
{
  menuUuid: null,
  menuKor: "",
  menuEng: "",
  menuIcon: "",
  menuUrl: "",
  menuLvl: "",
  menuOrd: null,
  upperMenuUuid: null,
  useYn: "",
  menuDesc: ""
};
const initialState = commMenu
  ? { message: "", commMenu }
  : { message: "", commMenu: null };

export const comMenu = {
  namespaced: true,
  state: initialState,
  actions: {
    getComMenuList({ commit }) {
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
    getComMenuListByGrp({ commit }) {
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
    getComMenuListForEdit({ commit }, comreq) {
      return ComMenuService.getComMenuListForEdit(comreq).then(
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
    },
    saveUserMenu({ commit }, comreq) {
      return ComMenuService.saveUserMenu(comreq).then(
        data => {
          commit('userMenuSaveSuccess', data);
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
    userMenuSaveSuccess(state) {
      state.success = true;
    }
  }
};
