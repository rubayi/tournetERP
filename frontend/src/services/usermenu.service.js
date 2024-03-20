import api from "./api";

class UserMenuService {
    saveUserMenu(comreq) {
        return api
            .post("/userMenus/createUserMenus", comreq)
            .then((response) => {
                return response.data;
            });
    }

    deleteUserMenu(comreq) {
        return api
            .post("/userMenus/deleteUserMenus", comreq)
            .then((response) => {
                return response.data;
            });
    }
}

export default new UserMenuService();
