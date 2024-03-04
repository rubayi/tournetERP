<template>
  <q-layout view="hHh Lpr hFf">
    <q-header elevated class="bg-grey-3 text-blue-grey">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title>
          <img src="images/logo_tnh2.png" style="width: 100px; height: 53px" />
        </q-toolbar-title>
        <div>
<!--          <q-btn-->
<!--            v-if="!$store.state.auth.user"-->
<!--            flat-->
<!--            to="/register"-->
<!--            stretch-->
<!--            class="text-bold"-->
<!--            ><q-icon-->
<!--              name="fas fa-user-plus"-->
<!--              class="q-mr-sm text-white"-->
<!--              size="xs"-->
<!--            ></q-icon-->
<!--            >사용자등록(Register)</q-btn-->
<!--          >-->
          <q-btn
            v-if="!$store.state.auth.user"
            to="/login"
            flat
            stretch
            class="text-bold"
          >
            <q-icon
              name="fas fa-sign-in-alt"
              class="q-mr-sm text-white"
              size="xs"
            ></q-icon
            >로그인(Login)</q-btn
          >
          <q-btn
            v-if="$store.state.auth.user"
            to="/profile"
            flat
            stretch
            class="text-bold"
            ><q-icon
              name="fas fa-user-circle"
              class="q-mr-sm text-white"
              size="xs"
            ></q-icon>
            {{ currentUser.username }}</q-btn
          >
          <q-btn
            v-if="$store.state.auth.user"
            @click="logout"
            flat
            stretch
            class="text-bold"
          >
            <q-icon
              name="fas fa-sign-out-alt"
              class="q-mr-sm text-white"
              size="xs"
            ></q-icon
            >로그아웃(Logout)</q-btn
          >
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above content-class="bg-grey-1">
      <div v-if="$store.state.auth.user">
        <div class="q-pa-md q-gutter-sm">
          <q-tree
            :nodes="linksData"
            node-key="label"
           />
        </div>
      </div>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>

    <q-footer elevated class="bg-grey-8 text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar> </q-avatar>
          <div></div>
        </q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script>
import { useQuasar } from "quasar";
import { useRouter } from "vue-router"; // Import useRouter from vue-router
let router;
const buildMenuTree = (menuItems, parentUuid) => {
  const filteredMenus = menuItems.filter(menu => menu.upperMenuUuid === parentUuid);
  return filteredMenus.map(menu => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon :"label",
    label: menu.menuKor + (menu.menuEng ? ` (${menu.menuEng})` : ''),
    link: menu.menuUrl,
    handler: (node)=>{handleMenuClick(node.link)},
    menuOrd: menu.menuOrd,
    caption: menu.menuDesc,
    children: buildMenuTree(menuItems, menu.menuUuid)
  }));
};

const handleMenuClick = (link) =>{
  if (link) {
    router.push(link);
  }
};

export default {
  name: "MainLayout",
  setup() {
    const $q = useQuasar();
    router = useRouter();
    // calling here; equivalent to when component is created
    $q.dark.set(true);

  },
  data() {
    return {
      loading: false,
      leftDrawerOpen: false,
      linksData: []
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

      this.$store.dispatch("comMenu/getMainComMenuList", menuReq)
        .then(
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
  },
  watch: {
    isAdmin: {
      handler(newVal) {
        // When isAdmin changes, update the menu
        this.getMenu();
      },
      immediate: true // Call the handler immediately upon creation
    }
  }
};
</script>
