import TourService from "../services/tour.service";

const tour =
{ tourUuid: 0,
  tourSector: "", tourGroup: "", tourAbb: "",
  tourKor: "", tourEng: "", tourColor: "",
  logoFile: "", estDate: "", tourRate: "",
  minAge: "", childAge: "",
  youthAge: "", couponYn: "", prepaidHow: "",
  beginDt: "", endDt: "", createdDt: "",
  createUserName: "", modifyUserName: "",
  modifiedDt:"",
};

const initialState = tour
  ? { message: "", tour }
  : { message: "", tour: null };

export const tourTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchTourList({ commit }, tourReq) {
      return TourService.searchTourList(tourReq).then(
        data => {
          commit('searchTours', data);
          return Promise.resolve(data);
        },
        error => {
          commit('tourError');
          return Promise.reject(error);
        }
      );
    },

    getTourById({ commit }, id) {
      return TourService.getTourById(id).then(
        data => {
          commit('searchTour', data);
          return Promise.resolve(data);
        },
        error => {
          commit('tourError');
          return Promise.reject(error);
        }
      );
    },

    deleteTour({ commit }, id) {
      return TourService.deleteTour(id).then(
        data => {
          commit('deleteTour', data);
          return Promise.resolve(data);
        },
        error => {
          commit('tourError');
          return Promise.reject(error);
        }
      );

    },

    updateTour({ commit }, tourreq) {

      return TourService.updateTour(tourreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('tourError');
          return Promise.reject(error);
        }
      );
    },

  },
  mutations: {
    searchTours(state, data) {
      state.tour = data;
    },
    searchMainTours(state, data) {
      state.tour = data;
    },

    searchTour(state, data) {
      state.tour = data;
    },
    deleteTour(state) {
      state.success = true;
    },
    createSuccess(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    tourError(state) {
    },
  }
};
