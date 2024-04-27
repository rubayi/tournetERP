<template>
  <div class="custom-tooltip q-pa-sm">
    <span>{{ content }}</span>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import FormatHelper from "src/utils/helpers/FormatHelper";

export default defineComponent({
  name: "TooltipComponent",
  data(): {
    params: any;
    content: any;
  } {
    return {
      params: {},
      content: null,
    };
  },
  beforeMount() {
    let tooltipField = this.params.toolTipContentKey;
    this.content = this.params.api.getDisplayedRowAtIndex(
      this.params.rowIndex
    ).data[tooltipField];
    this.content = FormatHelper.trimStringAndAddElipsis(this.content, 2000);
  },
});
</script>

<style lang="scss">
.custom-tooltip {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  background-color: white;
  border-color: var(--q-dark);
  border-style: solid;
  border-width: 2px;
  position: absolute;
  max-width: 500px;
  word-wrap: break-word;
  pointer-events: none;
  transition: opacity 1s;
  box-shadow: 3px 3px 3px var(--q-dark);
}

.custom-tooltip.ag-tooltip-hiding {
  opacity: 0;
}
</style>
