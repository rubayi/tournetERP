import api from "./api";

class ComcodeService {
  getComCodeList() {
    return api
      .get("/comCodes/comCodes")
      .then((response) => {
        return response.data;
      });
  }

  useComCode(comreq) {
    return api
      .post("/comCodes/useComCodeByGrp", comreq)
      .then((response) => {
        return response.data;
      });
  }

  deleteComcode({ id }) {
    return api
      .delete("/comCodes/deleteComcode/{id}");
  }

  createComcode(comreq) {
    return api.post("/comCodes/createComCode", comreq);
  }
}

export default new ComcodeService();
