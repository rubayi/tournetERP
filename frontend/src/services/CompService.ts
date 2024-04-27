import api from "./api";
import { CompForm } from "src/types/CompForm";
import { CompSearchForm } from "src/types/CompSearchForm";
import authHeader from "src/services/auth-header";

export class CompService {
  static getAllComp(casesearchform: CompSearchForm): Promise<CompForm[]> {
    return api.post<CompForm[]>("/comp/searchCompByCondition", casesearchform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getOneCompLogForm(worknum: number): Promise<CompForm> {
    return api.get<CompForm>(`/comp/active/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static saveCompLogForm(h3caseform: CompForm): Promise<CompForm> {
    return api.post<CompForm>("/comp/update", h3caseform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static deleteCompForm(worknum: number): Promise<void> {
    return api.delete(`/comp/delete/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGroupreceivedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>("/comp/apientryreceivedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGrouppostedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>("/comp/apientrypostedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }
}
