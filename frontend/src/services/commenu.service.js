import api from "./api";

class ComMenuService {
  getComMenuList() {
    return api
      .get("/comMenus/selectComMenus")
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
}

export default new ComMenuService();
