<template>
  <div>
    <h3>코드관리</h3>
    <div class="grid-container">
      <div>
        <div class="toc">
          <ul class="list no-bullets">
            <li v-for="comCode in comCodes" :key="comCode.codeUuid">
              <button class="plain-button" @click="chooseMainComCode(comCode)">
                {{ comCode.codeKr }}
              </button>
            </li>
          </ul>
        </div>
      </div>
      <div>
        <!-- <table style="width: 100%">
          <thead>
                <tr>
                  <th style="width: 25%">분류코드</th>
                  <th style="width: 25%">코드명(영문)</th>
                  <th style="width: 25%">코드명(한글)</th>
                  <th style="width: 25%">관리</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="comCode in chosenComCodes" :key="comCode.codeUuid">
                  <td style="text-align: center">{{ comCode.codeUuid }}</td>
                  <td style="text-align: center">{{ comCode.codeEn }}</td>
                  <td style="text-align: center">{{ comCode.codeKr }}</td>
                  <td style="text-align: center">
                    <button @click="editComCode(comCode)">수정/삭제</button>
                  </td>
                </tr>
              </tbody> </table>-->
        <!-- <q-card class="no-shadow" bordered>
          <q-card-section>
            <q-table
              square
              class="no-shadow"
              title="상세코드"
              :rows="chosenComCodes"
              :columns="columns"
              row-key="name"
              :filter="filter"
            >
              <template v-slot:body-cell-edit="props">
                <q-td :props="props">
                  <button @click="editComCode(props.row)">수정/삭제</button>
                </q-td>
              </template>
            </q-table>
          </q-card-section>
        </q-card> -->
        <ag-grid-vue
          :rowData="chosenComCodes"
          :columnDefs="colDefs"
          style="height: 500px"
          class="ag-theme-quartz"
        >
        </ag-grid-vue>
      </div>
      <div>
        <div class="toc">
          <div>코드등록</div>
          <form @submit.prevent="saveComCode">
            <input
              type="number"
              id="                   "
              v-model="edited.codeUuid"
              hidden
            />
            <div>
              <input
                type="text"
                placeholder="영문 상세코드명"
                id="codeEn"
                v-model="edited.codeEn"
                required
              />
            </div>
            <div>
              <input
                type="text"
                placeholder="한글 상세코드명"
                id="codeKr"
                v-model="edited.codeKr"
                required
              />
            </div>
            <div>
              <input
                type="text"
                placeholder="코드값 ex) 001"
                id="codeValue"
                v-model="edited.codeValue"
                required
              />
            </div>
            <div>
              <input
                type="text"
                placeholder="코드레벨 ex) 0,1,2.."
                id="codeLvl"
                v-model="edited.codeLvl"
                required
              />
            </div>
            <div>
              <input
                type="number"
                placeholder="코드정렬순서"
                id="codeOrd"
                v-model="edited.codeOrd"
                required
              />
            </div>
            <div>
              <input
                type="text"
                placeholder="코드사용여부"
                id="useYn"
                v-model="edited.useYn"
                required
              />
            </div>
            <div>
              <button type="submit">저장</button>
              <button type="reset" @click="resetForm">취소</button>
              <button type="button" @click="deleteComCode(edited.codeUuid)">
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
import "ag-grid-community/styles/ag-grid.css"; // Mandatory CSS required by the grid
import "ag-grid-community/styles/ag-theme-quartz.css"; // Optional Theme applied to the grid
import { AgGridVue } from "ag-grid-vue3"; // AG Grid Component

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
          checkboxSelection: true,
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
          field: "edit",
          headerName: "관리",
          cellRenderer: function (params) {
            return `<button @click="editComCode(${params.data})">수정/삭제</button>`;
          },
        },
      ],
      comreq: null,
      edited: {
        codeUuid: "",
        codeEn: "",
        codeKr: "",
        codeValue: "",
        codeOrd: "",
        useYn: "",
        uprCodeUuid: null,
      },
      comCodes: [],
      chosenComCodes: [],
      // columns: [
      //   {
      //     name: "codeUuid",
      //     required: true,
      //     label: "분류코드",
      //     align: "center",
      //     field: "codeUuid",
      //   },
      //   {
      //     name: "codeEn",
      //     required: true,
      //     label: "코드명(영문)",
      //     align: "center",
      //     field: "codeEn",
      //   },
      //   {
      //     name: "codeKr",
      //     required: true,
      //     label: "코드명(한글)",
      //     align: "center",
      //     field: "codeKr",
      //   },
      //   {
      //     name: "edit",
      //     required: true,
      //     label: "관리",
      //     align: "center",
      //     name: "edit",
      //   },
      // ],
    };
  },
  methods: {
    // getComCodes() {
    //   this.$store.dispatch("comCode/getComCodeList").then(
    //     (comCodes) => {
    //       this.comCodes = comCodes;
    //     },
    //     (error) => {
    //       console.log("getComCodes failed", error);
    //     }
    //   );
    // },
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
            this.edited = null;
          },
          (error) => {
            console.log("saveComCode failed", error);
          }
        );
      } else {
        this.$store.dispatch("comCode/createComcode", this.edited).then(
          () => {
            this.getMainComCode();
            this.edited = null;
          },
          (error) => {
            console.log("saveComCode failed", error);
          }
        );
      }
    },
    editComCode(comCode) {
      this.edited = Object.assign({}, comCode);
    },
    chooseMainComCode(comCode) {
      this.$store.dispatch("comCode/SearchComCodeListByGrp", comCode).then(
        (comcodes) => {
          this.chosenComCodes = comcodes;
          this.edited.uprCodeUuid = comCode.uprCodeUuid;
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
    },
    resetForm() {
      this.edited = {
        codeUuid: "",
        codeEn: "",
        codeKr: "",
        codeValue: "",
        codeOrd: "",
        useYn: "",
        uprCodeUuid: null,
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
  grid-template-columns: 20% 60% 20%;
  gap: 20px;
}
.no-bullets {
  list-style-type: none;
  border: 1px solid #b9b8b8;
}
.toc {
  border: 1px solid #b9b8b8;
  padding: 10px;
}

.list {
  margin: 0;
  padding: 0;
}

.list > li {
  border-bottom: #333333 1px solid;
}

.list > li:last-child {
  border-bottom: none;
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
