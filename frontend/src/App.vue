<template>
  <q-layout view="hHh lpR fFf">
    <q-header v-if="$store.state.auth.user" class="topNav" elevated>
      <q-toolbar>
        <router-link to="/">
          <img src="images/mainlogorev.png" class="toplogo" />
        </router-link>
        <q-toolbar-title> Tourtnet Hawaii ERP </q-toolbar-title>
        <div id="user-info">
          <div class="text-weight-bold">
            {{
              $store.state.auth.user
                ? $store.state.auth.user.username
                : "(Not Logged In)"
            }}
          </div>
        </div>
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
      overlay
      :width="240"
      v-if="$store.state.auth.user"
      v-model="leftDrawerOpen"
      show-if-above
      bordered
    >
      <div class="navigation-menu-container">
        <!-- <q-list>
            <div v-for="item in linksData" :key="item.label">
              <q-item-label v-if="item.headerLabel" header>
                {{ item.headerLabel }}
              </q-item-label>
              <expansion-item-comp
                v-else-if="item.expandChildren && !item.hide"
                :content-inset-level="0"
                :focused="item.focused"
                :icon="item.icon"
                :label="item.label"
              >
                <item-comp
                  v-for="child in item.children"
                  :external-u-r-l="child.externalURL"
                  :focused="child?.focused"
                  :hide="child.hide"
                  :icon="child.icon"
                  :label="child.label"
                  :menu-options="child.children"
                  :open-menu-on-hover="child.children?.length > 0"
                  :to="child.to"
                  :key="child.label"
                />
              </expansion-item-comp>
              <item-comp
                v-else-if="!item.expandChildren && !item.hide"
                :focused="item.focused"
                :hide="item.hide"
                :icon="item.icon"
                :label="item.label"
                :menu-options="item.children"
                :open-menu-on-hover="item.children?.length > 0"
                :to="item.to"
                :key="item.label"
              />
            </div>
          </q-list> -->
        <q-tree
          :nodes="linksData"
          node-key="label"
          default-expand-icon="expand_more"
          default-collapsed-icon="chevron_right"
        />
      </div>
      <q-img
        class="absolute-bottom"
        src="https://cdn.quasar.dev/img/material.png"
        style="height: 100px"
      />
      <div id="user-info">
        <div class="full-width q-pa-sm absolute-bottom">
          <q-btn class="full-width logout-button" @click="logout">
            <div class="row items-center text-left full-width">
              <q-icon left name="logout"></q-icon>
              <div class="text-left">
                <div class="text-weight-bold">
                  {{
                    $store.state.auth.user
                      ? $store.state.auth.user.username
                      : "(Not Logged In)"
                  }}
                </div>
                <div class="logout-text">Logout</div>
              </div>
            </div>
          </q-btn>
        </div>
      </div>
    </q-drawer>
    <q-btn
      class="drawer-toggle-button"
      :style="{ left: drawerButtonPosition }"
      icon="menu"
      dense
      @click="leftDrawerOpen = !leftDrawerOpen"
    />

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
    label: menu.menuKor,
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
  },
  data() {
    return {
      loading: false,
      leftDrawerOpen: true,
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
    drawerButtonPosition() {
      return this.leftDrawerOpen ? "240px" : "0px";
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
@import url("https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap");

body {
  font-family: "Raleway", sans-serif;
  overflow: hidden;
}
.navigation-drawer {
  padding: 10px;
  margin: 5px;
  overflow: hidden;
  .navigation-menu-container {
    overflow: "hidden";
    color: #3f3f3f;
    background-color: white;
    height: calc(100% - 70px);
  }
}
.drawer-toggle-button {
  position: fixed;
  left: 0px;
  top: 50%;
  font-size: 10px;
  transform: translateY(-50%);
  background-color: rgb(255, 255, 255);
  border-top: 1px solid #3f3f3f;
  border-right: 1px solid #3f3f3f;
  border-bottom: 1px solid #3f3f3f;
  z-index: 2; /* Ensure the button is above the drawer */
  width: 25px; /* Set the width of the button */
  height: 30px; /* Set the height of the button */
  border-radius: 0 50% 50% 0; /* Make the right of the button circular */
  overflow: hidden; /* Hide the bottom half of the button */
}
#user-info > * {
  color: var(--dark-cornflower-blue);
  .logout-button {
    background-color: white !important;
  }
  .logout-text {
    margin-top: -5px;
    font-size: 12px;
  }
}
.topNav {
  background-image: url("images/top_main.png");
  font-family: "Raleway", sans-serif;
  font-weight: 500;
  padding: 0.5rem;
}
.toplogo {
  height: 2.4rem;
  margin-right: 0.8rem;
}
.part_title {
  font-size: 22px;
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
