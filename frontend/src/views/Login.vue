<template>
  <q-page id="login-page" class="flex flex-center">
    <login-card></login-card>
  </q-page>
</template>

<script type="ts">
import LoginCard from "@/components/login/LoginCard.vue";

export default {
  name: "Login",
  components: { LoginCard },
};
</script>

<style lang="scss">
#login-page {
  background-image: linear-gradient(to bottom left, #4fc3f7, #1a237e);
}
</style>
