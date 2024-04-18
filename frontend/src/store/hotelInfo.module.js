import HotelService from "../services/hotelInfo.service";

const hotel =
{ hotelUuid: 0, tourUuid: 0,
  childAgeBreakfast: 0, checkinTime: "", checkoutTime: "",
  resortFee: "", createdDt: "",
  createUserName: "", modifyUserName: "",
  modifiedDt:"",
};

const initialState = hotel
  ? { message: "", hotel }
  : { message: "", hotel: null };

export const hotelTn = {
  namespaced: true,
  state: initialState,
  actions: {
    searchHotelList({ commit }, hotelReq) {
      return HotelService.searchHotelList(hotelReq).then(
        data => {
          commit('searchHotels', data);
          return Promise.resolve(data);
        },
        error => {
          commit('hotelError');
          return Promise.reject(error);
        }
      );
    },

    getHotelById({ commit }, id) {
      return HotelService.getHotelById(id).then(
        data => {
          commit('searchHotel', data);
          return Promise.resolve(data);
        },
        error => {
          commit('hotelError');
          return Promise.reject(error);
        }
      );
    },

    getHotelByTourUuid({ commit }, id) {
      return HotelService.getHotelByTourUuid(id).then(
          data => {
            commit('searchHotel', data);
            return Promise.resolve(data);
          },
          error => {
            commit('hotelError');
            return Promise.reject(error);
          }
      );
    },
    deleteHotel({ commit }, id) {
      return HotelService.deleteHotel(id).then(
        data => {
          commit('deleteHotel', data);
          return Promise.resolve(data);
        },
        error => {
          commit('hotelError');
          return Promise.reject(error);
        }
      );

    },

    updateHotel({ commit }, hotelreq) {

      return HotelService.updateHotel(hotelreq).then(
        data => {
          commit('updateSuccess', data);
          return Promise.resolve(data);
        },
        error => {
          commit('hotelError');
          return Promise.reject(error);
        }
      );
    },

  },
  mutations: {
    searchHotels(state, data) {
      state.hotel = data;
    },
    searchMainHotels(state, data) {
      state.hotel = data;
    },

    searchHotel(state, data) {
      state.hotel = data;
    },
    deleteHotel(state) {
      state.success = true;
    },
    updateSuccess(state) {
      state.success = true;
    },
    hotelError(state) {
    },
  }
};
