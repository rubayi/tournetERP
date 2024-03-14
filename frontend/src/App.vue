<template>
  <q-layout view="hHh lpR fFf">
    <q-header v-if="$store.state.auth.user" class="topNav" elevated>
      <q-toolbar>
        <router-link to="/">
          <img src="images/mainlogorev.png" class="toplogo" />
        </router-link>
        <q-toolbar-title> Tourtnet Hawaii ERP </q-toolbar-title>
        <q-btn
          aria-label="Home"
          dense
          flat
          icon="house"
          round
          @click="$router.push('/')"
        />
        <q-btn
          aria-label="Menu"
          dense
          flat
          icon="menu"
          round
          @click="leftDrawerOpen = !leftDrawerOpen"
        />
        <q-btn v-if="$store.state.auth.user" @click="logout" flat round
          ><q-icon name="logout" class="q-mr-sm text-white" size="sm" />
        </q-btn>
      </q-toolbar>
    </q-header>
    <q-drawer
      class="navigation-drawer"
      v-if="$store.state.auth.user"
      v-model="leftDrawerOpen"
      show-if-above
    >
      <div v-if="$store.state.auth.user">
        <div class="navigation-menu-container">
          <q-tree :nodes="linksData" node-key="label" />
        </div>
        <div>
          <!-- <user-info /> -->
        </div>
      </div>
    </q-drawer>

    <q-page-container style="background-color: #f0eeee">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { useQuasar } from "quasar";
import { useRouter } from "vue-router";
import EventBus from "./common/EventBus";

let router;
const buildMenuTree = (menuItems, parentUuid) => {
  const filteredMenus = menuItems.filter(
    (menu) => menu.upperMenuUuid === parentUuid
  );
  return filteredMenus.map((menu) => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon : "label",
    label: menu.menuKor + (menu.menuEng ? ` (${menu.menuEng})` : ""),
    link: menu.menuUrl,
    handler: (node) => {
      handleMenuClick(node.link);
    },
    menuOrd: menu.menuOrd,
    caption: menu.menuDesc,
    children: buildMenuTree(menuItems, menu.menuUuid),
  }));
};

const handleMenuClick = (link) => {
  if (link) {
    router.push(link);
  }
};

export default {
  name: "MainLayout",
  setup() {
    const $q = useQuasar();
    router = useRouter();
    $q.dark.set(true);
  },
  data() {
    return {
      loading: false,
      leftDrawerOpen: false,
      linksData: [],
    };
  },
  computed: {
    isAdmin() {
      const user = this.$store.state.auth.user;
      if (user) {
        if (user.roles) {
          return user.roles.includes("ROLE_ADMIN");
        }
      }
      return false;
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },

    getMenu() {
      let menuReq = {
        role: "ROLE_USER",
      };

      if (this.isAdmin) {
        menuReq = {
          role: "ROLE_ADMIN",
        };
      }

      this.$store.dispatch("comMenu/getMainComMenuList", menuReq).then(
        (comMenu) => {
          this.linksData = buildMenuTree(comMenu, 0);
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
  mounted() {
    // Call the method to fetch work options if user is admin
    this.getMenu();
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  beforeUnmount() {
    EventBus.remove("logout");
  },
  watch: {
    isAdmin: {
      handler(newVal) {
        // When isAdmin changes, update the menu
        this.getMenu();
      },
      immediate: true, // Call the handler immediately upon creation
    },
  },
};
</script>
<style lang="scss">
.navigation-drawer {
  overflow: hidden;
  .navigation-menu-container {
    overflow: "hidden";
    height: calc(100% - 70px);
  }
}
.topNav {
  background-image: url("images/top_main.png");
  font-family: Raleway;
  font-weight: 500;
  color: #f0eeee;
  padding: 0.5rem;
}
.toplogo {
  height: 2.4rem;
  margin-right: 0.8rem;
}
.part_title {
  font-size: 22px;
  color: #000;
  font-weight: normal;
  font-family: Raleway;
  font-weight: bold;
}

.fieldset {
  border: 1px solid #ddd;
  background-color: #faf9f9;
  border-top: none;
  padding: 0.5em;
  margin: 0.5em 2px;
  margin-bottom: 1rem;
}

.fieldset > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset > h1 > span {
  float: left;
}

.fieldset > h1:before {
  border-top: 1px solid #ddd;
  content: " ";
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.75em;
}

.fieldset > h1:after {
  border-top: 1px solid #ddd;
  content: " ";
  display: block;
  height: 1.5em;
  left: 2px;
  margin: 0 1px 0 0;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}
.fieldset02 {
  border: 1px solid #ddd;
  border-top: none;
  padding: 0.5em;
  margin: 1em 2px;
}

.fieldset02 > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset02 > h1 > span {
  float: left;
  height: 1em;
  margin: -2.9em 0.5em 0;
}

.fieldset02 > h1:before {
  border-top: 1px solid #ddd;
  content: " ";
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.9em;
}

.fieldset02 > h1:after {
  border-top: 1px solid #ddd;
  content: "";
  display: block;
  height: 1.2em;
  left: 2px;
  margin: 0 1px 0 11.2em;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}

.fieldset03 {
  border: none;
  padding: 0.5em;
  margin: 0.5em 2px;
}

.fieldset03 > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset03 > h1 > span {
  float: left;
  height: 1em;
  margin: -2.9em 0.5em 0;
}

.fieldset03 > h1:before {
  border-top: 1px solid #ddd;
  content: " ";
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.9em;
}

.fieldset03 > h1:after {
  border-top: 1px solid #ddd;
  content: "";
  display: block;
  height: 1.2em;
  left: 2px;
  margin: 0 1px 0 9em;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}
</style>
