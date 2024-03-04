<template>
  <div class="q-pa-md">

      <q-form @submit="handleRegister"
              @reset="onReset">
<!--        <div v-if="successful">-->
          <div class="row q-col-gutter-x-md" style="max-width: 900px">
            <q-input class="col-6" outlined
                     v-model="username"
                     type="text"
                     label="사용자명(username) *"
                     lazy-rules
                     :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>

            <q-input class="col-6" outlined
                     v-model="password"
                     type="password"
                     label="암호(password) *"
                     lazy-rules
                     :rules="[  val => val !== null && val !== '' || '암호를 입력 해 주십시오.']"/>

            <q-input class="col-6" outlined v-model="empKor"
                     type="text"
                     label="이름(한글이름) *"
                     lazy-rules
                     :rules="[ val => val && val.length > 0 || '한글이름 입력 해 주십시오.']"/>

            <q-input class="col-6" outlined v-model="empEmail"
                     type="email"
                     label="이메일(Email) *"
                     lazy-rules
                     :rules="[ val => val && val.length > 0 || '이메일을 입력 해 주십시오.']"/>

            <q-input class="col-6" outlined  v-model="empEng"
                     type="text"
                     label="이름(영문명) "/>

            <q-select class="col-3" outlined  bottom-slots
                      v-model="empDobType"
                      :options="dobTypeOptions"
                      option-value="codeValue"
                      option-label="codeKr"
                      label="생일타입" />

            <q-input class="col-3" outlined v-model="empDob" mask="####/##/##" :rules="['date']">
              <template v-slot:append>
                <q-icon name="event" class="cursor-pointer">
                  <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                    <q-date v-model="empDob" minimal>
                      <div class="row items-center justify-end">
                        <q-btn v-close-popup label="Close" color="primary" flat />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>

            <q-select class="col-3" outlined bottom-slots
                      v-model="empWorkType"
                      :options="workOptions"
                      option-value="codeValue"
                      option-label="codeKr"
                      label="근무형태" />

            <q-select class="col-3" outlined bottom-slots
                      v-model="empDiv"
                      :options="divOptions"
                      option-value="codeValue"
                      option-label="codeKr"
                      label="부서명" />

            <q-select class="col-3" outlined bottom-slots
                       v-model="empTitle"
                       :options="titleOptions"
                       option-value="codeValue"
                       option-label="codeKr"
                       label="직위" />

            <q-select class="col-3" outlined bottom-slots
                       v-model="empRole"
                       :options="empRoleOptions"
                       option-value="codeValue"
                       option-label="codeKr"
                       label="직책" />

            <q-input class="col-4" outlined v-model="empPhone"
                     type="text"
                     label="핸드폰(Mobile) *"
                     lazy-rules
                     :rules="[ val => val && val.length > 0 || '핸드폰을 입력 해 주십시오.']"/>

            <q-input class="col-4" outlined v-model="empWorkPhone"
                     type="text"
                     label="내선번호(Work Phone) "/>

            <q-input class="col-4" outlined v-model="empEmailBook"
                     type="email"
                     label="예약이메일(Email) "/>

            <q-input class="col-2" outlined v-model="empZip"
                     type="text"
                     label="우편번호(Zip) "/>

            <q-input class="col-5" outlined v-model="empAddress1"
                     type="text"
                     label="주소1(Address1) "/>

            <q-input class="col-5" outlined  bottom-slots v-model="empAddress2"
                     type="text"
                     label="주소1(Address2) "/>

            <q-input class="col-4" outlined  bottom-slots v-model="empCity"
                     type="text"
                     label="도시(City) "/>

            <q-input class="col-4" outlined  bottom-slots v-model="empState"
                     type="text"
                     label="주/도(State) "/>

<!--            <q-input class="col-4" outlined  bottom-slots v-model="empCountry"-->
<!--                     type="text"-->
<!--                     label="국가(Country) "/>-->

            <q-select class="col-4" outlined  bottom-slots
                      v-model="empCountry"
                      :options="countryOptions"
                      option-value="codeValue"
                      option-label="codeKr"
                      label="국가(Country)" />

