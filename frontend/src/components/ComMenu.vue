<template>
  <div :style="{ marginLeft: '15px', marginRight: '15px' }">
    <h5 :style="{ marginTop: '15px', marginBottom: '15px' }">메뉴 권한 관리</h5>
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

      <q-scroll-area class="toc" style="height: 700px">
        <q-tree :nodes="userLinksData" node-key="label" default-expand-all>
          <template v-slot:default="props">
            <q-item-section>
              <q-item-label>
                {{ props.node.label }}
              </q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-btn
                flat
                v-if="props.node.menuDelete"
                @click.stop="deleteNode(props.node)"
                >Delete</q-btn
              >
              <q-btn
                flat
                v-if="props.node.menuRead"
                @click.stop="readNode(props.node)"
                >Read</q-btn
              >
              <q-btn
                flat
                v-if="props.node.menuWrite"
                @click.stop="writeNode(props.node)"
                >Write</q-btn
              >
            </q-item-section>
          </template>
        </q-tree>
      </q-scroll-area>

      <q-scroll-area class="toc" style="height: 700px">
        <q-tree
          :nodes="linksData"
          node-key="menuUuid"
          default-expand-all
          tick-strategy="strict"
          v-model:ticked="ticked"
          @update:ticked="handleMenuClick"
        />
        <div style="position: absolute; bottom: 0">
          <button class="save" @click="saveUserMenu">저장</button>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
const buildMenuTree = (menuItems, parentUuid) => {
  const filteredMenus = menuItems.filter(
    (menu) => menu.upperMenuUuid === parentUuid
  );
  return filteredMenus.map((menu) => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon : "label",
    label:
      menu.menuKor +
      (menu.menuEng ? ` (${menu.menuEng})` : "") +
      (menu.menuUrl ? ` :${menu.menuUrl}` : ""),
    link: menu.menuUrl,
    menuOrd: menu.menuOrd,
    menuUrl: menu.menuUrl,
    caption: menu.menuDesc,
    menuDelete: menu.menuDelete,
    menuRead: menu.menuRead,
    menuWrite: menu.menuWrite,
    children: buildMenuTree(menuItems, menu.menuUuid),
  }));
};
const buildComMenuTree = (menuItems, parentUuid) => {
  const filteredComMenus = menuItems.filter(
    (menu) => menu.upperMenuUuid === parentUuid
  );
  return filteredComMenus.map((menu) => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon : "label",
    label:
      menu.menuKor +
      (menu.menuEng ? ` (${menu.menuEng})` : "") +
      (menu.menuUrl ? ` :${menu.menuUrl}` : ""),
    link: menu.menuUrl,
    menuOrd: menu.menuOrd,
    menuUrl: menu.menuUrl,
    caption: menu.menuDesc,
    children: buildComMenuTree(menuItems, menu.menuUuid),
  }));
};
export default {
  name: "ComMenu",
  data() {
    return {
      ticked: [],
      roles: [],
      allOfLinks: [],
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
        const chosenMenus = this.allOfLinks.filter((menu) =>
          newTicked.includes(menu.menuUuid)
        );
        this.userLinksData = buildMenuTree(chosenMenus, 0);
      }
      console.log("newTicked", newTicked);
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
          console.log(comMenus);
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
          console.log(comMenu);
          this.userLinksData = buildMenuTree(comMenu, 0);
          this.ticked = comMenu.map((menu) => menu.menuUuid);
          this.getMainComMenu();
        },
        (error) => {
          console.log("getMenu failed", error);
        }
      );
    },
    saveUserMenu() {
      param = {
        roleUuid: this.selected.roleUuid,
        menuUuids: this.ticked,
      };
      this.$store.dispatch("userMenu/saveUserMenu", param).then(
        () => {
          this.getMenu();
        },
        (error) => {
          console.log("saveUserMenu failed", error);
        }
      );
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
  mounted() {
    this.getMenu();
  },
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
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 20% 40% auto;
  gap: 20px;
}
.no-bullets {
  list-style-type: none;
  border: 1px solid rgba(255, 255, 255, 0.16);
}
.toc {
  border: 1px solid rgba(255, 255, 255, 0.16);
  padding: 10px;
  background-color: color-mix(in srgb, #fff, #182230 93%);
  border-radius: 10px;
}
.list {
  margin: 0;
  padding: 0;
}
.list > li {
  border-bottom: rgba(255, 255, 255, 0.16) 1px solid;
}
.list > li:last-child {
  border-bottom: none;
}
.spaces {
  margin-top: 10px;
  margin-top: 10px;
}
.inputBox {
  width: 100%;
  border: 1px solid rgba(255, 255, 255, 0.16);
  padding: 10px;
  background-color: color-mix(in srgb, #fff, #182230 97%);
  border-radius: 3px;
}
.plain-button {
  color: white;
  background: color-mix(in srgb, #fff, #182230 97%);
  border: none;
  padding: 0;
  text-align: left;
  width: 100%;
  display: block;
  padding: 10px 10px;
  cursor: pointer;
}
.chosen {
  background: color-mix(in srgb, #fff, #182230 87%);
}
.save {
  background: #50d427ad;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
  border: none;
  color: white;
}
.clear {
  background: #ffae00ad;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
  border: none;
  color: white;
}
.delete {
  background: #ff0000ad;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
  border: none;
  color: white;
}
</style>
