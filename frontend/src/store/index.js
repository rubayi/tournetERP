import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comCode } from "./comCode.module";
import { comMenu } from "./comMenu.module";
import { empTn } from "./emp.module";
import { userMenu } from "./userMenu.module";

import setupInterceptors from '../services/setupInterceptors';

const store = createStore({
  modules: {
    auth,
    comCode,
    comMenu,
    empTn,
    userMenu,
  },
});

setupInterceptors(store);
export default store;
