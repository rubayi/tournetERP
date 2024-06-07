import { AxiosRequestConfig } from 'axios';
import api from './api';
import qs from 'qs';
import { IReport } from 'src/types/CompReport';
import authHeader from 'src/services/auth-header';

export enum ReportURL {
  EXPORTEXCELREPORT_LIST = '/report/exportexcel',
  EXPORTPDFREPORT_LIST = '/report/exportpdf',
  EXPORTPDFREPORT_ONE = '/report/exportpdfone',
}

export class ReportService {
  static getCompListReport(data: IReport): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTEXCELREPORT_LIST, data, {
        responseType: 'arraybuffer',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/pdf',
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }
  static getCompListPdfReport(data: IReport): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTPDFREPORT_LIST, data, {
        responseType: 'arraybuffer',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/pdf',
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }
  static getCompOnePdfReport(data: IReport): Promise<ArrayBuffer> {
    return api
      .post<ArrayBuffer>(ReportURL.EXPORTPDFREPORT_ONE, data, {
        responseType: 'arraybuffer',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/pdf',
          ...authHeader(),
        },
      })
      .then((response) => response.data);
  }

  static getReportPDFWithParameterString(
    reportUrl: ReportURL,
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    params: Record<string, any>
  ): Promise<ArrayBuffer> {
    return (
      api
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        .get<any>(
          `${reportUrl}?${this.generateParameterString(params)}`,
          this.pdfResponseConfig
        )
        .then((response) => response.data)
    );
  }

  static getReportDTOWithParameterString(
    reportUrl: ReportURL,
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    params: Record<string, any>
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  ): Promise<Array<Record<string, any>>> {
    return (
      api
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        .get<any>(`${reportUrl}?${this.generateParameterString(params)}`)
        .then((response) => response.data)
    );
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static generateParameterString(params: Record<string, any>): string {
    const paramEntries = Object.entries(params).filter(
      ([, value]) => value !== null && value !== undefined
    );
    return qs.stringify(Object.fromEntries(paramEntries), {
      arrayFormat: 'repeat',
    });
  }
  static pdfResponseConfig: AxiosRequestConfig = {
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/pdf',
    },
  };
}
