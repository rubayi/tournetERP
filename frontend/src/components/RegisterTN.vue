<template>
  <div class="q-pa-md" style="max-width: 400px">

      <q-form @submit="handleRegister"
              @reset="onReset">
        <div v-if="!successful">
          <q-input filled
                   v-model="username"
                   type="text"
                   class="form-control"
                   label="사용자명(username) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>

          <q-input filled
                   v-model="empEmail"
                   type="text"
                   class="form-control"
                   label="이메일(Email) *"
                   lazy-rules
                   :rules="[ val => val && val.length > 0 || '사용자명을 입력 해 주십시오.']"/>
          <q-input filled
                   v-model="password"
                   type="password"
                   class="form-control"
                   label="암호(password) *"
                   lazy-rules
                   :rules="[
                  val => val !== null && val !== '' || '암호를 입력 해 주십시오.',
                ]"/>

          <q-btn :disabled="loading" label="사용자등록" type="submit" color="primary"/>
          <q-btn label="초기화" type="reset" color="primary" flat class="q-ml-sm" />
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
export default {
  name: "RegisterTN",
  data() {
    return {
      successful: false,
      loading: false,
      message: "",
      username: "",
      password: "",
      empEmail: ""
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
        password: this.password
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
