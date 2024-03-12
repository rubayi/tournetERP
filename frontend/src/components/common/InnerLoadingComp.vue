<template>
  <q-inner-loading id="loading-comp" :showing="loading">
    <q-spinner-oval color="primary" size="50px" />
  </q-inner-loading>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "InnerLoadingComp",
  props: {
    loading: {
      type: Boolean,
      default: false,
    },
  },
});
</script>

<style lang="scss">
#loading-comp {
  background: rgba(255, 255, 255, 1);
  // This ensures that the loading overlay is above the ag grid header (z-index: 1)
  z-index: 2;
}
</style>
