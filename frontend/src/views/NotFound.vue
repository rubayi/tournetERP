<template>
  <div id="not-found">
    <q-page class="q-pa-md column flex-center text-center">
      <div class="row">
        <div class="col-12">
          <q-icon name="sentiment_very_dissatisfied" size="12rem"></q-icon>
          <div class="text-h1 text-bold">404 Page Not Found</div>
          <div class="text-subtitle1 q-mt-md">
            We are sorry, but the page you requested or are looking for cannot
            be found.
            <br />
            Please go back to the <a href="">previous page</a>, visit our
            <a href="">homepage</a>, or contact IT Staff.
          </div>
        </div>
      </div>
    </q-page>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped></style>
