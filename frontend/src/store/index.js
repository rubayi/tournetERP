import { createStore } from "vuex";
import { auth } from "./auth.module";
import { comCode } from "./comCode.module";
import { comMenu } from "./comMenu.module";
import { empTn } from "./emp.module";
import { userMenu } from "./userMenu.module";
import { compTn } from "./comp.module";
import { contactTn } from "./contact.module";
import { trContactTn } from "./tourContact.module";
import { tourTn } from "./tour.module";
import { cdcdmngTn } from "./cdcdMng.module";
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
    contactTn,
    empEmergencyTn,
    tourTn,
    trContactTn,
    cdcdmngTn
  },
});

setupInterceptors(store);
export default store;
