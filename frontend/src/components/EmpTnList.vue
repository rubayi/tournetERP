<template>
  <div class="q-pa-md q-gutter-sm">
    <div class="q-px-lg">
      <div class="q-py-lg text-h4 text-weight-bold text-blue">직원관리</div>

      <q-form @submit="searchEmpList"
                @reset="onReset">
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
                    label="재직상태 *" />
          <q-input v-if="this.searchIdx != '상태'"
                   v-model="searchWord"
                   type="text"
                   label="검색어 *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>
          <div class="q-py-md">
            <q-btn label="검색" type="submit" color="primary"/>
          </div>

          <div class="q-py-md">
            <q-btn label="+ 사용자등록" color="green" @click="onClickAdd"/>
          </div>
        </div>
      </q-form>
    </div>
      <div class="row q-px-lg">
        <div class="col-8">
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
    </div>
      <div class="q-pa-md q-gutter-sm">

        <q-dialog v-model="showForm">
          <q-card style="width: 700px; max-width: 80vw;">
            <q-bar class="q-pa-lg">
              <div>사용자 정보 관리</div>
              <q-space />
              <q-btn flat icon="close" v-close-popup>
                <q-tooltip>닫기</q-tooltip>
              </q-btn>
            </q-bar>

            <q-card-section class="scroll">
              <div class="row q-col-gutter-sm">
                <input
                  id="empUuid"
                  v-model="edited.empUuid"
                  hidden
                />
                <q-input
                  class="col-4"
                  type="text"
                  id="username"
                  v-model="edited.username"
                  label="사용자명(username)"
                />

                <q-input
                  class="col-4"
                  type="text"
                  id="empKor"
                  v-model="edited.empKor"
                  label="한글이름(Name Kor)"
                />
                <q-input
                  class="col-4"
                  type="text"
                  id="empEng"
                  v-model="edited.empEng"
                  label="영문이름(Name Eng)"
                />
                <q-select
                  class="col-3"
                  v-model="edited.empWorkType"
                  :options="workOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="근무형태" />
                <q-select
                  class="col-3"
                  v-model="edited.empDiv"
                  :options="divOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="부서명" />
                <q-select
                  class="col-3"
                  v-model="edited.empTitle"
                  :options="titleOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="직위" />
                <q-select
                  class="col-3"
                  v-model="edited.empRole"
                  :options="empRoleOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="직책" />
                <q-input
                  class="col-6"
                  type="text"
                  id="username"
                  v-model="edited.empPhone"
                  label="핸드폰(Mobile) *"
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="empWorkPhone"
                  v-model="edited.empWorkPhone"
                  label="내선번호(Work Phone) "
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="empEmail"
                  v-model="edited.empEmail"
                  label="이메일(Email)"
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="empEmailBook"
                  v-model="edited.empEmailBook"
                  label="예약이메일(Email)"
                />
                <q-select
                  class="col-6"
                  v-model="edited.empDobType"
                  :options="dobTypeOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="생일타입" />

                <q-input class="col-6" v-model="edited.empDob" mask="####/##/##" :rules="['date']">
                  <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                      <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                        <q-date v-model="edited.empDob" minimal>
                          <div class="row items-center justify-end">
                            <q-btn v-close-popup label="Close" color="primary" flat />
                          </div>
                        </q-date>
                      </q-popup-proxy>
                    </q-icon>
                  </template>
                </q-input>
                <q-input
                  class="col-3"
                  type="text"
                  id="empZip"
                  v-model="edited.empZip"
                  label="우편번호(Zip)"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="empCity"
                  v-model="edited.empCity"
                  label="도시(City) "
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="empState"
                  v-model="edited.empState"
                  label="주/도(State) "
                />
                <q-select
                  class="col-3"
                  v-model="empCountry"
                  :options="countryOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  label="국가(Country)" />
                <q-input
                  class="col-6"
                  bottom-slots
                  type="text"
                  id="empAddress1"
                  v-model="edited.empAddress1"
                  label="주소1(Address1) "
                />
                <q-input
                  class="col-6"
                  bottom-slots
                  type="text"
                  id="empAddress2"
                  v-model="edited.empAddress2"
                  label="주소2(Address2) "
                />

              </div>
            </q-card-section>
            <q-card-actions align="right">
              <q-btn v-if="edited.empUuid != 0" :disabled="loading" label="정보수정" type="submit" color="primary"/>
              <q-btn v-if="edited.empUuid == 0" :disabled="loading" label="사용자등록" type="submit" color="primary"/>
              <q-btn label="초기화" type="reset" color="primary" flat class="q-ml-sm" />
            </q-card-actions>
          </q-card>
        </q-dialog>
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
      showPage: 5, //보여줄 row 개수
      //E: Paging SET

      showForm: false,

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
    //근무형태
    const workOptionReq = {
      uprCodeUuid: '16',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", workOptionReq)
      .then(
        (commCode) => {
          this.workOptions = commCode;
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
    //부서명
    const divOptionsReq = {
      uprCodeUuid: '19',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", divOptionsReq)
      .then(
        (commCode) => {
          this.divOptions = commCode;
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
    //직위
    const titleOptionsReq = {
      uprCodeUuid: '17',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", titleOptionsReq)
      .then(
        (commCode) => {
          this.titleOptions = commCode;
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

    //직책
    const empRoleOptionsReq = {
      uprCodeUuid: '18',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", empRoleOptionsReq)
      .then(
        (commCode) => {
          this.empRoleOptions = commCode;
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
    //생일타입
    const dobTypeOptionsReq = {
      uprCodeUuid: '222',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", dobTypeOptionsReq)
      .then(
        (commCode) => {
          this.dobTypeOptions = commCode;
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
    //국가코드
    const countryOptionsReq = {
      uprCodeUuid: '1',
      codeLvl:'1'
    }
    this.$store.dispatch("comCode/useComCode", countryOptionsReq)
      .then(
        (commCode) => {
          this.countryOptions = commCode;
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
      this.showForm = true;
      // if (params.column.gid === "edit") {
        this.edited = params.data;
      // }

    },
    onClickAdd(){
      this.edited = params.data;
      this.showForm = true;
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

</style>
