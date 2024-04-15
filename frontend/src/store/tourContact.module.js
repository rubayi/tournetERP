import TrContactService from "../services/tourcontact.service";

const contact =
{ contactUuid: 0,
  tourUuid: "", contactGroup: "", contactAbb: "",
  contactKor: "", contactEng: "", contactColor: "",

};

const initialState = contact
  ? { message: "", contact }
  : { message: "", contact: null };

export const trContactTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchContactList({ commit }, contactReq) {
      return TrContactService.searchContactList(contactReq).then(
        data => {
          commit('searchContacts', data);
          return Promise.resolve(data);
        },
        error => {
          commit('contactError');
          return Promise.reject(error);
        }
      );
    },

    getContactById({ commit }, id) {
      return TrContactService.getContactById(id).then(
        data => {
          commit('searchContact', data);
          return Promise.resolve(data);
        },
        error => {
          commit('contactError');
          return Promise.reject(error);
        }
      );
    },

    deleteContact({ commit }, id) {
      return TrContactService.deleteContact(id).then(
        data => {
          commit('deleteContact', data);
          return Promise.resolve(data);
        },
        error => {
          commit('contactError');
          return Promise.reject(error);
        }
      );

    },

    updateContact({ commit }, comreq) {
      console.log(comreq);
      return TrContactService.updateContact(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('contactError');
          return Promise.reject(error);
        }
      );
    },

    createContact({ commit }, comreq) {
      return TrContactService.createContact(comreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('contactError');
          return Promise.reject(error);
        }
      );
    }

  },
  mutations: {
    searchContacts(state, data) {
      state.contact = data;
    },
    searchMainContacts(state, data) {
      state.contact = data;
    },
    createContact(state, data) {
      state.contact = data;
    },
    searchContact(state, data) {
      state.contact = data;
    },
    deleteContact(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    contactError(state) {
    },
  }
};
