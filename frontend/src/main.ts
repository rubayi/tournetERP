import { createApp } from "vue";
import router from "./router";
import App from "./App.vue";
import store from "./store";
import {Quasar} from "quasar";
import quasarUserOptions from "./quasar-user-options";
import "./constants";

import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
createApp(App)
    .use(Quasar, quasarUserOptions)
    .use(store)
    .use(router)
    .mount("#app");
