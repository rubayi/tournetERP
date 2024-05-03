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

  static getOneCdcdForm(cdcdUuid: number): Promise<CdcdForm> {
    return api
      .get<CdcdForm>(API_URL + `getCreditCardMngBy/${cdcdUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveCdcdForm(cdcdform: CdcdForm): Promise<CdcdForm> {
    return api
      .post<CdcdForm>(API_URL + "updateCreditCardMng", cdcdform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteCdcdForm(cdcdUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteCreditCardMng/${cdcdUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
