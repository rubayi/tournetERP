<template>
  <component-to-re-render :key="componentKey" />
  <div id="officeform">
    <q-page class="q-pa-md">
      <div class="row">
        <div class="col q-pr-md flex items-center">
          <span class="part_title text-primary">
            <q-icon name="code" class="q-ml-xs q-mb-xs" size="md"></q-icon>
            코드관리</span
          >
        </div>
        <div class="col-5 text-right">
          <div class="row q-col-gutter-sm justify-end">
            <q-select
              class="col-6"
              v-model="searchIdx"
              :options="comCodes"
              option-label="codeKr"
              emit-value
              map-options
              label="코드분류 *"
              @update:modelValue="chooseMainComCode"
            />
            <div class="q-pa-md">
              <q-btn label="+ 코드등록" color="green" @click="createAction" />
            </div>
          </div>
        </div>
      </div>

      <div id="officeform-grid-container" class="row">
        <table-comp
          :row-data="chosenComCodes"
          :column-defs="colDefs"
          :on-cell-clicked="openAction"
          class="ag-theme-alpine grid"
          id="memberform-grid"
        />
      </div>

      <code-form-drawer
        :open-drawer="openDrawer"
        :drawer-width="500"
        :data-val="edited"
        :on-close-click="closeAction"
        @save="saveComCode"
        @delete="deleteComCode"
      />
    </q-page>
  </div>
</template>

<script>
import { ref } from "vue";
import { CodeFormTableConfig } from "src/views/code/CodeFormTableConfig";
import { initialData } from "src/views/code/CodeData";
import TableComp from "src/components/table/TableComp.vue";
import CodeFormDrawer from "src/views/code/CodeFormDrawer.vue";

export default {
  name: "ComCode",
  components: {
    TableComp,
    CodeFormDrawer,
  },
  setup() {
    return {
      openDrawer: ref(false),
      searchIdx: ref(null),
      bar: ref(false),
    };
  },
  data() {
    return {
      colDefs: CodeFormTableConfig.columns(),
      comreq: null,
      edited: initialData,
      comCodes: [{ codeKr: "전체코드" }],
      chosenComCodes: [],
    };
  },
  methods: {
    createAction() {
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },
    openAction(params) {
      params.data.password = "";
      this.updateEdited = Object.assign({}, params.data);
      this.edited = params.data;
      this.openDrawer = !this.openDrawer;
    },
    getMainComCode() {
      this.$store.dispatch("comCode/getMainComCodeList").then(
        (comCodes) => {
          this.comCodes = comCodes;
          this.chosenComCodes = comCodes;
        },
        (error) => {
          console.log("getMainComCode failed", error);
        }
      );
    },
    saveComCode(editedData) {
      console.log("editedData", editedData);
      if (editedData.codeUuid) {
        this.$store.dispatch("comCode/updateComcode", editedData).then(
          (response) => {
            alert(response.data.message);
            this.openDrawer = false;
            this.handlePageChange();
          },
          (error) => {
            console.log("saveComCode failed", error);
          }
        );
      } else {
        this.$store.dispatch("comCode/createComcode", editedData).then(
          (response) => {
            alert(response.data.message);
            this.openDrawer = false;
            this.handlePageChange();
          },
          (error) => {
            console.log("saveComCode failed", error);
          }
        );
      }
    },
    onCellClicked(params) {
      if (params.column.getColId() === "edit") {
        this.edited = Object.assign({}, params.data);
      }
    },
    chooseMainComCode(comCode) {
      if (comCode.codeKr === "전체코드") {
        this.getMainComCode();
        return;
      }
      this.$store.dispatch("comCode/SearchComCodeListByGrp", comCode).then(
        (comcodes) => {
          this.chosenComCodes = comcodes;
          this.edited = {
            codeUuid: 0,
            codeEn: "",
            codeKr: "",
            codeValue: "",
            codeOrd: "",
            useYn: "",
            codeLvl: "",
            uprCodeUuid: comCode.codeUuid,
          };
        },
        (error) => {
          console.log("chooseMainComCode failed", error);
        }
      );
    },
    deleteComCode(id) {
      this.$store.dispatch("comCode/deleteComcode", id).then(
        (response) => {
          alert(response.data.message);
          this.openDrawer = false;
          this.handlePageChange();
        },
        (error) => {
          console.log("deleteComcode failed", error);
        }
      );
    },
    resetForm() {
      this.edited = {
        codeUuid: 0,
        codeEn: "",
        codeKr: "",
        codeValue: "",
        codeOrd: "",
        useYn: "",
        codeLvl: "",
        uprCodeUuid: this.chosenComCodes[0]?.uprCodeUuid ?? 0,
      };
    },
    handlePageChange() {
      this.resetForm();
      this.getMainComCode();
    },
    closeAction() {
      this.edited = initialData;
      this.openDrawer = !this.openDrawer;
    },
  },
  created() {
    this.getMainComCode();
  },
};
</script>
<style lang="scss">
#officeform {
  #officeform-grid-container {
    flex-grow: 1;

    #officeform-grid {
      height: 100%;
    }
  }
}
.ag-theme-alpine .ag-cell {
  display: flex;
  align-items: center;
}
.ag-header-cell {
  background-image: url("src/assets/top_main.png");
  color: #fff;
  color: var(--ag-background-color, #0c2c8b);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
