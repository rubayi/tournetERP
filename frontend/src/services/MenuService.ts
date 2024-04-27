import api from "./api";
import { MenuForm } from "src/types/MenuForm";
import { MenuSearchForm } from "src/types/MenuSearchForm";
import authHeader from "src/services/auth-header";

const API_URL = "/comMenu/";
export class MenuService {


  static getAll(menuSearchForm: MenuSearchForm): Promise<MenuForm[]> {
    return api
      .post<MenuForm[]>(API_URL + "useComMenuByRole", menuSearchForm, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }


  static getGroupMenuForm(uprCodeUuid: number): Promise<MenuForm[]> {
    return api
      .get<MenuForm[]>(API_URL + `SearchComCodesByGrp/${uprCodeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static getOneMenuFormDetail(uprCodeUuid: number, code2: number): Promise<MenuForm> {
    return api
      .get<MenuForm>(API_URL + `active/${uprCodeUuid}/${code2}`, {
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

  static getOneMenuForm(codeUuid: number): Promise<MenuForm> {
    return api
      .get<MenuForm>(API_URL + `active/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }

  static saveMenuForm(codeform: MenuForm): Promise<MenuForm> {
    return api
      .post<MenuForm>(API_URL + "write", codeform, { headers: authHeader() })
      .then((response) => response.data);
  }

  static deleteMenuForm(codeUuid: number): Promise<void> {
    return api
      .delete(API_URL + `deleteComcode/${codeUuid}`, { headers: authHeader() })
      .then((response) => response.data);
  }
}
