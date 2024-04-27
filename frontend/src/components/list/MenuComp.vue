<template>
  <q-menu
    :anchor="anchor"
    :self="self"
    :value="value"
    @input="$emit('input', $event)"
    @mouseout="$emit('mouseout')"
    @mouseover="$emit('mouseover')"
  >
    <slot />
  </q-menu>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "MenuComp",
  props: {
    value: {
      type: Boolean,
    },
    anchor: {
      type: String,
      default: "top right",
    },
    self: {
      type: String,
      default: "top left",
    },
  },
  emits: ["input", "mouseout", "mouseover"],
});
</script>

<style scoped></style>
