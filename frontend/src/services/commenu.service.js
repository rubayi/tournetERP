import api from "./api";

class ComMenuService {
  getComMenuList() {
    return api
      .get("/comMenus/selectComMenus")
      .then((response) => {
        return response.data;
      });
  }

  getComMenuListByGrp() {
    return api
      .post("/comMenus/selectComMenusByLvl", comreq)
      .then((response) => {
        return response.data;
      });
  }

  getMainComMenuList(comreq) {
    return api
      .post("/comMenus/useComMenuByRole", comreq)
      .then((response) => {
        return response.data;
      });
  }

  deleteComMenu(id) {
    return api
      .delete(`/comMenus/deleteComMenu/${id}`);
  }

  createComMenu(comreq) {
    return api.post("/comMenus/createComMenu", comreq);
  }

  updateComMenu(comreq) {
    return api.put(`/comMenus/updateComMenu`, comreq);
  }

  // ------------------ UserMenu ------------------
  saveUserMenu(comreq) {
    return api
      .put("/userMenus/updateUserMenus", comreq)
      .then((response) => {
        return response.data;
      });
  }
}

export default new ComMenuService();
