<template>
  <q-checkbox v-model="inputValue" :disable="disable" :label="label">
    <slot name="label" />
  </q-checkbox>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'CheckboxComp',
  props: {
    disable: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: null,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const inputValue = ref<boolean>(false);

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue
    );

    return {
      inputValue,
    };
  },
});
</script>

<style lang="scss"></style>
