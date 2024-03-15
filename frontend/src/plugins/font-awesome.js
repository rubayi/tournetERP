import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
  faFolder, // import the folder icon
} from "@fortawesome/free-solid-svg-icons";

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt, faFolder); // add the folder icon to the library

export { FontAwesomeIcon };