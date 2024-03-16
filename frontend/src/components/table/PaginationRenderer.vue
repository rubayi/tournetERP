<template>
  <div v-if="lcPageData">
  <q-pagination
      v-model="lcPageData"
      :max="lcMax"
      direction-links
  />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

const columnDefs = ref([]);
const rowData = ref([]);
export default defineComponent({
  components: {
  },
  props: {
    pageVal: Number,
    max: Number,
  },
  setup(props, { emit }) {
    const lcPageData = ref(props.pageVal || 0);
    const lcMax = ref(props.max || 0);
    return {
      lcPageData,
      lcMax
    };
  },
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
