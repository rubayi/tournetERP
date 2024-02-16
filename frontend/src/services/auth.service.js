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
        console.log(response.data);
        if (response.data.accessToken) {
          console.log(response.data.accessToken);
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }

  logout() {
    TokenService.removeUser();
  }

  register({ username, empEmail, password }) {
    return api.post("/auth/signup", {
      username,
      empEmail,
      password
    });
  }
}

export default new AuthService();
