import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comCode } from "./comCode.module";
import { comMenu } from "./comMenu.module";
import { empTn } from "./emp.module";
import { userMenu } from "./userMenu.module";
import { compTn } from "./comp.module";
import { empEmergencyTn } from "./empemergency.module";
import setupInterceptors from '../services/setupInterceptors';

const store = createStore({
  modules: {
    auth,
    comCode,
    comMenu,
    empTn,
    userMenu,
    compTn,
    empEmergencyTn,
  },
});

setupInterceptors(store);
export default store;
