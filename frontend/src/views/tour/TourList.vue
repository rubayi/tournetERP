<template>
  <div id="tourform">
    <q-page class="q-pa-md">
      <div class="row">
        <div class="col q-pr-md flex items-center">
          <span class="part_title text-primary">
            <q-icon name="person" class="q-ml-xs q-mb-xs" size="md"></q-icon
            >투어관리</span>
        </div>
        <div class="col-5 text-right">
          <q-form @submit="searchTourInfoList">
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
                v-model="searchCategory"
                :options="tourCategoryOptions"
                option-value="codeValue"
                option-label="codeKr"
                emit-value
                map-options
                label="상품유형 *"
              />
              <q-input
                v-if="this.searchIdx != '상태'"
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
                  label="+ 상품등록"
                  color="green"
                  @click="createAction"
                />
              </div>
            </div>
          </q-form>
        </div>
      </div>
      <div id="tour-form-grid-container" class="row">
        <table-comp
          id="tour-form-grid"
          class="ag-theme-alpine grid"
          :column-defs="colDefs"
          :row-data="tours"
          :on-cell-clicked="openAction"
        />
      </div>
      <tour-form-drawer
        :open-drawer="openDrawer"
        :drawer-width="drawerWidth"
        :dataVal="edited"
        :on-close-click="closeAction"
        @update:openDrawer="openDrawer = $event"
        @dataSaved="handlePageChange"
      />
    </q-page>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { tourInfoFormTableConfig } from "src/views/tour/TourFormTableConfig";
import { initialData } from "src/views/tour/TourData";
import { getCommonValue } from "src/utils/common.js"; // 공통코드 값

//Component
import TableComp from "src/components/table/TableComp.vue";
// Layout
import TourFormDrawer from "src/views/tour/TourFormDrawer.vue";
import EventBus from "src/common/EventBus";

export default {
  name: "TourInfoTnList",
  components: {
    TableComp,
    TourFormDrawer,
  },
  setup() {
    const drawerWidth = ref(0);

    onMounted(() => {
      drawerWidth.value = window.innerWidth * 0.8;
      window.addEventListener("resize", () => {
        drawerWidth.value = window.innerWidth * 0.8;
      });
    });

    return {
      openDrawer: ref(false),
      searchIdx: ref(null),
      bar: ref(false),
      options: ["투어명", "투어명(영문)", "상태"],
      drawerWidth,
    };
  },
  data() {
    return {
      tourStatus: "",
      tourKor: "",
      tourEng: "",
      searchWord: "",
      searchCategory: "",
      initEdited: initialData,
      updateEdited: {},
      edited: initialData,
      colDefs: tourInfoFormTableConfig.columns(),
      tours: [],
      sector: "",
      tourCategoryOptions: {},
    };
  },
  methods: {
    async fetchTourInfoByCategory(sector) {
      // Use sector to fetch tour information
      console.log("Fetching tour info for sector:", sector);
      // Example usage:
      // const tours = await fetchToursByCategory(sector);
      // this.tours = tours;
    },

    handleSearchByCategory() {
      // Fetch tour information based on sector
      this.fetchTourInfoByCategory(this.sector);
    },

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
      if (params.column.colId == "edit") {
        params.data.password = "";
        this.updateEdited = Object.assign({}, params.data);
        this.edited = params.data;
        this.openDrawer = !this.openDrawer;
      }
    },

    closeAction() {
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },
    handlePageChange() {
      this.onReset();
    },
    searchTourInfoList() {
      if (this.searchIdx === "지역") {
        this.tourArea = this.searchWord;
        this.tourCategory = "";
        this.tourKor = "";
        this.tourEng = "";
      } else if (this.searchIdx === "투어명") {
        this.tourKor = this.searchWord;
        this.tourCategory = "";
        this.tourArea = "";
        this.tourEng = "";
      } else if (this.searchIdx === "투어명(영문)") {
        this.tourEng = this.searchWord;
        this.tourCategory = "";
        this.tourArea = "";
        this.tourKor = "";
      } else if (this.searchIdx === "투어구분") {
        this.tourCategory = this.searchCategory;
        this.tourEng = "";
        this.tourArea = "";
        this.tourKor = "";
      }

      const searchReq = {
        tourCategory: this.tourCategory,
        tourKor: this.tourKor,
        tourEng: this.tourEng,
        tourArea: this.tourArea,
      };

      this.$store.dispatch(`tourTn/searchTourList`, searchReq).then(
        (tours) => {

          this.tours = tours.listTour;

        },
        (error) => {
          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },

    deleteTourInfo(id) {
      this.$store.dispatch("tourTn/deleteTourInfo", id).then(
        (response) => {
          alert(response.data.message);
          this.resetForm();
          this.openDrawer = false;
          this.handlePageChange();
        },
        (error) => {
          console.log("deleteTourInfo failed", error);
        }
      );
      this.resetForm();
    },
    resetForm() {
      if (this.edited.tourUuid != 0) {
        this.edited = this.updateEdited;
      } else {
        this.edited = Object.assign({}, this.initEdited);
      }
    },
    onReset() {
      this.searchIdx = "";
      this.searchWord = "";
      this.tourArea = "";
      this.tourCategory = "";
      this.tourKor = "";
      this.tourEng = "";
      this.searchTourInfoList();
    },
  },
  created() {
    this.sector = this.$route.params.sector;

    this.fetchTourInfoByCategory(this.sector);

    this.searchTourInfoList();

    //상품유형
    this.getCommonCode({ upCode: 22, codeLvl: "1", dataName: "tourCategoryOptions" });

  },
};
</script>
<style lang="scss">
#tourform {
  #tourform-grid-container {
    flex-grow: 1;

    #tourform-grid {
      height: 100%;
    }
  }
}
</style>
