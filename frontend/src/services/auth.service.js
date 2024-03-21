import api from "./api";
import TokenService from "./token.service";
import authHeader from "src/services/auth-header";

class AuthService {
  login({ username, password }) {
    return api
      .post("/auth/signin", {
        username,
        password
      })
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }

  logout() {
    TokenService.removeUser();
  }

  register({ username, empEmail, password,
    empKor, empEng, empWorkType,
    empDiv, empTitle, empPhone,
    empWorkPhone, empEmailBook,
    empAddress1, empAddress2, empCity,
    empState, empCountry, empZip,
    empDob, empDobType,
    empMemo }) {
    return api.post("/auth/signup", {
      username, empEmail, password,
      empKor, empEng, empWorkType,
      empDiv, empTitle, empPhone,
      empWorkPhone, empEmailBook,
      empAddress1, empAddress2, empCity,
      empState, empCountry, empZip,
      empDob, empDobType,
      empMemo
    }, { headers: authHeader() });
  }

  getAllRoles() {
    return api.get("/auth/getAllroles");
  }
}

export default new AuthService();
