<template>
  <span
    v-if="value != ''"
    class="hwy-checkbox text-bold text-green text-h7 q-pa-md"
  >
    ✓
  </span>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ActionIconCellRenderer",
  data(): { params: any } {
    return {
      params: {},
    };
  },
  computed: {
    value(): boolean {
      return this.params.value;
    },
  },
  methods: {
    openClickHandler(): void {
      this.params.context.componentParent.openAction(this.params.value);
    },
  },
});
</script>

<style scoped lang="scss"></style>
