import { createStore } from 'vuex';
import { UserDetails } from "src/types/UserDetails";
export default createStore({
  state: {
    currentUser: new UserDetails(),
  },
  getters: {
    currentUserIsLoggedIn: (state) => {
      return (
          state.currentUser &&
          state.currentUser.credentialsNonExpired &&
          state.currentUser.enabled
      );
    },
    currentUserRole: (state) => {
      return (

          state.currentUser.authorities
      );
    },
    currentUserHasApplicationRole: (state) => (role: string) => {
      // if (
      //     permission &&
      //     state.currentUser &&
      //     state.currentUser.enabled &&
      //     state.currentUser.credentialsNonExpired
      // ) {
      if (  state.currentUser &&
            state.currentUser.enabled &&
            state.currentUser.credentialsNonExpired
        ) {
        return state.currentUser.authorities.some(
            (p) => p.authority === role
        );
      }
      return false;
    },

    currentUserHasApplicationPermission : (state) => (permission: string) => {
      if (
          permission &&
          state.currentUser &&
          state.currentUser.enabled &&
          state.currentUser.credentialsNonExpired
      ) {
        return state.currentUser.authorities.some(
            (p) => p.authority === permission
        );
      }
      return false;
    },
  },
  mutations: {
    updateCurrentUser(state, updatedValue) {
      if (updatedValue && updatedValue instanceof UserDetails) {
        state.currentUser = new UserDetails(updatedValue);
      } else {
        state.currentUser = updatedValue;
      }
    },
    clearCurrentUser(state) {
      state.currentUser = new UserDetails();
    },
  },
  actions: {
    // Your actions here
  },
  modules: {
    // Your modules here
  }
});
