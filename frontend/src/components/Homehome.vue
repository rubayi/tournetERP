<template>
  <q-layout view="hHh lpR fFf">
    <header class="jumbotron">
      <h3>{{ content }}</h3>
    </header>
  </q-layout>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "Home-home",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    UserService.getPublicContent().then(
      (response) => {
        this.content = response.data;
      },
      (error) => {
        this.content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  },
};
</script>
