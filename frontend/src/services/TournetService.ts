import api from "./api";
import { TournetForm } from "src/types/TournetForm";
import { TournetSearchForm } from "src/types/TournetSearchForm";
import authHeader from "src/services/auth-header";

export class TournetService {
  static getAllTournet(casesearchform: TournetSearchForm): Promise<TournetForm[]> {
    return api.post<TournetForm[]>("/hawaiifoody/list", casesearchform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getOneTournetLogForm(worknum: number): Promise<TournetForm> {
    return api.get<TournetForm>(`/hawaiifoody/active/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static saveTournetLogForm(h3caseform: TournetForm): Promise<TournetForm> {
    return api.post<TournetForm>("/hawaiifoody/update", h3caseform, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static deleteTournetForm(worknum: number): Promise<void> {
    return api.delete(`/hawaiifoody/delete/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGroupreceivedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>("/hawaiifoody/apientryreceivedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static getGrouppostedbyEntryForm(): Promise<string[]> {
    return api.get<string[]>("/hawaiifoody/apientrypostedbylist", {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }
}
