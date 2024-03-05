import api from "./api";
import TokenService from "./token.service";

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
    });
  }

  getAllRoles() {
    return api.get("/auth/getAllroles");
  }
}

export default new AuthService();
