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

  static getOneCompLogForm(worknum: number): Promise<CompForm> {
    return api.get<CompForm>(API_URL + `active/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static saveCompLogForm(h3caseform: CompForm): Promise<CompForm> {
    return api.post<CompForm>(API_URL + "update", h3caseform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static deleteCompForm(worknum: number): Promise<void> {
    return api.delete(API_URL + `delete/${worknum}`, {
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