<!--            <q-input class="col-12" v-model="empMemo"-->
<!--                     type="text"-->
<!--                     label="메모(password) "-->
<!--                     bottom-slots-->
<!--                     />-->
            <div class="col-12 q-pl-md q-py-md">
            <q-editor v-model="empMemo"
                      bottom-slots
                      label="메모(password) "
                      :toolbar="[
                      [
                        {
                          label: $q.lang.editor.align,
                          icon: $q.iconSet.editor.align,
                          fixedLabel: true,
                          list: 'only-icons',
                          options: ['left', 'center', 'right', 'justify']
                        },
                      ],
                      ['bold', 'italic', 'strike', 'underline', 'subscript', 'superscript'],
                      ['token', 'hr', 'link', 'custom_btn'],
                      ['print', 'fullscreen'],
                      [
                        {
                          label: $q.lang.editor.formatting,
                          icon: $q.iconSet.editor.formatting,
                          list: 'no-icons',
                          options: [
                            'p',
                            'h1',
                            'h2',
                            'h3',
                            'h4',
                            'h5',
                            'h6',
                            'code'
                          ]
                        },
                        {
                          label: $q.lang.editor.fontSize,
                          icon: $q.iconSet.editor.fontSize,
                          fixedLabel: true,
                          fixedIcon: true,
                          list: 'no-icons',
                          options: [
                            'size-1',
                            'size-2',
                            'size-3',
                            'size-4',
                            'size-5',
                            'size-6',
                            'size-7'
                          ]
                        },
                        {
                          label: $q.lang.editor.defaultFont,
                          icon: $q.iconSet.editor.font,
                          fixedIcon: true,
                          list: 'no-icons',
                          options: [
                            'default_font',
                            'arial',
                            'arial_black',
                            'comic_sans',
                            'courier_new',
                            'impact',
                            'lucida_grande',
                            'times_new_roman',
                            'verdana'
                          ]
                        },
                        'removeFormat'
                      ],
                      ['quote', 'unordered', 'ordered', 'outdent', 'indent'],
                      ['undo', 'redo'],
                      ['viewsource']
                    ]"
                    :fonts="{
                      arial: 'Arial',
                      arial_black: 'Arial Black',
                      comic_sans: 'Comic Sans MS',
                      courier_new: 'Courier New',
                      impact: 'Impact',
                      lucida_grande: 'Lucida Grande',
                      times_new_roman: 'Times New Roman',
                      verdana: 'Verdana'
                      }"
            />
            </div>
            <div class="q-pl-md">
            <q-btn :disabled="loading" label="사용자등록" type="submit" color="primary"/>
            <q-btn label="초기화" type="reset" color="primary" flat class="q-ml-sm" />
            </div>
          </div>
<!--        </div>-->
        <div
          v-if="message"
         class="alert"
          :class="successful ? 'alert-success' : 'alert-danger'"
        >
          {{ message }}
        </div>
      </q-form>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "RegisterTN",

  setup () {
    const viewday = new Date();

    return {
      model: ref(null),
      date: ref(viewday)
    }
  },
  data() {
    return {
      successful: false,
      loading: false,
      message: "",
      username: "",
      password: "",
      empEmail: "",
      empKor: "",
      empEng: "",
      empWorkType: "",
      empDiv: "",
      empRole: "",
      empTitle: "",
      empPhone: "",
      empWorkPhone: "",
      empEmailBook: "",
      empAddress1: "",
      empAddress2: "",
      empCity: "",
      empState: "",
      empCountry: "",
      empZip: "",
      empDob: "",
      empDobType: "",
      empMemo: "",
      empStatus: "",
      workOptions: [],
      divOptions: [],
      titleOptions: [],
      empRoleOptions: [],
      dobTypeOptions: [],
      countryOptions: []
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (!this.loggedIn) {
      this.$router.push("/login");
    }
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
    getCurrentDate () {
      var viewday = new Date();
      var dd = String(viewday.getDate()).padStart(2, '0');
      var mm = String(viewday.getMonth() + 1).padStart(2, '0');
      var yyyy = viewday.getFullYear()-20;

      viewday = yyyy+'/' +mm + '/' + dd + '/';

      this.empDob = viewday

    },
    handleRegister() {
      this.message = "";
      this.successful = false;
      this.loading = true;
      const user = {
        username: this.username,
        empEmail: this.empEmail,
        password: this.password,
        empKor: this.empKor,
        empEng: this.empEng,
        empWorkType: this.empWorkType.value,
        empDiv: this.empDiv.value,
        empTitle: this.empTitle.value,
        empPhone: this.empPhone,
        empWorkPhone: this.empWorkPhone,
        empEmailBook: this.empEmailBook,
        empAddress1: this.empAddress1,
        empAddress2: this.empAddress2,
        empCity: this.empCity,
        empCountry: this.empCountry.value,
        empState: this.empState,
        empZip: this.empZip,
        empDob: this.empDob,
        empDobType: this.empDobType.value,
        empMemo: this.empMemo
      };

      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
        }
      );
    },

    onReset () {
      this.username = ""
      this.password = ""
    },
  },
  beforeMount(){
    this.getCurrentDate()
  },
};
</script>

<style scoped>

</style>
