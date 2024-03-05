<template>
  <div :style="{ marginLeft: '15px', marginRight: '15px' }">
    <h5 :style="{ marginTop: '15px', marginBottom: '15px' }">직원관리</h5>
    <div class="grid-container">

      <div>
        <ag-grid-vue
          :rowData="emps"
          :columnDefs="colDefs"
          :onCellClicked="onCellClicked"
          style="height: 100%"
          class="ag-theme-quartz-dark"
        >
        </ag-grid-vue>
      </div>

      <div>

      </div>
    </div>
  </div>
</template>

<script>
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import { AgGridVue } from "ag-grid-vue3";

export default {
  name: "EmpTn",
  components: {
    AgGridVue,
  },
  data() {
    return {
      searchReq:{
        empStatus: "",
        empKor:"",
        empEng: "",
        username: "",
      },
      colDefs: [
        {
          field: "empUuid",
          headerName: "번호",
          sortable: true,
          filter: true,
          // checkboxSelection: true,
        },
        {
          field: "username",
          headerName: "사용자명",
          sortable: true,
          filter: true,
        },
        {
          field: "empEng",
          headerName: "직원명(영문)",
          sortable: true,
          filter: true,
        },
        {
          field: "empKor",
          headerName: "직원명(한글)",
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
          field: "empWorkType",
          headerName: "근무형태",
          sortable: true,
          filter: true,
        },
        {
          field: "empDiv",
          headerName: "부서명",
          sortable: true,
          filter: true,
        },
        {
          field: "empTitle",
          headerName: "직위",
          sortable: true,
          filter: true,
        },
        {
          field: "empPhone",
          headerName: "전화번호",
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
          field: "empZip",
          headerName: "우편번호",
          sortable: true,
          filter: true,
        },
        {
          field: "empAddress1",
          headerName: "주소1",
          sortable: true,
          filter: true,
        },
        {
          field: "empAddress2",
          headerName: "주소2",
          sortable: true,
          filter: true,
        },
        {
          field: "empCity",
          headerName: "도시",
          sortable: true,
          filter: true,
        },

      ],
      emps: [],
    };
  },
  methods: {
    searchEmpList() {
      this.$store.dispatch(`empTn/searchEmpList`, this.searchReq)
        .then(
        (emps) => {
          this.emps = emps;
        },
        (error) => {
          console.log("searchEmpList failed", error);
        }
      );
    },
    saveEmp() {
      if (this.empMemoed.empUuid) {
        this.$store.dispatch("empTn/updateEmp", this.empMemoed).then(
          () => {
            this.resetForm();
          },
          (error) => {
            console.log("saveEmp failed", error);
          }
        );
      } else {
        this.$store.dispatch("empTn/createEmp", this.empMemoed).then(
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
      if (params.column.getColId() === "empMemo") {
        this.empMemoed = Object.assign({}, params.data);
      }
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
    getDisplayEmp(emp) {
      return {
        empUuid: emp.empUuid,
        username: emp.username,
        empEmail: emp.empEmail,
        password: emp.password,
        empKor: emp.empKor,
        empEng: emp.empEng,
        empWorkType: emp.empWorkType,
        empDiv: emp.empDiv,
        empTitle: emp.empTitle,
        empPhone: emp.empPhone,
        empWorkPhone: emp.empWorkPhone,
        empEmailBook: emp.empEmailBook,
        empAddress1: emp.empAddress1,
        empAddress2: emp.empAddress2,
        empCity: emp.empCity,
        empState: emp.empState,
        empCountry: emp.empCountry,
        empZip: emp.empZip,
        empDob: emp.empDob,
        empDobType: emp.empDobType,
        empStatus:emp.empStatus,
        empMemo: emp.empMemo
      };
    }
  },
  created() {
    this.searchEmpList();
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
