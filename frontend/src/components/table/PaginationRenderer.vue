<template>
  <q-pagination
      vModel="page"
      :max="max"
      direction-links
  />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

const columnDefs = ref([]);
const rowData = ref([]);
export default defineComponent({
  components: {
  },
  props: {
    vModel: Array,
    max: Array,
  },
  mounted() {
    console.log('Received columnDefs:', this.columnDefs);
    console.log('Received rowData:', this.rowData);
  }

});
</script>

<style lang="scss">
.ag-header-cell {
  background-image: url("src/assets/top_main.png");
  color: #fff;
  color: var(--ag-background-color, #fff);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
