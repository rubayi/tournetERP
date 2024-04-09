<template>
  <component-to-re-render :key="componentKey" />
  <div id="cdcdform">
    <q-page class="q-pa-md">
      <div class="row">
        <div class="col q-pr-md flex items-center">
          <span class="part_title text-primary">
            <q-icon name="person" class="q-ml-xs q-mb-xs" size="md"></q-icon
            >카드관리</span
          >
        </div>
        <div class="col-5 text-right">
          <q-form @submit="searchCdcdList">
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
                v-model="searchCdcdSector"
                :options="cdcdSectorOptions"
                option-value="codeValue"
                option-label="codeKr"
                emit-value
                map-options
                label="카드유형 *"
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
                  label="+ 카드등록"
                  color="green"
                  @click="createAction"
                />
              </div>
            </div>
          </q-form>
        </div>
      </div>
      <div id="cdcd-form-grid-container" class="row">
        <table-comp
          id="cdcd-form-grid"
          class="ag-theme-alpine grid"
          :column-defs="colDefs"
          :row-data="cdcds"
          :on-cell-clicked="openAction"
        />
      </div>
      <cdcd-form-drawer
        :open-drawer="openDrawer"
        :drawer-width="800"
        :dataVal="edited"
        :on-close-click="closeAction"
        @update:openDrawer="openDrawer = $event"
        @drawer-closed="searchCdcdList"
      />
    </q-page>
  </div>
</template>

<script>
import { ref } from "vue";
import { cdcdFormTableConfig } from "src/views/cdcd/CdcdFormTableConfig";
import { initialData } from "src/views/cdcd/CdcdData";
import { getCommonValue } from "src/utils/common.js"; // 공통코드 값

//Component
import TableComp from "src/components/table/TableComp.vue";
// Layout
import CdcdFormDrawer from "src/views/cdcd/CdcdFormDrawer.vue";
import EventBus from "src/common/EventBus";

export default {
  name: "CdcdTnList",
  components: {
    TableComp,
    CdcdFormDrawer,
  },
  setup() {
    return {
      openDrawer: ref(false),
      searchIdx: ref(null),
      bar: ref(false),
      options: ["카드명", "카드명(영문)", "유형"],
    };
  },
  data() {
    return {
      //S: Paging SET
      page: 1, //현재페이지
      showPage: 5, //표시할 페이지 개수
      count: 10, //보여줄 row 개수
      //E: Paging SET

      componentKey: 0,
      cdcdSector: "",
      cdcdKor: "",
      cdcdEng: "",
      searchWord: "",
      searchCdcdSector: [],
      initEdited: initialData,
      updateEdited: {},
      edited: initialData,
      colDefs: cdcdFormTableConfig.columns(),
      cdcds: [],
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
      this.updateEdited = Object.assign({}, params.data);
      this.edited = params.data;
      this.openDrawer = !this.openDrawer;
    },

    closeAction() {
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },
    handlePageChange() {
      this.searchCdcdList();
    },
    searchCdcdList() {
      if (this.searchIdx === "카드명") {
        this.cdcdKor = this.searchWord;
        this.cdcdSector = "";
        this.cdcdEng = "";
      } else if (this.searchIdx === "카드명(영문)") {
        this.cdcdEng = this.searchWord;
        this.cdcdSector = "";
        this.cdcdKor = "";
      } else if (this.searchIdx === "유형") {
        this.cdcdSector = this.searchCdcdSector;
        this.cdcdEng = "";
        this.cdcdKor = "";
      } else {
        this.cdcdSector = 0;
        this.cdcdEng = "";
        this.cdcdKor = "";
      }

      const searchReq = {
        cdcdSector: this.cdcdSector,
        cdcdKor: this.cdcdKor,
        cdcdEng: this.cdcdEng,
        // page: this.page - 1 < 0 ? 0 : this.page - 1,
        // size: this.count,
      };

      this.$store.dispatch(`cdcdmngTn/searchCdcdmngList`, searchReq).then(
        (response) => {
          this.cdcds = response.cdcds;
          // this.page = cdcds.currentPage;
          // this.showPage = cdcds.totalPages;
        },
        (error) => {
          console.log("searchCdcdList failed", error);
          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },
    deleteCdcd(id) {
      this.$store.dispatch("cdcdmngTn/deleteCdcdmng", id).then(
        (response) => {
          alert(response.data.message);
          this.resetForm();
          this.openDrawer = false;
          this.handlePageChange();
        },
        (error) => {
          console.log("deleteCdcd failed", error);
        }
      );
      this.resetForm();
    },
    resetForm() {
      if (this.edited.cdcdUuid != 0) {
        this.edited = this.updateEdited;
      } else {
        this.edited = Object.assign({}, this.initEdited);
      }
    },
    onReset() {
      this.searchIdx = "";
      this.searchWord = "";
      this.cdcdSector = "";
      this.cdcdKor = "";
      this.cdcdEng = "";
      this.searchCdcdList();
    },
  },
  created() {
    this.searchCdcdList();
    //카드유형
    this.getCommonCode({ upCode: 22, codeLvl: "1", dataName: "cdcdSectorOptions" });

  },
};
</script>
<style lang="scss">
#cdcdform {
  #cdcdform-grid-container {
    flex-grow: 1;

    #cdcdform-grid {
      height: 100%;
    }
  }
}
</style>
