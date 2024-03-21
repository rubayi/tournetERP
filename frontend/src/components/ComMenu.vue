<template>
  <component-to-re-render :key="componentKey" />
  <div id="officeform">
    <q-page class="q-pa-md">
      <div class="row">
        <div class="col q-pr-md flex items-center">
          <span class="part_title text-primary">
            <q-icon name="menu" class="q-ml-xs q-mb-xs" size="md"></q-icon>
            메뉴 권한 관리</span
          >
        </div>
        <div class="col-5 text-right">
          <div class="q-pa-sm">
            <q-btn label="+ 메뉴수정" color="green" @click="saveUserMenu" />
          </div>
        </div>
      </div>

      <div class="grid-container">
        <div>
          <div class="toc">
            <ul class="list no-bullets">
              <li v-for="role in roles" :key="role.roleUuid">
                <button
                  class="plain-button"
                  @click="getMenu(role)"
                  :class="{ chosen: selected.roleUuid === role.roleUuid }"
                >
                  {{ role.roleUuid }} - {{ role.roles }}
                </button>
              </li>
            </ul>
          </div>
        </div>

        <div class="toc">
          <navigation-menu :menu-options="userLinksData" />
        </div>

        <q-scroll-area class="toc" style="height: 800px">
          <q-tree
            :nodes="linksData"
            node-key="menuUuid"
            default-expand-all
            tick-strategy="strict"
            v-model:ticked="ticked"
            @update:ticked="handleMenuClick"
            class="custom-tree"
          />
        </q-scroll-area>
      </div>
    </q-page>
  </div>
</template>

<script>
import NavigationMenu from "./navigation/NavigationMenu.vue";

