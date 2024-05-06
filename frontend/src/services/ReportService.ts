import { AxiosRequestConfig } from "axios";
import api from "./api";
import qs from "qs";
import { IReportVO } from "src/types/TournetReportVO";
import authHeader from "src/services/auth-header";
export enum ReportURL {
  // TODO: Replace the "/somethingHere" urls when the report is implemented
  EXPORTEXCELREPORT_LIST = "/report/exportexcel",
  EXPORTPDFREPORT_LIST = "/report/exportpdf",
  EXPORTPDFREPORT_ONE = "/report/exportpdfone",
}
//const user = JSON.parse(localStorage.getItem("user") || "");
export class ReportService {
  static getTournetListReport(data: IReportVO): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTEXCELREPORT_LIST, data, {
        responseType: "arraybuffer",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/pdf",
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }
  static getTournetListPdfReport(data: IReportVO): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTPDFREPORT_LIST, data, {
        responseType: "arraybuffer",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/pdf",
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }
  static getTournetOnePdfReport(data: IReportVO): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTPDFREPORT_ONE, data, {
        responseType: "arraybuffer",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/pdf",
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }

  static getReportPDFWithParameterString(
    reportUrl: ReportURL,
    params: Record<string, any>
  ): Promise<ArrayBuffer> {
    return api
      .get<any>(
        `${reportUrl}?${this.generateParameterString(params)}`,
        this.pdfResponseConfig
      )
      .then((response) => response.data);
  }

  static getReportDTOWithParameterString(
    reportUrl: ReportURL,
    params: Record<string, any>
  ): Promise<Array<Record<string, any>>> {
    return api
      .get<any>(`${reportUrl}?${this.generateParameterString(params)}`)
      .then((response) => response.data);
  }

  static generateParameterString(params: Record<string, any>): string {
    const paramEntries = Object.entries(params).filter(
      ([, value]) => value !== null && value !== undefined
    );
    return qs.stringify(Object.fromEntries(paramEntries), {
      arrayFormat: "repeat",
    });
  }
  static pdfResponseConfig: AxiosRequestConfig = {
    responseType: "arraybuffer",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/pdf",
    },
  };
}
