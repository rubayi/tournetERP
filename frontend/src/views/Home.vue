<template>
  <q-page class="flex flex-center">
    <!-- <div class="home-text">
      <div class="q-my-none text-center">
        TOURNET ERP
      </div>
      <hr class="full-width" />
      <div class="q-my-none text-center home-subtext">
        Tournet Hawaii, Department of IT
      </div>
    </div> -->
    <div class="logo-container">
      <img alt="Tournet Hawaii" src="img/mainlogo.png" />
    </div>
  </q-page>
</template>

<script>
import { defineComponent } from 'vue';
import router from 'src/router';

export default defineComponent({
  name: 'LandingPage',
  setup() {
    setTimeout(() => router.push({ path: '/comcode' }), 3000);
  },
});
</script>

<style lang="scss">
.logo-container {
  transform: scale(0.9);
  padding-right: 10px;
}

.home-text {
  font-size: 24px;
  font-family: Raleway;
  font-weight: bold;
}
.home-subtext {
  font-weight: normal;
}
@media only screen and (max-width: 1023px) {
  .logo-container {
    padding-right: 0;
    transform: scale(0.8);
  }
  .home-text {
    font-size: 20px;
  }
}
</style>
