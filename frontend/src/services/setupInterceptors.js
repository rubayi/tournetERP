import axiosInstance from "./api";
import TokenService from "./token.service";
import authHeader from "src/services/auth-header";

const setup = (store) => {
  axiosInstance.interceptors.request.use(
    (config) => {
      const token = TokenService.getLocalAccessToken();
      if (token) {
        //config.headers["Authorization"] = 'Bearer ' + token;  // for Spring Boot back-end
        config.headers["x-access-token"] = token; // for Node.js Express back-end
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInstance.interceptors.response.use(

    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;

      if (originalConfig.url !== "/auth/signin" && err.response) {
        // Access Token was expired
        alert(err.response.data.message);
        if ((err.response.status === 401 && !originalConfig._retry)) {
          originalConfig._retry = true;

          store.dispatch("auth/logout");
          store.push("/login");

          // try {
          //   const rs = await axiosInstance.post("/auth/refreshtoken", {
          //     refreshToken: TokenService.getLocalRefreshToken(),
          //   });
          //
          //   const { accessToken } = rs.data;
          //
          //   store.dispatch('auth/refreshToken', accessToken);
          //   TokenService.updateLocalAccessToken(accessToken);
          //
          //   return axiosInstance(originalConfig);
          // } catch (_error) {
          //   return Promise.reject(_error);
          // }
        }
      }

      return Promise.reject(err);
    }
  );
};

export default setup;
