<template>
  <div :style="{ marginLeft: '15px', marginRight: '15px' }">
    <h5 :style="{ marginTop: '15px', marginBottom: '15px' }">직원관리</h5>

    <q-form @submit="searchEmpList"
              @reset="onReset">
      <div class="row q-col-gutter-sm" style="max-width: 900px">
        <q-select class="q-pa-sm col-3"
                  outlined
                  v-model="searchIdx"
                  :options="options"
                  label="검색방법 *" />
        <q-select v-if="this.searchIdx == '상태'" class="col-3"
                  outlined
                  v-model="searchEmpStatus"
                  :options="empStatusOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="검색방법 *" />
        <q-input v-if="this.searchIdx != '상태'" class="col-6" outlined
                 v-model="searchWord"
                 type="text"
                 label="검색어 *"
                 lazy-rules
                 :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>
        <div class="q-pa-sm q-gutter-sm">
          <q-btn label="검색" type="submit" color="primary"/>
        </div>
      </div>
      </q-form>
    <div class="grid-container">
      <div class="row">
        <ag-grid-vue
          :rowData="emps"
          :columnDefs="colDefs"
          style="width:100%; height: 500px"
          :onCellClicked="onCellClicked"
          class="ag-theme-quartz-dark"
        >
        </ag-grid-vue>
        <div class="q-col-lg q-pa-sm flex flex-center">
          <q-pagination
            v-model="page"
            :max="count"
            direction-links
            @click="handlePageChange"
          />
        </div>
      </div>
      <div class="toc q-mr-md">
        <form @submit.prevent="saveEmp">
          <input
            type="number"
            id="codeUuid"
            v-model="edited.empUuid"
            hidden
          />
          <input
            type="number"
            id="codeUuid"
            v-model="edited.username"
            hidden
          />
        </form>
      </div>
    </div>

  </div>
</template>

<script>
import { ref } from 'vue';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { AgGridVue } from "ag-grid-vue3";

export default {
  name: "EmpTn",
  components: {
    AgGridVue,
  },
  setup () {
    return {
      searchIdx: ref(null),
      options: [
        '사용자명', '직원명', '직원명(영문)', '상태'
      ]
    }
  },
  data() {
    return {
      //S: Paging SET
      page: 1, //현재페이지
      count: 5, //표시할 페이지 개수
      showPage: 5, //보여줄 row 개수
      //E: Paging SET

      empStatus: "",
      empKor:"",
      empEng: "",
      username: "",
      searchWord: "",
      searchEmpStatus: "",
      edited: {
        empUuid: 0,
        username: "",
        empEng: "",
        empKor: "",
        empWorkType: "",
        empDiv: "",
        empEmail: "",
        empStatus: "",
        empDob: "",
        empDobType: "",
        empMemo: "",
        empAddress1: "",
        empAddress2: "",
        empCity: "",
        empState: "",
        empCountry: "",
        empZip: "",
        empTitle: "",
        empRole: "",
      },
      colDefs: [
        {
          field: "empUuid",
          headerName: "번호",
          width: 30,
          sortable: true,
          filter: true,
          // checkboxSelection: true,
        },
        {
          field: "edit",
          headerName: "관리",
          width: 100,
          cellRenderer: function (params) {
            return `<q-btn style="background: #50d427ad; padding: 5px; border-radius: 4px; cursor: pointer; font-size: 12px;">${params.value}</q-btn>`;
          },
          valueGetter: function (params) {
            return "수정/삭제";
          },
        },
        {
          field: "username",
          headerName: "사용자명",
          width: 150,
          sortable: true,
          filter: true,
        },
        {
          field: "empKor",
          headerName: "직원명",
          width: 100,
          sortable: true,
          filter: true,
        },
        {
          field: "empEng",
          headerName: "직원명(영문)",
          width: 150,
          sortable: true,
          filter: true,
        },
        {
          field: "empEmail",
          headerName: "이메일",
          sortable: true,
          filter: true,
        },
        {
          field: "empDiv",
          headerName: "부서명",
          width: 100,
          sortable: true,
          filter: true,
        },
        {
          field: "empTitle",
          headerName: "직위",
          width: 50,
          sortable: true,
          filter: true,
        },
        {
          field: "empRole",
          headerName: "직책",
          width: 50,
          sortable: true,
          filter: true,
        },
        {
          field: "empPhone",
          headerName: "전화번호",
          width: 150,
          sortable: true,
          filter: true,
        },
        {
          field: "empWorkPhone",
          headerName: "내선번호",
          sortable: true,
          filter: true,
        },
        {
          field: "empEmailBook",
          headerName: "예약이메일",
          sortable: true,
          filter: true,
        },
        {
          field: "empAddress1",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empAddress2",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empCity",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empState",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empCountry",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empZip",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empDob",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empDobType",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empMemo",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empStatus",
          headerName: "주소",
          hide: true,
        },
        {
          field: "empEmailBook",
          headerName: "주소",
          hide: true,
        },
      ],
      emps: [],
      empStatusOptions: []
    };
  },
  mounted() {
    //재직상태
    const empOptionReq = {
      uprCodeUuid: '15',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", empOptionReq)
      .then(
        (commCode) => {
          this.empStatusOptions = commCode;
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
  },
  methods: {
    handlePageChange() {

      this.searchEmpList();
    },
    searchEmpList() {

      if (this.searchIdx === "사용자명") {
        this.username = this.searchWord;

        this.empStatus = "";
        this.empKor = "";
        this.empEng ="";
      } else if(this.searchIdx === "직원명") {
        this.empKor = this.searchWord;
        this.empStatus = "";
        this.username = "";
        this.empEng ="";
      } else if(this.searchIdx === "직원명(영문)") {
        this.empEng = this.searchWord;
        this.empStatus = "";
        this.username = "";
        this.empKor ="";
      } else if(this.searchIdx === "상태") {
        this.empStatus = this.searchEmpStatus.codeValue;
        this.empEng = "";
        this.username = "";
        this.empKor ="";
      }

      const searchReq = {
          empStatus: this.empStatus,
          empKor:this.empKor,
          empEng: this.empEng,
          username: this.username,

          page: (this.page - 1) < 0 ? 0:(this.page - 1),
          size: this.showPage,
      }
      this.$store.dispatch(`empTn/searchEmpList`, searchReq)
        .then(
        (emps) => {
          this.emps = emps.selectedUsers;
          this.page = emps.currentPage;
          this.count = emps.totalPages;
        },
        (error) => {
          console.log("searchEmpList failed", error);
        }
      );
    },
    saveEmp() {
      if (this.edited.empUuid) {
        this.$store.dispatch("empTn/updateEmp", this.edited).then(
          () => {
            this.resetForm();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      } else {
        this.$store.dispatch("empTn/createEmp", this.edited).then(
          () => {
            this.resetForm();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      }
    },
    onCellClicked(params) {
      console.log(params);
      // if (params.column.gid === "edit") {
        this.edited = params.data;
      // }
    },

    deleteEmp(id) {
      this.$store.dispatch("empTn/deleteEmp", id).then(
        () => {
          //this.getMainEmp();
        },
        (error) => {
          console.log("deleteEmp failed", error);
        }
      );
      this.resetForm();
    },
    resetForm() {

    },

  },
  created() {
    this.searchEmpList();
  },

};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 70% 30%;
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
