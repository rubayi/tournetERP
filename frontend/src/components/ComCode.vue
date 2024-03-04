<template>
  <div :style="{ marginLeft: '15px', marginRight: '15px' }">
    <h5 :style="{ marginTop: '15px', marginBottom: '15px' }">코드관리</h5>
    <div class="grid-container">
      <div>
        <div class="toc">
          <ul class="list no-bullets">
            <li>
              <button class="plain-button" @click="getMainComCode">
                전체코드
              </button>
            </li>
            <li v-for="comCode in comCodes" :key="comCode.codeUuid">
              <button class="plain-button" @click="chooseMainComCode(comCode)">
                {{ comCode.codeKr }}
              </button>
            </li>
          </ul>
        </div>
      </div>

      <div>
        <ag-grid-vue
          :rowData="chosenComCodes"
          :columnDefs="colDefs"
          :onCellClicked="onCellClicked"
          style="height: 100%"
          class="ag-theme-quartz-dark"
        >
        </ag-grid-vue>
      </div>

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
      </div>
    </div>
  </div>
</template>

<script>
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { AgGridVue } from "ag-grid-vue3";

export default {
  name: "ComCode",
  components: {
    AgGridVue,
  },
  data() {
    return {
      colDefs: [
        {
          field: "codeUuid",
          headerName: "분류코드",
          sortable: true,
          filter: true,
          // checkboxSelection: true,
        },
        {
          field: "codeEn",
          headerName: "코드명(영문)",
          sortable: true,
          filter: true,
        },
        {
          field: "codeKr",
          headerName: "코드명(한글)",
          sortable: true,
          filter: true,
        },
        {
          field: "codeValue",
          headerName: "코드값",
          sortable: true,
          filter: true,
        },
        {
          field: "edit",
          headerName: "관리",
          cellRenderer: function (params) {
            return `<q-btn style="background: #50d427ad; padding: 5px; border-radius: 4px; cursor: pointer; font-size: 12px;">${params.value}</q-btn>`;
          },
          valueGetter: function (params) {
            return "수정/삭제";
          },
        },
      ],
      comreq: null,
      edited: {
        codeUuid: 0,
        codeEn: "",
        codeKr: "",
        codeValue: "",
        codeLvl: "",
        codeOrd: "",
        useYn: "",
        uprCodeUuid: null,
      },
      comCodes: [],
      chosenComCodes: [],
    };
  },
  methods: {
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
  },
  created() {
    this.getMainComCode();
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 20% 60% auto;
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
