<template>
  <input-comp
    v-model="inputValue"
    clearable
    :disable="disable"
    :label="label"
    lazy-rules="ondemand"
    :model-value="inputValue"
    :validation-message="validationMessage"
    :validator="validator"
    @input-blur="blurEvent"
    @update:model-value="inputValueChange"
    ref="currencyInput"
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
import { defineComponent, ref } from "vue";
import { useValidateInputs } from "@/utils/helpers/useValidateInputs";
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

import InputComp from "@/components/common/InputComp.vue";
import FormatHelper from "@/utils/helpers/FormatHelper";
import NumberHelper from "@/utils/helpers/NumberHelper";

export default defineComponent({
  name: "CurrencyComp",
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
    validationMessage: {
      type: String,
      default: "Number is Invalid",
    },
    disable: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    let timeout = 0;

    const inputValue = ref<string>("");
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      inputValue,
      FormatHelper.convertNumberToCurrency,
      FormatHelper.convertToNumber,
      true
    );

    function inputValueChange(newVal: string) {
      // Trigger scaling of inputValue
      if (timeout) {
        clearTimeout(timeout);
      }
      // Timer to pause for user input
      timeout = window.setTimeout(() => {
        inputValue.value = FormatHelper.convertStringToCurrency(newVal);
      }, 1000);
    }

    const currencyInput = ref();
    const { inputHasError, validate, resetValidation, hasError } =
      useValidateInputs([currencyInput]);

    const validator = (input: string): boolean => {
      const number: number | null = FormatHelper.convertToNumber(
        FormatHelper.convertToScale(input, 2, true)
      );
      return NumberHelper.isInNumberRange(
        number,
        props.minNumber,
        props.maxNumber
      );
    };

    function blurEvent() {
      validate();
      const updatedValue: number | null = FormatHelper.convertToNumber(
        FormatHelper.convertToScale(inputValue.value, 2, true)
      );
      if (props.modelValue !== updatedValue) {
        emit("update:modelValue", updatedValue);
      }
    }

    return {
      inputValue,
      inputValueChange,
      currencyInput,
      validate,
      resetValidation,
      hasError,
      inputHasError,
      validator,
      blurEvent,
    };
  },
});
</script>

<style lang="scss"></style>
