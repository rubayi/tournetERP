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
            <q-btn label="+ 사용자등록" color="green" @click="onClickAdd"/>
          </div>
        </div>
      </q-form>
    </div>
    <div class="row q-px-lg">
      <div class="col-10">
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

          <q-form>
            <q-card-section class="scroll">
              <div class="row q-col-gutter-sm">

                <input
                    id="empUuid"
                    v-model="edited.empUuid"
                    hidden
                />
                <q-input
                    class="col-3"
                    type="text"
                    id="username"
                    v-model="edited.username"
                    label="사용자명(username) *"
                    lazy-rules
                    :rules="[ val => val != '' || '사용자명을 입력 해 주십시오.']"
                />

                <q-input
                    v-if="edited.empUuid != 0"
                    class="col-3"
                    type="password"
                    id="password"
                    v-model="edited.password"
                    label="암호"
                    hint="입력하지 않으면 변경되지 않습니다."
                />

                <q-input
                    v-if="edited.empUuid == 0"
                    class="col-3"
                    type="password"
                    id="password"
                    v-model="edited.password"
                    label="암호* "
                    lazy-rules
                    :rules="[ val => !!val  || '암호를 입력 해 주십시오.']"
                />
                <q-input
                    class="col-3"
                    type="text"
                    id="empKor"
                    v-model="edited.empKor"
                    label="이름(한글이름) *"
                    lazy-rules
                    :rules="[ val => !!val  || '한글이름 입력 해 주십시오.']"
                />
                <q-input
                    class="col-3"
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
                    emit-value
                    map-options
                    label="근무형태" />
                <q-select
                    class="col-3"
                    v-model="edited.empDiv"
                    :options="divOptions"
                    option-value="codeValue"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="부서명" />
                <q-select
                    class="col-3"
                    v-model="edited.empTitle"
                    :options="titleOptions"
                    option-value="codeValue"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="직위" />
                <q-select
                    class="col-3"
                    v-model="edited.empRole"
                    :options="empRoleOptions"
                    option-value="codeValue"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="직책" />

                <q-input
                    class="col-6"
                    type="text"
                    id="username"
                    v-model="edited.empPhone"
                    label="핸드폰 *"
                    lazy-rules
                    :rules="[ val => val && val.length > 0 || '핸드폰 번호를 입력 해 주십시오.']"
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
                    v-model="edited.empEmail"
                    label="이메일* "
                    lazy-rules
                    :rules="[ val => val && val.length > 0 || '이메일을 입력 해 주십시오.']"
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
                    emit-value
                    map-options
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
                    emit-value
                    map-options
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
          </q-form>
          <q-card-actions align="right">
            <q-btn v-if="edited.empUuid != 0" label="정보수정" @click="onClickSave" color="primary"/>
            <q-btn v-if="edited.empUuid == 0" label="사용자등록" @click="onClickSave" color="primary"/>
            <q-btn label="초기화" color="primary" flat class="q-ml-sm" @click="resetForm"/>
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
import { EmpFormTableConfig } from "src/views/emp/EmpSimpleFormTableConfig";
import { initialData } from "src/views/emp/EmpData";

//const initialData = EmpData;

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
    getCommonCode(upCode, codeLvl, propName) {
      const req = {
        uprCodeUuid: upCode,
        codeLvl: codeLvl
      };
      this.$store.dispatch("comCode/useComCode", req)
          .then(
              (commCode) => {
                this[propName] = commCode;
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
    getValueName(param, dataset){

      const foundItem = dataset.find(item => item.codeValue === param);
      return foundItem ? foundItem.codeKr : '';
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

      console.log(this.edited);
      if (this.edited.empUuid != 0) {
        this.$store.dispatch("empTn/updateEmp", this.edited).then(
            (response) => {
              alert(response.data.message);
              this.resetForm();
              this.showForm=false;
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
                  alert(response.data.message);
                  this.resetForm();
                  this.showForm=false;
                  this.handlePageChange();
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
      params.data.password = "";
      this.updateEdited = Object.assign({}, params.data);
      this.edited = params.data;
      // }

    },
    onClickAdd(){
      this.edited = Object.assign({}, this.initEdited);
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
    this.getCommonCode('19', '1', 'divOptions');
    //직위
    this.getCommonCode('17', '1', 'titleOptions');
    //국가코드
    this.getCommonCode('1', '1', 'countryOptions');
    //생일타입
    this.getCommonCode('222', '1', 'dobTypeOptions');
    //직책
    this.getCommonCode('18', '1', 'empRoleOptions');
    //근무형태
    this.getCommonCode('16', '1', 'workOptions');
    //재직상태
    this.getCommonCode('15', '1', 'empStatusOptions');
  },

};
</script>

<style scoped>

</style>
