<template>
  <q-img
    class="absolute-bottom"
    src="https://cdn.quasar.dev/img/material.png"
    style="height: 100px"
  >
    <div class="absolute-bottom bg-transparent"></div>
  </q-img>

  <div id="user-info">
    <div class="full-width q-pa-sm">
      <q-btn
        class="full-width logout-button"
        :loading="isLoading"
        @click="logout"
      >
        <div class="row items-center text-left full-width">
          <q-icon left name="logout"></q-icon>
          <div class="text-left">
            <div class="text-weight-bold">
              {{ currentUser ? currentUser.username : "(Not Logged In)" }}
            </div>
            <div class="logout-text">Logout</div>
          </div>
        </div>
      </q-btn>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import router from "src/router";
import store from "src/store";
import { notificationHelper } from "src/utils/helpers/NotificationHelper";
import { LoginService } from "src/services/LoginService";

export default defineComponent({
  name: "UserInfo",
  setup() {
    const isLoading = ref<boolean>(false);
    const currentUser = computed(() => store.state.currentUser);
    currentUser.value.authorities.sort();
    function logout() {
      isLoading.value = true;
      notificationHelper.createOngoingNotification("Logging out...");

      LoginService.logout()
        .then(() => {
          setTimeout(() => {
            notificationHelper.dismiss();
            isLoading.value = false;

            store.commit("clearCurrentUser");
            notificationHelper.createSuccessNotification("Logout successful");
            router.push("/login");
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
      isLoading,
      currentUser: computed(() => store.state.currentUser),
      logout,
    };
  },
});
</script>

<style type="text/scss" lang="scss">
#user-info > * {
  color: var(--dark-cornflower-blue);
  .logout-button {
    background-color: white !important;
  }
  .logout-text {
    margin-top: -5px;
    font-size: 12px;
  }
}
</style>
