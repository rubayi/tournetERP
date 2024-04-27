<template>
  <q-tabs
    active-color="primary"
    :align="align"
    class="bg-grey-3 text-black"
    indicator-color="primary"
    :model-value="value"
    narrow-indicator
    @update:modelValue="$emit('update:modelValue', $event)"
  >
    <q-tab
      v-for="tabOption in tabOptions"
      v-show="tabOption.show"
      :alert="tabOption.alert"
      :alert-icon="tabOption.alertIcon"
      :label="tabOption.label"
      :name="tabOption.name"
      :key="tabOption.name"
    />
  </q-tabs>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { TabOptions } from "src/types/TabOptions";
export default defineComponent({
  name: "TabsComp",
  props: {
    value: {
      type: String,
      default: "",
    },
    align: {
      type: String,
      default: "left",
    },
    tabOptions: {
      type: Object as () => Array<TabOptions>,
      default: Object as () => [],
    },
  },
  emits: ["update:modelValue"],
});
</script>

<style scoped></style>
