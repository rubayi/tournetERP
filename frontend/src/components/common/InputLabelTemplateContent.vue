<template>
  <div v-if="label" class="row all-pointer-events">
    <q-icon
      v-if="prependIconName"
      class="q-ml-xs"
      :name="prependIconName"
      :size="prependIconName"
    ></q-icon>
    {{ label }}
    <q-icon
      v-if="appendIconName"
      class="q-ml-xs"
      :name="appendIconName"
      :size="appendIconSize"
    ></q-icon>
    <q-tooltip
      v-if="tooltipText"
      :anchor="tooltipAnchor"
      :offset="tooltipOffset"
      :self="tooltipSelf"
    >
      <div class="text-caption">
        {{ tooltipText }}
      </div>
    </q-tooltip>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "InputLabelTemplateContent",
  props: {
    label: {
      type: String,
      default: null,
    },
    prependIconName: {
      type: String,
      default: null,
    },
    prependIconSize: {
      type: String,
      default: "20px",
    },
    appendIconName: {
      type: String,
      default: null,
    },
    appendIconSize: {
      type: String,
      default: "20px",
    },
    tooltipText: {
      type: String,
      default: null,
    },
    tooltipAnchor: {
      type: String,
      default: "top left",
    },
    tooltipOffset: {
      type: Array,
      default: () => [5, 5],
    },
    tooltipSelf: {
      type: String,
      default: "bottom left",
    },
    scale: {
      type: Number,
      default: 0,
    },
    scaleStrict: {
      type: Boolean,
      default: false,
    },
    validationMessage: {
      type: String,
      default: "Number is Invalid",
    },
  },
  emits: ["update:modelValue"],
});
</script>

<style lang="scss"></style>
