<template>
  <q-page id="login-page" class="flex flex-center">
    <login-card></login-card>
  </q-page>
</template>

<script type="ts">
import LoginCard from "src/components/login/LoginCard.vue";

export default {
  name: "LoginTN",
  components: { LoginCard },
};
</script>

<style lang="scss">
#login-page {
  background-image: linear-gradient(to bottom left, #4fc3f7, #1a237e);
}
</style>