const buildComMenuTree = (menuItems, parentUuid) => {
  const filteredComMenus = menuItems.filter(
    (menu) => menu.upperMenuUuid === parentUuid
  );
  return filteredComMenus.map((menu) => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon : "label",
    label: menu.menuKor + (menu.menuUrl ? ` :${menu.menuUrl}` : ""),
    link: menu.menuUrl,
    menuOrd: menu.menuOrd,
    menuUrl: menu.menuUrl,
    caption: menu.menuDesc,
    children: buildComMenuTree(menuItems, menu.menuUuid),
  }));
};
export default {
  name: "ComMenu",
  components: {
    NavigationMenu,
  },
  data() {
    return {
      ticked: [],
      roles: [],
      allOfLinks: [],
      previousLinksData: [],
      linksData: [],
      userLinksData: [],
      selected: {
        role: "ROLE_USER",
        roleUuid: 1,
      },
      edited: {
        menuUuid: 0,
        menuEng: "",
        menuKor: "",
        menuUrl: "",
        menuLvl: "",
        menuOrd: "",
        menuDesc: "",
        menuIcon: "",
        useYn: "",
        upperMenuUuid: null,
      },
      chosenComMenus: [],
    };
  },
  methods: {
    handleMenuClick(newTicked) {
      if (newTicked) {
        // this.previousLinksData = this.previousLinksData.filter((data) =>
        //   newTicked.includes(data.menuUuid)
        // );
        const chosenMenus = this.allOfLinks.filter((menu) =>
          newTicked.includes(menu.menuUuid)
        );
        this.userLinksData = buildComMenuTree(chosenMenus, 0);
      }
    },
    getAllRoles() {
      this.$store.dispatch("auth/getAllRoles").then(
        (roles) => {
          this.roles = roles.data;
        },
        (error) => {
          console.log("getAllRoles failed", error);
        }
      );
    },
    getMainComMenu() {
      this.$store.dispatch("comMenu/getComMenuList").then(
        (comMenus) => {
          this.allOfLinks = comMenus;
          this.linksData = buildComMenuTree(comMenus, 0);
        },
        (error) => {
          console.log("getMainComMenu failed", error);
        }
      );
    },
    getMenu(chosenRole) {
      let menuReq = {
        role: "ROLE_USER",
      };
      if (chosenRole) {
        menuReq = {
          role: chosenRole.roles,
        };
        this.selected = chosenRole;
      }
      this.$store.dispatch("comMenu/getComMenuListForEdit", menuReq).then(
        (comMenu) => {
          this.ticked = comMenu.map((menu) => menu.menuUuid);
          this.previousLinksData = comMenu;
          this.userLinksData = buildComMenuTree(comMenu, 0);
          this.getMainComMenu();
        },
        (error) => {
          console.log("getMenu failed", error);
        }
      );
    },
    getDataPath(data) {
      console.log("getDataPath", data);
      const menu = this.userLinksData.find(
        (menu) => menu.menuUuid === data.menuUuid
      );
      return menu ? menu.orgHierarchy : null;
    },
    saveUserMenu() {
      this.ticked.forEach((tickedItem) => {
        const param = {
          roleUuid: this.selected.roleUuid,
          menuUuid: tickedItem,
        };
        this.$store.dispatch("userMenu/saveUserMenu", param).then(
          () => {
            console.log("saveUserMenu success");
            this.getMenu();
          },
          (error) => {
            console.log("saveUserMenu failed", error);
          }
        );
      });
      this.previousLinksData
        .filter((data) => !this.ticked.includes(data.menuUuid))
        .forEach((tickedItem) => {
          const param = {
            roleUuid: this.selected.roleUuid,
            menuUuid: tickedItem.menuUuid,
          };
          this.$store.dispatch("userMenu/deleteUserMenu", param).then(
            () => {
              console.log("deleteUserMenu success");
              this.getMenu();
            },
            (error) => {
              console.log("deleteUserMenu failed", error);
            }
          );
        });
    },
    saveComMenu() {
      if (this.edited.menuUuid) {
        this.$store.dispatch("comMenu/updateComMenu", this.edited).then(
          () => {
            this.getMainComMenu();
            this.resetForm();
          },
          (error) => {
            console.log("saveComMenu failed", error);
          }
        );
      } else {
        this.$store.dispatch("comMenu/createComMenu", this.edited).then(
          () => {
            this.getMainComMenu();
            this.resetForm();
          },
          (error) => {
            console.log("saveComMenu failed", error);
          }
        );
      }
    },
    chooseMainComMenu(comMenu) {
      this.$store.dispatch("comMenu/selectComMenus", comMenu).then(
        (comcodes) => {
          this.chosenComMenus = comcodes;
          this.edited = {
            menuUuid: 0,
            menuEng: "",
            menuKor: "",
            menuUrl: "",
            menuOrd: "",
            menuDesc: "",
            menuLvl: "",
            menuIcon: "",
            useYn: "",
            upperMenuUuid: comMenu.menuUuid,
          };
        },
        (error) => {
          console.log("chooseMainComMenu failed", error);
        }
      );
    },
    deleteComMenu(id) {
      this.$store.dispatch("comMenu/deleteComMenu", id).then(
        () => {
          this.getMainComMenu();
        },
        (error) => {
          console.log("deleteComMenu failed", error);
        }
      );
      this.resetForm();
    },
    resetForm() {
      this.edited = {
        menuUuid: 0,
        menuEng: "",
        menuKor: "",
        menuUrl: "",
        menuOrd: "",
        menuDesc: "",
        menuLvl: "",
        useYn: "",
        upperMenuUuid: this.chosenComMenus[0]?.upperMenuUuid ?? 0,
      };
    },
  },
  mounted() {},
  watch: {
    isAdmin: {
      handler(newVal) {
        this.getMenu();
      },
      immediate: true,
    },
  },
  created() {
    this.getAllRoles();
    this.getMenu();
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 30% 30% auto;
  gap: 20px;
}
.no-bullets {
  list-style-type: none;
  border: 1px solid rgba(255, 255, 255, 0.16);
}
.toc {
  border: 1px solid rgba(171, 171, 171);
  padding: 10px;
  background-color: white;
  border-radius: 3px;
}
.list {
  margin: 0;
  padding: 0;
}
.plain-button {
  color: rgb(0, 0, 0);
  background: white;
  border: none;
  padding: 0;
  text-align: left;
  width: 100%;
  display: block;
  padding: 13px 13px;
  cursor: pointer;
}
.chosen {
  background: #d3d3d3;
}
/* .custom-tree ::v-deep .q-tree__node {
  padding: 8px;
}
.custom-tree ::v-deep .q-tree__node--child {
  padding-left: 22px;
  padding-top: 0px;
} */
</style>
