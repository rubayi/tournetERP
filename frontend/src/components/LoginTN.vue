<template>
  <div class="q-pa-md" style="max-width: 400px">

      <q-form
        @submit="handleLogin"
        @reset="onReset"
        class="q-gutter-md"
      >
        <q-input filled
                 v-model="username"
                 type="text"
                 class="form-control"
                 label="사용자명(username) *"
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

        <div>
          <q-btn :disabled="loading" label="로그인" type="submit" color="primary"/>
          <q-btn label="초기화" type="reset" color="primary" flat class="q-ml-sm" />
        </div>

        <div>
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div>
      </q-form>
  </div>
</template>

<script>
export default {
  name: "LoginTournet",
  data() {
    return {
      loading: false,
      message: "",
      username: "",
      password: ""
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleLogin() {
      this.loading = true
      this.message = ""
      const user = {
        username: this.username,
        password: this.password
      };
      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/profile");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
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
