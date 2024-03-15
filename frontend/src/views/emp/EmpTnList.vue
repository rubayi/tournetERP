<template>
  <component-to-re-render :key="componentKey" />
  <div class="q-pa-md q-gutter-sm">
    <div class="q-px-lg">
      <div class="q-py-lg text-h4 text-weight-bold text-blue">직원관리</div>

      <q-form @submit="searchEmpList">
        <div class="row q-col-gutter-sm" style="max-width: 900px">
          <q-select class="col-3"
                    v-model="searchIdx"
                    :options="options"
                    label="검색방법 *" />
          <q-select v-if="this.searchIdx == '상태'" class="col-3"
                    v-model="searchEmpStatus"
                    :options="empStatusOptions"
                    option-value="codeValue"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="재직상태 *" />
          <q-input v-if="this.searchIdx != '상태'"
                   v-model="searchWord"
                   type="text"
                   label="검색어 *"
          />
          <div class="q-py-md">
            <q-btn label="검색" type="submit" color="primary"/>
            <q-btn style="margin-left: 5px" label="전체검색" color="secondary" @click="onReset"/>
          </div>
          <div class="q-pa-md">
            <q-btn label="+ 사용자등록" color="green" @click="createAction"/>
          </div>
        </div>
      </q-form>
    </div>
    <div class="row q-px-lg">
      <div class="col-12">
        <table-comp
            id="memberform-grid"
            class="ag-theme-alpine grid"
            :column-defs="colDefs"
            :row-data="emps"
            :onCellClicked="onCellClicked"
        />
        <div class="q-col-lg q-pa-sm flex flex-center">
          <page-comp
              v-model="page"
              :max="count"
              direction-links
              @click="handlePageChange"
          />
        </div>
      </div>
    </div>
    <emp-form-drawer
        :openDrawer="openDrawer"
        drawerWidth="500"
        :dataVal="edited"
    />

  </div>

</template>

<script>
import { ref } from 'vue';
import { EmpFormTableConfig } from "src/views/emp/EmpSimpleFormTableConfig";
import { initialData } from "src/views/emp/EmpData";
import { getCommonValue } from 'src/utils/common.js'; // 공통코드 값

//Component
import TableComp from "src/components/table/TableComp.vue";
import PageComp from "src/components/table/PaginationRenderer.vue";
// Layout
import EmpFormDrawer from "src/views/emp/EmpFormDrawer.vue";


export default {
  name: "EmpTn",
  components: {
    TableComp,
    PageComp,
    EmpFormDrawer
  },
  setup () {
    return {
      openDrawer: ref(false),
      searchIdx: ref(null),
      bar: ref(false),
      options: [
        '사용자명', '직원명', '직원명(영문)', '상태'
      ],

    }
  },
  data() {
    return {
      //S: Paging SET
      page: 1, //현재페이지
      count: 5, //표시할 페이지 개수
      showPage: 15, //보여줄 row 개수
      //E: Paging SET

      componentKey: 0,
      empStatus: "",
      empKor:"",
      empEng: "",
      username: "",
      searchWord: "",
      searchEmpStatus: "",
      workOptions: [],
      divOptions: [],
      titleOptions: [],
      empRoleOptions: [],
      dobTypeOptions: [],
      countryOptions: [],
      initEdited : initialData,
      updateEdited:{},
      edited: initialData,
      colDefs: EmpFormTableConfig.columns(),
      emps: [],
      empStatusOptions: []
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
      console.log(this.edited);
    },

    /* Edit */
    openAction(empVal) {
      this.edited = empVal;
      this.openDrawer = !this.openDrawer;
    },


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
        this.empStatus = this.searchEmpStatus;
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
          .then((emps) => {
                this.emps = emps.selectedUsers;
                this.page = emps.currentPage;
                this.count = emps.totalPages;
              },
              (error) => {
                console.log("searchEmpList failed", error);
              }
          );
    },
    onClickSave() {

      if (this.edited.empUuid != 0) {
        this.$store.dispatch("empTn/updateEmp", this.edited).then(
            (response) => {
              alert(response.data.message);
              this.resetForm();
              this.openDrawer=false;
              //  this.handlePageChange();
            },
            (error) => {
              console.log("saveEmp failed", error);
            }
        );
      } else {

        this.$store.dispatch("auth/register", this.edited)
            .then(
                (response) => {
                  alert(response.message);
                  this.resetForm();
                  this.openDrawer=false;
                  this.handlePageChange();
                },
                (error) => {
                  console.log("saveEmp failed", error);
                }
            );
      }
    },
    onCellClicked(params) {
      this.openDrawer = true;
      // if (params.column.gid === "edit") {
      params.data.password = "";
      this.updateEdited = Object.assign({}, params.data);
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
      console.log(this.updateEdited);
      if (this.edited.empUuid != 0) {
        this.edited = this.updateEdited;
      } else {
        this.edited = Object.assign({}, this.initEdited);
      }
    },
    onReset () {
      this.searchIdx = "";
      this.searchWord = "";
      this.username = "";
      this.empStatus = "";
      this.empKor = "";
      this.empEng ="";
      this.searchEmpList();
    }

  },
  created() {
    this.searchEmpList();
    //부서명
    this.getCommonCode({upCode: '19', codeLvl:'1', dataName:'divOptions'});
    //직위
    this.getCommonCode({upCode: '17', codeLvl:'1', dataName:'titleOptions'});
    //국가코드
    //this.getCommonCode('1', '1', 'countryOptions');
    this.getCommonCode({upCode: '1', codeLvl:'1', dataName:'countryOptions'});
    //생일타입
    this.getCommonCode({upCode: '222', codeLvl:'1', dataName:'dobTypeOptions'});
    //직책
    this.getCommonCode({upCode: '18', codeLvl:'1', dataName:'empRoleOptions'});
    //근무형태
    // this.getCommonCode('16', '1', 'workOptions');
    this.getCommonCode({upCode: '16', codeLvl:'1', dataName:'workOptions'});
    //재직상태
    this.getCommonCode({upCode: '15', codeLvl:'1', dataName:'empStatusOptions'});
  },

};
</script>

<style scoped>

</style>
