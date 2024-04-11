<template>

  <div id="comp-list">
    <q-page class="q-pa-md">
      <div class="row">
        <div class="col q-pr-md flex items-center">
          <span class="part_title text-primary">
            <q-icon name="person" class="q-ml-xs q-mb-xs" size="md"></q-icon
            >업체관리</span
          >
        </div>
        <div class="col-5 text-right">
          <q-form @submit="searchCompList">
            <div class="row q-col-gutter-sm justify-end">
              <q-select
                class="col-2"
                v-model="searchIdx"
                :options="options"
                label="검색방법 *"
              />
              <q-select
                v-if="this.searchIdx == '유형'"
                class="col-3"
                v-model="searchCompSector"
                :options="compSectorOptions"
                option-value="codeValue"
                option-label="codeKr"
                emit-value
                map-options
                label="업체유형 *"
              />
              <q-input
                v-if="this.searchIdx != '유형'"
                v-model="searchWord"
                type="text"
                label="검색어 *"
              />
              <div class="q-py-md">
                <q-btn label="검색" type="submit" color="primary" />
                <q-btn
                  style="margin-left: 5px"
                  label="전체검색"
                  color="secondary"
                  @click="onReset"
                />
              </div>
              <div class="q-pa-md">
                <q-btn
                  label="+ 업체등록"
                  color="green"
                  @click="createAction"
                />
              </div>
            </div>
          </q-form>
        </div>
      </div>
      <div id="comp-grid-container" class="row">
        <table-comp
          id="comp-grid"
          class="ag-theme-alpine grid"
          :column-defs="colDefs"
          :row-data="compList"
          :on-cell-clicked="openAction"
        />
      </div>
      <comp-drawer
        :open-drawer="openDrawer"
        :drawer-width="800"
        :dataVal="edited"
        :contact-list="contactList"
        :on-close-click="closeAction"
        :sub-drawer-close="reloadContactList"
        @update:openDrawer="openDrawer = $event"
        @drawer-closed="searchCompList"
      />
    </q-page>
  </div>
</template>

<script>
import { ref } from "vue";
import { compFormTableConfig } from "src/views/comp/CompFormTableConfig";
import { initialData } from "src/views/comp/CompData";
import { getCommonValue } from "src/utils/common.js"; // 공통코드 값

//Component
import TableComp from "src/components/table/TableComp.vue";
// Layout
import CompDrawer from "src/views/comp/CompDrawer.vue";
import EventBus from "src/common/EventBus";

export default {
  name: "CompList",
  components: {
    TableComp,
    CompDrawer,
  },
  setup() {
    return {
      openDrawer: ref(false),
      searchIdx: ref(null),
      bar: ref(false),
      options: ["업체명", "업체명(영문)", "유형"],
    };
  },
  data() {
    return {
      componentKey: 0,
      compSector: "",
      compKor: "",
      compEng: "",
      searchWord: "",
      searchCompSector: [],
      initEdited: initialData,
      updateEdited: {},
      edited: initialData,
      colDefs: compFormTableConfig.columns(),
      compList: [],
      contactList: [],
      curCompUuid: 0,
    };
  },
  methods: {
    /* 공통코드값 가져오기 */
    async getCommonCode(req) {
      try {
        this[req.dataName] = await getCommonValue(req);
      } catch (error) {
        console.error(error);
      }
    },

    /* New */
    createAction() {
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },

    /* Edit */
    openAction(params) {
      //this.updateEdited = Object.assign({}, params.data);
      this.edited = params.data;
      this.getContactList(params.data.compUuid);
      this.openDrawer = !this.openDrawer;
    },

    closeAction() {
      this.contactList = [];
      this.curCompUuid = 0;
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },
    handlePageChange() {
      this.searchCompList();
    },
    getContactList(compUuid) {
      this.curCompUuid = compUuid;
      const reqPram = {compUuid: compUuid}

      this.$store.dispatch(`contactTn/searchContactList`, reqPram).then(
          (response) => {
            this.contactList =  response.contactList;
          },
          (error) => {
            console.log("searchCompList failed", error);
            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
      );
    },
    reloadContactList() {
      const reqPram = {compUuid: this.curCompUuid}

      this.$store.dispatch(`contactTn/searchContactList`, reqPram).then(
          (response) => {
              this.contactList =  response.contactList;
          },
          (error) => {
              console.log("searchCompList failed", error);
              if (error.response && error.response.status === 403) {
                  EventBus.dispatch("logout");
              }
          }
      );
    },
    searchCompList() {
      if (this.searchIdx === "업체명") {
        this.compKor = this.searchWord;
        this.compSector = "";
        this.compEng = "";
      } else if (this.searchIdx === "업체명(영문)") {
        this.compEng = this.searchWord;
        this.compSector = "";
        this.compKor = "";
      } else if (this.searchIdx === "유형") {
        this.compSector = this.searchCompSector;
        this.compEng = "";
        this.compKor = "";
      } else {
        this.compSector = 0;
        this.compEng = "";
        this.compKor = "";
      }

      const searchReq = {
        compSector: this.compSector,
        compKor: this.compKor,
        compEng: this.compEng,
      };

      this.$store.dispatch(`compTn/searchCompList`, searchReq).then(
        (response) => {
          this.compList = response.compList;
          // this.page = compList.currentPage;
          // this.showPage = compList.totalPages;
        },
        (error) => {
          console.log("searchCompList failed", error);
          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },
    deleteComp(id) {
      this.$store.dispatch("compTn/deleteComp", id).then(
        (response) => {
          alert(response.data.message);
          this.resetForm();
          this.openDrawer = false;
          this.handlePageChange();
        },
        (error) => {
          console.log("deleteComp failed", error);
        }
      );
      this.resetForm();
    },
    resetForm() {
      if (this.edited.compUuid != 0) {
        this.edited = this.updateEdited;
      } else {
        this.edited = Object.assign({}, this.initEdited);
      }
    },
    onReset() {
      this.searchIdx = "";
      this.searchWord = "";
      this.compSector = "";
      this.compKor = "";
      this.compEng = "";
      this.searchCompList();
    },
  },
  created() {
    this.searchCompList();
    //업체유형
    this.getCommonCode({ upCode: 22, codeLvl: "1", dataName: "compSectorOptions" });

  },
};
</script>
<style lang="scss">
#compform {
  #compform-grid-container {
    flex-grow: 1;

    #compform-grid {
      height: 100%;
    }
  }
}
</style>
