import api from "./api";
import { CompForm } from "src/types/CompForm";
import { CompSearchForm } from "src/types/CompSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/comp/";
export class CompService {
  static getAllComp(casesearchform: CompSearchForm): Promise<CompForm[]> {
    return api.post<CompForm[]>(API_URL + "searchCompByCondition", casesearchform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getOneCompForm(compUuid: number): Promise<CompForm> {
    return api.get<CompForm>(API_URL + `getComp/${compUuid}`, {
      headers: authHeader(),
    })
      .then((response) => {
        return response.data;
      });
  }

  static saveCompForm(attachFile: any, h3caseform: CompForm): Promise<CompForm> {
    // console.log(h3caseform);
    // return api.post<CompForm>(API_URL + "updateComp", h3caseform,
    //   { headers: {...authHeader(), "Content-Type": "multipart/form-data"}  })
    //   .then((response) => response.data);
    const formData = new FormData();

    formData.append("companyReq", JSON.stringify(h3caseform));
    formData.append("file", attachFile);
    // for (let pair of formData.entries()) {
    //   console.log(pair[0] + ', ' + pair[1]);
    // }
    console.log(formData);
    return api.post("/comp/updateComp", formData,
      { headers: {...authHeader(), "Content-Type": "multipart/form-data"}  });
  }

  static deleteCompForm(compUuid: number): Promise<void> {
    return api.get(API_URL + `deleteComp/${compUuid}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGroupreceivedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>(API_URL + "apientryreceivedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGrouppostedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>(API_URL + "apientrypostedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }
}
