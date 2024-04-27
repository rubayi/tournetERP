import api from "./api";
import authHeader from "src/services/auth-header";
import { IAppCodeType } from "src/types/AppCodeType";

export class ComCodeService  {
  static getTournetLangType(): Promise<IAppCodeType[]> {
    return api
      .get<IAppCodeType[]>("/langtype", {  // Assuming API_URL is not defined globally
        headers: authHeader(),
      })
      .then((response) => response.data);
  }

}
