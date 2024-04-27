import axios from "axios";
axios.defaults.baseURL = process.env.VUE_APP_AXIOS_BASE_URL;
axios.defaults.withCredentials = true;
axios.defaults.timeout = 120000; // 2 minutes
