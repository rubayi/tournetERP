<template>
  <div :style="{ marginLeft: '15px', marginRight: '15px' }">
    <h5 :style="{ marginTop: '15px', marginBottom: '15px' }">메뉴 권한 관리</h5>
    <div class="grid-container">

      <div>
        <div class="row q-col-gutter-x-md" style="max-width: 900px">
          <q-scroll-area style="height: 700px; width:550px" >
          <q-tree
            :nodes="linksData"
            node-key="label"
            default-expand-all
         />
          </q-scroll-area>
        </div>
      </div>

      <div>
        <div class="toc inputBox">
          <div>메뉴등록</div>
          <form @submit.prevent="saveComMenu">
            <input
              type="number"
              id="menuUuid"
              v-model="edited.menuUuid"
              hidden
            />
            <div class="spaces">
              <input
                type="text"
                placeholder="영문 상세메뉴명"
                id="menuEng"
                v-model="edited.menuEng"
              />
            </div>
            <div class="spaces">
              <input
                type="text"
                placeholder="한글 상세메뉴명"
                id="menuKor"
                v-model="edited.menuKor"
                required
              />
            </div>
            <div class="spaces">
              <input
                type="text"
                placeholder="메뉴 URL "
                id="menuUrl"
                v-model="edited.menuUrl"
                required
              />
            </div>
            <div class="spaces">
              <input
                type="text"
                placeholder="메뉴레벨 ex) 0,1,2.."
                id="menuLvl"
                v-model="edited.menuLvl"
                required
              />
            </div>
            <div class="spaces">
              <input
                type="number"
                placeholder="메뉴정렬순서"
                id="menuOrd"
                v-model="edited.menuOrd"
                required
              />
            </div>
            <div class="spaces">
              <input
                type="text"
                placeholder="메뉴사용여부"
                id="menuOrd"
                v-model="edited.menuOrd"
                required
              />
            </div>
            <div class="spaces">
              <button type="submit">
                {{ edited.menuUuid ? "수정" : "저장" }}
              </button>
              <button type="reset" @click="resetForm">취소</button>
              <button type="button" @click="deleteComMenu(edited.menuUuid)">
                삭제
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const buildMenuTree = (menuItems, parentUuid) => {
  const filteredMenus = menuItems.filter(menu => menu.upperMenuUuid === parentUuid);
  return filteredMenus.map(menu => ({
    menuUuid: menu.menuUuid,
    upperMenuUuid: menu.upperMenuUuid,
    menuLvl: menu.menuLvl,
    icon: menu.menuIcon ? menu.menuIcon :"label",
    label: menu.menuKor + (menu.menuEng ? ` (${menu.menuEng})` : '')+ (menu.menuUrl ? ` :${menu.menuUrl}` : ''),
    link: menu.menuUrl,
    handler: (node)=>{handleMenuClick(node.link)},
    menuOrd: menu.menuOrd,
    menuUrl: menu.menuUrl,
    caption: menu.menuDesc,
    children: buildMenuTree(menuItems, menu.menuUuid)
  }));
};
export default {
  name: "ComMenu",

  data() {
    return {
      linksData: [],
      colDefs: [
        {
          field: "menuUuid",
          headerName: "메뉴메뉴",
          sortable: true,
          filter: true,
          // checkboxSelection: true,
        },
        {
          field: "menuEng",
          headerName: "메뉴명(영문)",
          sortable: true,
          filter: true,
        },
        {
          field: "menuKor",
          headerName: "메뉴명(한글)",
          sortable: true,
          filter: true,
        },
        {
          field: "menuUrl",
          headerName: "메뉴경로",
          sortable: true,
          filter: true,
        },
        {
          field: "menuIcon",
          headerName: "메뉴아이콘",
          sortable: true,
          filter: true,
        },
        {
          field: "menuOrd",
          headerName: "메뉴순서",
          sortable: true,
          filter: true,
        },
        {
          field: "menuOrd",
          headerName: "메뉴사용여부",
          sortable: true,
          filter: true,
        },
        {
          field: "edit",
          headerName: "관리",
          cellRenderer: function (params) {
            return `<q-btn style="background: #e5e5e5; padding: 5px; cursor: pointer; font-size: 12px;">${params.value}</q-btn>`;
          },
          valueGetter: function (params) {
            return "수정/삭제";
          },
          cellClass: "grid-btn",
        },
      ],
      comreq: null,
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
      comMenus: [],
      chosenComMenus: [],
    };
  },
  methods: {
    getMainComMenu() {

      this.$store.dispatch("comMenu/getComMenuList")
        .then(
        (comMenus) => {
          console.log(comMenus);
          this.linksData = buildMenuTree(comMenus, 0);
        },
        (error) => {
          console.log("getMainComMenu failed", error);
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
    onCellClicked(params) {
      if (params.column.getColId() === "edit") {
        this.edited = Object.assign({}, params.data);
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
  created() {
    this.getMainComMenu();
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 40% 20%;
  gap: 20px;
}
.no-bullets {
  list-style-type: none;
  border: 1px solid #b9b8b8;
}
.toc {
  border: 1px solid #b9b8b8;
  padding: 10px;
  background-color: #fff;
  border-radius: 3px;
}
.inputBox {
  color: black;
}
.list {
  margin: 0;
  padding: 0;
}
.grid-btn {
  background-color: #d1d1d1;
  color: rgb(31, 31, 31);
  padding: 15px 15px;
  margin: 5px 5px;
  text-align: center;
  font-size: 11px;
  border: 1px solid #000000;
  cursor: pointer;
}
.list > li {
  border-bottom: #333333 1px solid;
}

.list > li:last-child {
  border-bottom: none;
}
.spaces {
  margin-top: 10px;
  margin-top: 10px;
  align-content: center;
}
.plain-button {
  background: #efefef;
  border: none;
  padding: 0;
  text-align: left;
  width: 100%;
  display: block;
  padding: 10px 10px;
}
</style>
