<template>
  <div class="q-pa-md">

      <q-form @submit="handleRegister"
              @reset="onReset">

        <div class="q-gutter-md" style="max-width: 400px">
        <div v-if="!successful">
          <q-input
                   v-model="username"
                   type="text"
                   label="사용자명(username) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>
          <q-input
                   v-model="empEmail"
                   type="text"
                   label="이메일(Email) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>

          <q-input
                   v-model="password"
                   type="password"
                   label="암호(password) *"
                   lazy-rules
                   :rules="[  val => val !== null && val !== '' || '암호를 입력 해 주십시오.']"/>

          <q-input
                   v-model="empKor"
                   type="text"
                   label="이름(한글이름) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '한글이름 입력 해 주십시오.']"/>

          <q-input
                   v-model="empEng"
                   type="text"
                   label="이름(영문명) "/>

          <q-select
                    bottom-slots
                    v-model="empWorkType"
                    :options="workOptions"
                    label="근무형태" />

          <q-select
                    bottom-slots
                    v-model="empDiv"
                    :options="titleOptions"
                    label="부서명" />

          <q-select  bottom-slots v-model="empTitle" :options="titleOptions" label="직위" />

          <q-select  bottom-slots v-model="empRole" :options="divOptions" label="직책" />

          <q-input
                   v-model="empPhone"
                   type="text"
                   label="핸드폰(Mobile) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '핸드폰을 입력 해 주십시오.']"/>
          <q-input
                   v-model="empWorkPhone"
                   type="text"
                   label="내선번호(Work Phone) "/>
          <q-input
                   v-model="empEmailBook"
                   type="text"
                   label="예약이메일(Email) "/>
          <q-input
                   v-model="empAddress1"
                   type="text"
                   label="주소1(Address1) "/>
          <q-input
                   v-model="empAddress2"
                   type="text"
                   label="주소1(Address2) "/>
          <q-input
                   v-model="empCity"
                   type="text"
                   label="도시(City) "/>
          <q-input
                   v-model="empState"
                   type="text"
                   label="주/도(State) "/>
          <q-input
                   v-model="empZip"
                   type="text"
                   label="우편번호(Zip) "/>

          <q-input v-model="empDob" mask="date" :rules="['date']">
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

          <q-select  bottom-slots v-model="empDobType" :options="dobTypeOptions" label="생일타입" />

          <q-input
                   v-model="empMemo"
                   type="text"
                   label="메모(password) "
                   bottom-slots
                   />


          <q-btn :disabled="loading" label="사용자등록" type="submit" color="primary"/>
          <q-btn label="초기화" type="reset" color="primary" flat class="q-ml-sm" />

          </div>
        </div>

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
    const today = new Date();
    return {
      model: ref(null),
      workOptions: [
        '풀타임', '파트타임'
      ],
      divOptions: [
        'Google', 'Facebook', 'Twitter', 'Apple', 'Oracle'
      ],
      titleOptions: [
        'Google', 'Facebook', 'Twitter', 'Apple', 'Oracle'
      ],
      empRoleOptions: [
        'Google', 'Facebook', 'Twitter', 'Apple', 'Oracle'
      ],
      dobTypeOptions: [
        '양력', '음력'
      ],
      date: ref(today)
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
      empZip: "",
      empDob: "",
      empDobType: "",
      empMemo: "",
      empStatus: ""

    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister() {
      this.message = "";
      this.successful = false;
      this.loading = true;
      const user = {
        username: this.username,
        empEmail: this.empEmail,
        password: this.password,
        empKor: "",
        empEng: "",
        empWorkType: "",
        empDiv: "",
        empTitle: "",
        empPhone: "",
        empWorkPhone: "",
        empEmailBook: "",
        empAddress1: "",
        empAddress2: "",
        empCity: "",
        empState: "",
        empZip: "",
        empDob: "",
        empDobType: "",
        empMemo: "",
        empStatus: ""
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
    }
  },
};
</script>

<style scoped>

</style>
