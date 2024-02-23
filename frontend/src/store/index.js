import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comCode } from "./comCode.module";

const store = createStore({
  modules: {
    auth,
    comCode,
  },
});

export default store;
