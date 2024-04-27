import api from "./api";
import { ReportForm } from "src/types/ReportForm";
import { ReportSearchForm } from "src/types/ReportSearchForm";
import authHeader from "src/services/auth-header";
const API_URL = "/report/";

export class ManageReportService {
  static getAll(reportsearchform: ReportSearchForm): Promise<ReportForm[]> {
    return api
      .post<ReportForm[]>(API_URL + "list", reportsearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
  static getGroupReportForm(code1: number): Promise<ReportForm[]> {
    return api
      .get<ReportForm[]>(API_URL + `apilist/${code1}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }
  static getOneReportForm(reportId: number): Promise<ReportForm> {
    return api
      .get<ReportForm>(API_URL + `active/${reportId}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static saveReportForm(reportform: ReportForm): Promise<ReportForm> {
    return api
      .post<ReportForm>(API_URL + "write", reportform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static deleteReportForm(reportId: number): Promise<void> {
    return api
      .delete(API_URL + `delete/${reportId}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
