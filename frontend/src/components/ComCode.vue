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
        :select-options="selectOptions"
      />

      <div>
        <div class="toc">
          <div style="padding: 3px">코드등록</div>
          <form @submit.prevent="saveComCode">
            <input
              type="number"
              id="codeUuid"
              v-model="edited.codeUuid"
              hidden
            />
            <div class="spaces">
              <input
                class="inputBox"
                type="text"
                placeholder="영문 상세코드명"
                id="codeEn"
                v-model="edited.codeEn"
              />
            </div>
            <div class="spaces">
              <input
                class="inputBox"
                type="text"
                placeholder="한글 상세코드명"
                id="codeKr"
                v-model="edited.codeKr"
                required
              />
            </div>
            <div class="spaces">
              <input
                class="inputBox"
                type="text"
                placeholder="코드값 ex) 001"
                id="codeValue"
                v-model="edited.codeValue"
                required
              />
            </div>
            <div class="spaces">
              <input
                class="inputBox"
                type="text"
                placeholder="코드레벨 ex) 0,1,2.."
                id="codeLvl"
                v-model="edited.codeLvl"
                required
              />
            </div>
            <div class="spaces">
              <input
                class="inputBox"
                type="number"
                placeholder="코드정렬순서"
                id="codeOrd"
                v-model="edited.codeOrd"
                required
              />
            </div>
            <div class="spaces">
              <input
                class="inputBox"
                type="text"
                placeholder="코드사용여부"
                id="useYn"
                v-model="edited.useYn"
                required
              />
            </div>
            <div class="spaces">
              <button type="submit" class="save">
                {{ edited.codeUuid ? "수정" : "저장" }}
              </button>
              <button class="clear" type="reset" @click="resetForm">
                취소
              </button>
              <button
                class="delete"
                type="button"
                @click="deleteComCode(edited.codeUuid)"
              >
                삭제
              </button>
            </div>
          </form>
        </div>
      </div></q-page
    >
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
          this.comCodes = [...this.comCodes, ...comCodes];
          this.chosenComCodes = comCodes;
        },
        (error) => {
          console.log("getMainComCode failed", error);
        }
      );
    },
    saveComCode() {
      if (this.edited.codeUuid) {
        this.$store.dispatch("comCode/updateComcode", this.edited).then(
          () => {
            this.getMainComCode();
            this.resetForm();
          },
          (error) => {
            console.log("saveComCode failed", error);
          }
        );
      } else {
        this.$store.dispatch("comCode/createComcode", this.edited).then(
          () => {
            this.getMainComCode();
            this.resetForm();
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
        () => {
          this.getMainComCode();
        },
        (error) => {
          console.log("deleteComcode failed", error);
        }
      );
      this.resetForm();
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
