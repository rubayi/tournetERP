<template>
  <ag-grid-vue
    class="ag-theme-alpine grid"
    :columnDefs="columnDefs"
    :rowData="rowData"
    style="width:100%; height: 500px"
  />
</template>

<script lang="ts">
import { AgGridVue } from "ag-grid-vue3";
import { defineComponent, ref } from "vue";

const columnDefs = ref([]);
const rowData = ref([]);
export default defineComponent({
  name: "TableComp",
  components: {
    AgGridVue
  },
  props: {
    columnDefs: Array,
    rowData: Array
  },
});
</script>

<style lang="scss">
.ag-header-cell {
  background-image: url("src/assets/top_main.png");
  color: #fff;
  color: var(--ag-background-color, #0c2c8b);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
