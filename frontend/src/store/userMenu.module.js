import UserMenuService from "../services/usermenu.service";

const userrMenu =
{
    empMunuUuid: null,
    menuUuid: null,
    roleUuid: null,
    menuDelete: "",
    menuRead: "",
    menuWrite: "",
};
const initialState = userrMenu
    ? { message: "", userrMenu }
    : { message: "", userrMenu: null };

export const userMenu = {
    namespaced: true,
    state: initialState,
    actions: {
        saveUserMenu({ commit }, comreq) {
            return UserMenuService.saveUserMenu(comreq).then(
                data => {
                    commit('userMenuSaveSuccess', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('userMenuError');
                    return Promise.reject(error);
                }
            );
        },
        deleteUserMenu({ commit }, comreq) {
            return UserMenuService.deleteUserMenu(comreq).then(
                data => {
                    commit('userMenuDeleteSuccess', data);
                    return Promise.resolve(data);
                },
                error => {
                    commit('userMenuError');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        userMenuError(state) {
        },
        userMenuSaveSuccess(state) {
            state.success = true;
        },
        userMenuDeleteSuccess(state) {
            state.success = true;
        }
    }
};
