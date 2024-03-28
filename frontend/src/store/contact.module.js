import ContactService from "../services/contact.service";

const contact =
{ contactUuid: 0,
  contactSector: "", contactGroup: "", contactAbb: "",
  contactKor: "", contactEng: "", contactColor: "",
  logoFile: "", estDate: "", contactRate: "",
  minAge: "", childAge: "",
  youthAge: "", couponYn: "", prepaidHow: "",
  beginDt: "", endDt: "", createdDt: "",
  createUserName: "", modifyUserName: "",
  modifiedDt:"",
};

const initialState = contact
  ? { message: "", contact }
  : { message: "", contact: null };

export const contactTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchContactList({ commit }, contactReq) {
      return ContactService.searchContactList(contactReq).then(
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
      return ContactService.getContactById(id).then(
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
      return ContactService.deleteContact(id).then(
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
      return ContactService.updateContact(comreq).then(
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
      return ContactService.createContact(comreq).then(
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
