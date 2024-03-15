<template>
  <ag-grid-vue
    class="ag-theme-alpine grid"
    :columnDefs="columnDefs"
    :rowData="rowData"
    :getRowHeight="getRowHeight"
    :headerHeight="50"
    :onCellClicked="onCellClicked"
    style="width: 100%; height: 600px"
  />
</template>

<script lang="ts">
import { AgGridVue } from "ag-grid-vue3";
import { defineComponent } from "vue";

export default defineComponent({
  name: "TableComp",
  components: {
    AgGridVue,
  },
  props: {
    columnDefs: Array,
    rowData: Array,
    onCellClicked: Function,
  },
  setup() {
    const getRowHeight = () => {
      return 50;
    };

    return {
      getRowHeight,
    };
  },
});
</script>

<style lang="scss">
.ag-theme-alpine .ag-cell {
  display: flex;
  align-items: center;
  justify-content: center;
}
.ag-header-cell {
  background-image: url("src/assets/top_main.png");
  color: #fff;
  color: var(--ag-background-color, #0c2c8b);
}
.ag-header-cell-label {
  justify-content: center;
}
</style>
