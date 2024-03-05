import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comCode } from "./comCode.module";
import { comMenu } from "./comMenu.module";
import { empTn } from "./emp.module";

const store = createStore({
  modules: {
    auth,
    comCode,
    comMenu,
    empTn,
  },
});

export default store;
