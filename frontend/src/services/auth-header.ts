type AxiosRequestHeaders = Record<string, string | number | boolean>;

export default function authHeader(): AxiosRequestHeaders {
  const user = JSON.parse(localStorage.getItem('user') || '');
  if (user && user.accessToken) {
    return { Authorization: 'Bearer ' + user.accessToken };
  } else {
    return {};
  }
}
