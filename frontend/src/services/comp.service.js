import api from "./api";
import authHeader from "src/services/auth-header";
class CompService {

  searchCompList(cmpReq) {
    return api
      .post(`/comp/searchCompByCondition`, cmpReq, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  getCompById(id) {
    return api
      .get(`/comp/searchCompBy/${id}`, { headers: authHeader() })
      .then((response) => {
        return response.data;
      });
  }

  deleteComp(id) {
    return api
      .post(`/comp/deleteComp/${id}`, { headers: authHeader() });
  }

  updateComp(attachFile, compReq) {

    let formData = new FormData();

    for (let key in compReq) {
      formData.append(key, compReq[key])
    }

    formData.append("file", attachFile);
    // return api.post(`/comp/updateComp`, formData, {
    //     headers: {"Content-Type": "multipart/form-data", Authorization: authHeader()}
    // });

    return api.post("/comp/updateComp", formData,
      { headers: {...authHeader(), "Content-Type": "multipart/form-data"}  });

  }


  createComp(cmpReq) {

    return api.post(`/comp/createComp`, cmpReq,
        { headers: {"Content-Type": "multipart/form-data", Authorization: authHeader()} });
    //return api.put(`/comCodes/updateComCode`, cmpReq, { headers: authHeader() });
  }
}

export default new CompService();
