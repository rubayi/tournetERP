import router from "src/router";

type AxiosRequestHeaders = Record<string, string | number | boolean>;

export default function authHeader(): AxiosRequestHeaders {
  if (localStorage.getItem('user')) {
    const user = JSON.parse(localStorage.getItem('user') || '');
    if (user && user.accessToken) {
      return {Authorization: 'Bearer ' + user.accessToken};
    } else {
      return {};
    }
  } else {
    router.push({path: "/login"});
    // Since the function needs to return AxiosRequestHeaders,
    // we can return an empty object here
    return {};
  }
}
