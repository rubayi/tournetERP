import api from "./api";
import { Login } from 'src/types/Login';
import { UserDetails } from 'src/types/UserDetails';
import authHeader from 'src/services/auth-header';
import TokenService from "src/services/TokenService";
import router from "src/router";
const API_URL = '/auth/';

export class LoginService {
  static login(loginVO: Login): Promise<UserDetails> {
    return api
      .post<UserDetails>(API_URL + 'signin', loginVO)
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }
        return response.data;
      });
  }

  static getCurrentUser(): Promise<UserDetails | null> {
    if(TokenService.getUser()){
      const userInfo: UserDetails | null = JSON.parse(TokenService.getUser());
      if (userInfo) {
        return api
          .get<UserDetails>(API_URL + "getCurrentUser", {
            headers: authHeader(),
          })
          .then((response) => response.data);
      } else {
        return api
          .get<UserDetails>(API_URL + 'getCurrentUser')
          .then((response) => response.data);
      }
    } else {

      router.push({ path: "/login" });
      return Promise.resolve(null);
    }
  }

  static logout(): Promise<void> {
    return api
      .post<string>(API_URL + 'logout', { headers: authHeader() })
      .then(() => {
        localStorage.removeItem('user');
      });
  }
}
