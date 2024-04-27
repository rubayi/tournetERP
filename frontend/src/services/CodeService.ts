import api from "./api";
import { CodeForm } from "src/types/CodeForm";
import { CodeSearchForm } from "src/types/CodeSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/comCodes/";
export class CodeService {


  static getAll(codesearchform: CodeSearchForm): Promise<CodeForm[]> {
    return api
      .post<CodeForm[]>(API_URL + "comCodes", codesearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }


  static getGroupCodeForm(uperCodeUuid:number): Promise<CodeForm[]> {

    const searchReq = {
      searchUprCodeUuid: uperCodeUuid,
      searchCodeKr: "",
      searchCodeLvl: 0,
    }
    return api
      .post<CodeForm[]>(API_URL + `searchComCodesByGrp`, searchReq,{ headers: authHeader() })
      .then((response) => response.data);
  }
  static getpartGroupCodeForm(
    uprCodeUuid: number,
    certduration: string
  ): Promise<CodeForm[]> {
    return api
      .get<CodeForm[]>(API_URL + `getComCodeByGrp/${uprCodeUuid}/${certduration}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneCodeFormDetail(uprCodeUuid: number, code2: number): Promise<CodeForm> {
    return api
      .get<CodeForm>(API_URL + `active/${uprCodeUuid}/${code2}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneCodeForm(codeUuid: number): Promise<CodeForm> {
    return api
      .get<CodeForm>(API_URL + `active/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveCodeForm(codeform: CodeForm): Promise<CodeForm> {
    return api
      .post<CodeForm>(API_URL + "createComCode", codeform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteCodeForm(codeUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteComcode/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
