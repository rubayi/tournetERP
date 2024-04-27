import axios, { AxiosInstance } from "axios";

const instance: AxiosInstance = axios.create({
  baseURL: "http://localhost:8099/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;
