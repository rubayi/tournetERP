<template>
  <div class="login-card">
    <card-comp>
      <template #header> </template>
      <template #content>
        <div class="row">
          <div class="col-6 col-md-6 col-xs-12">
            <div class="login-img">
              <img src="img/mainlogo.png" />
            </div>
          </div>
          <div class="col-6 col-md-6 col-xs-12">
            <q-card-section>
              <p class="login-version-text">
                Version 1.0.0 Last Updated on January 14,2024
              </p>
              <p class="login-title">TOURNET ERP</p>
              <p class="login-title-sub">
                * Please Type User name and Password
              </p>
              <div class="q-mb-sm">
                <input-comp
                  v-model="editLogin.username"
                  autofocus
                  label="Username"
                  validation-message="Required"
                  :validator="requiredValidator"
                />
              </div>
              <div class="q-mb-lg">
                <input-comp
                  v-model="editLogin.password"
                  filled
                  label="Password"
                  :type="passwordType"
                  validation-message="Required"
                  :validator="requiredValidator"
                  @keyup.enter="login()"
                >
                  <template #append>
                    <q-icon
                      class="cursor-pointer q-mr-sm"
                      :name="passwordVisibilityIcon"
                      @click="isPassword = !isPassword"
                    ></q-icon>
                  </template>
                </input-comp>
              </div>
              <div class="q-mb-sm">
                <q-btn
                  id="login-button"
                  class="full-width"
                  label="Sign On"
                  :loading="isLoading"
                  @click="login()"
                ></q-btn>
              </div>
            </q-card-section>
<!--            <p class="login-footer-text">-->
<!--              For View-Only user, <br />Please enter '<b>guest</b>' for both-->
<!--              user name and password-->
<!--            </p>-->
          </div>
        </div>
      </template>
    </card-comp>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed, ref } from "vue";
import router from "src/router";

import CardComp from "src/components/common/CardComp.vue";
import InputComp from "src/components/common/InputComp.vue";

import { LoginService } from "src/services/LoginService";
import { requiredValidator } from "src/utils/helpers/InputValidatorHelper";
import { Login } from "src/types/Login";
import { notificationHelper } from "src/utils/helpers/NotificationHelper";

export default defineComponent({
  name: "LoginCard",
  components: {
    CardComp,
    InputComp,
  },
  emits: ["update:modelValue"],
  setup() {
    const editLogin = ref<Login>({
      username: "",
      password: "",
    });
    const isPassword = ref<boolean>(true);
    const isLoading = ref<boolean>(false);

    const passwordType = computed(() => {
      return isPassword.value ? "password" : "text";
    });

    const passwordVisibilityIcon = computed(() => {
      return isPassword.value ? "visibility_off" : "visibility";
    });

    function login() {
      isLoading.value = true;
      LoginService.login(editLogin.value)
        .then(() => {
          setTimeout(() => {
            notificationHelper.dismiss();
            isLoading.value = false;
            router.push("/");
          }, 1500);
        })
        .catch((error) => {
          notificationHelper.dismissOngoingNotification();
          isLoading.value = false;

          notificationHelper.createErrorNotification(
            notificationHelper.formatResponseToErrorMessage(error.response)
          );
        });
    }

    return {
      editLogin,
      isPassword,
      passwordType,
      passwordVisibilityIcon,
      requiredValidator,
      isLoading,
      login,
    };
  },
});
</script>

<style lang="scss">
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
