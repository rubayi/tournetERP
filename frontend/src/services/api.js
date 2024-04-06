import axios from "axios";

const instance = axios.create({
  baseURL: "http://www.ddukbokki.com:8099/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;
