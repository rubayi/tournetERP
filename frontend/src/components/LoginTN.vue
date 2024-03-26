<template>
  <q-page id="login-page" class="flex flex-center">
    <div class="login-card">
      <card-comp>
        <template #header> </template>
        <template #content>
          <q-form @submit="handleLogin" @reset="onReset" class="row">
            <div class="col-6 col-md-6 col-xs-12">
              <div class="login-img">
                <img src="images/mainlogo.png" />
              </div>
            </div>
            <div class="col-6 col-md-6 col-xs-12">
              <q-card-section>
                <p class="login-version-text">
                  Version 1.0.0 Last Updated on March 17,2023
                </p>
                <p class="login-title">Tournet Hawaii ERP</p>
                <p class="login-title-sub">
                  * 사용자명과 암호를 입력해 주십시오.
                </p>
                <div class="q-mb-sm">
                  <q-input
                    filled
                    v-model="username"
                    type="text"
                    class="form-control"
                    label="사용자명(username) *"
                    lazy-rules
                    :rules="[
                      (val) =>
                        (val && val.length > 0) ||
                        '사용자명을 입력 해 주십시오.',
                    ]"
                  />
                </div>
                <div class="q-mb-lg">
                  <q-input
                    filled
                    v-model="password"
                    type="password"
                    class="form-control"
                    label="암호(password) *"
                    lazy-rules
                    :rules="[
                      (val) =>
                        (val !== null && val !== '') ||
                        '암호를 입력 해 주십시오.',
                    ]"
                  />
                </div>
                <div class="q-mb-sm">
                  <q-btn
                    :disabled="loading"
                    label="로그인"
                    type="submit"
                    id="login-button"
                  />
                  <q-btn
                    label="초기화"
                    type="reset"
                    color="primary"
                    flat
                    class="q-ml-sm"
                  />
                </div>

                <div>
                  <div v-if="message" class="alert alert-danger" role="alert">
                    {{ message }}
                  </div>
                </div>
              </q-card-section>

              <!-- <p class="login-footer-text">
                For View-Only user, <br />Please enter '<b>guest</b>' for both
                user name and password
              </p> -->
            </div>
          </q-form>
        </template>
      </card-comp>
    </div>
  </q-page>
</template>

<script>
import CardComp from "./common/CardComp.vue";

export default {
  name: "LoginTournet",
  components: {
    CardComp,
  },
  data() {
    return {
      loading: false,
      message: "",
      username: "",
      password: "",
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
      const tokenExpiration = this.$store.state.auth.tokenExpiration;
      if (tokenExpiration && Date.now() >= tokenExpiration * 1000) {
        // Token is expired, prompt user to log in again
        this.message = "Your session has expired. Please log in again.";
        return;
      }

      this.loading = true;
      this.message = "";
      const user = {
        username: this.username,
        password: this.password,
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
    onReset() {
      this.username = "";
      this.password = "";
    },
  },
};
</script>

<style scoped>
#login-page {
  background-image: linear-gradient(to bottom left, #4fc3f7, #1a237e);
}
.login-card {
  width: 820px;
  .card-comp {
    .q-card {
      padding-left: 0px;
      padding-bottom: 20px;
      padding-right: 35px;
      padding-top: 15px;
    }
  }
  #login-header-text-container {
    color: #145ba4;
    font-size: 16px;
    font-weight: 450;
    hr {
      margin: 2px 0;
      height: 1px;
      background: #d5d5d5;
      border: none;
    }
  }
  .login-title {
    font-size: 22px;
    color: #000;
    font-weight: normal;
    font-family: Raleway;
    font-weight: bold;
    margin-bottom: 23px;
  }
  .login-version-text {
    font-size: 0.7rem;
    color: #919aa3;
    text-align: right;
    margin-bottom: 1rem;
  }
  .login-title-sub {
    font-size: 0.8rem;
    color: #919aa3;
    margin-bottom: 1rem;
  }
  .login-footer-text {
    font-size: 0.9rem;
    color: #919aa3;
    margin-bottom: 1.5rem;
  }
  .login-img {
    text-align: center;
    padding-top: 4rem;
  }
  #login-button {
    color: white;
    background: #000000;
  }
}
</style>
