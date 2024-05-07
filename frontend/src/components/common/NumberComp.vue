<template>
  <input-comp
    v-model="inputValue"
    clearable
    :disable="disable"
    :label="label"
    lazy-rules="ondemand"
    type="number"
    :validation-message="validationMessage"
    :validator="validator"
    @blur="validate"
    @update:model-value="inputValueChange"
    ref="numberInput"
  >
    <template v-if="$slots.prepend" #prepend>
      <slot name="prepend" />
    </template>
    <template v-if="$slots.append" #append>
      <slot name="append" />
    </template>
    <template v-if="$slots.label" #label>
      <slot name="label" />
    </template>
  </input-comp>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useValidateInputs } from 'src/utils/helpers/useValidateInputs';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

import InputComp from 'src/components/common/InputComp.vue';
import NumberHelper from 'src/utils/helpers/NumberHelper';
import FormatHelper from 'src/utils/helpers/FormatHelper';

export default defineComponent({
  name: 'NumberComp',
  components: { InputComp },
  props: {
    modelValue: {
      type: Number,
      default: null,
    },
    label: {
      type: String,
      default: null,
    },
    minNumber: {
      type: Number,
      default: null,
    },
    maxNumber: {
      type: Number,
      default: 2147483647, // Max Integer
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
      default: 'Number is Invalid',
    },
    required: {
      type: Boolean,
      default: false,
    },
    disable: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const inputValue = ref<string>('');
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue,
      FormatHelper.convertToString,
      FormatHelper.convertToNumber
    );

    let timeout = 0;
    function inputValueChange(newVal: string) {
      inputValue.value = newVal;
      // Trigger scaling of inputValue
      if (timeout) {
        clearTimeout(timeout);
      }
      // Timer to pause for user input
      timeout = window.setTimeout(() => {
        inputValue.value = FormatHelper.convertToScale(
          newVal,
          props.scale,
          props.scaleStrict
        );
      }, 500);
    }

    const numberInput = ref();
    const { inputHasError, validate, resetValidation, hasError } =
      useValidateInputs([numberInput]);

    const validator = (input: string): boolean => {
      const number: number | null = parseFloat(input);
      return (
        NumberHelper.isInNumberRange(
          number,
          props.minNumber,
          props.maxNumber
        ) && inputIsRequiredAndEntered(input)
      );
    };

    function inputIsRequiredAndEntered(input: string): boolean {
      return props.required ? !!input : true;
    }
    return {
      inputValue,
      numberInput,
      inputValueChange,
      validate,
      hasError,
      resetValidation,
      inputHasError,
      validator,
    };
  },
});
</script>

<style lang="scss"></style>
