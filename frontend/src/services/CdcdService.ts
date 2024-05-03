import api from "./api";
import { CdcdForm } from "src/types/CdcdForm";
import { CdcdSearchForm } from "src/types/CdcdSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/cdCdMng/";
export class CdcdService {


  static getAll(codesearchform: CdcdSearchForm): Promise<CdcdForm[]> {
    return api
      .post<CdcdForm[]>(API_URL + "selectCreditCardMngs", codesearchform, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }


  static getCdcdForm(cdcdUuid:number): Promise<CdcdForm[]> {

    const searchReq = {
      searchCdCdUuid: cdcdUuid,
      searchMngNameKor: "",
      searchMngNameEng: "",
      searchCardNumber: 0,
    }
    return api
      .post<CdcdForm[]>(API_URL + `searchComCodesByGrp`, searchReq,{ headers: authHeader() })
      .then((response) => response.data);
  }
  static getpartGroupCdcdForm(
    uprCodeUuid: number,
    certduration: string
  ): Promise<CdcdForm[]> {
    return api
      .get<CdcdForm[]>(API_URL + `getComCodeByGrp/${uprCodeUuid}/${certduration}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneCdcdForm(codeUuid: number): Promise<CdcdForm> {
    return api
      .get<CdcdForm>(API_URL + `getComcode/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveCdcdForm(codeform: CdcdForm): Promise<CdcdForm> {
    return api
      .post<CdcdForm>(API_URL + "updateComCode", codeform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteCdcdForm(codeUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteComcode/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
