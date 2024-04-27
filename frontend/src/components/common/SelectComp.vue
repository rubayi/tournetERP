<template>
  <q-select
    :clearable="clearable"
    dense
    :disable="disabled"
    :label="label"
    :model-value="inputValue"
    :options="options"
    :rules="inputRules"
    stack-label
    :style="maxInputWidth"
    @update:model-value="inputValueChange"
    ref="input"
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
    <template v-if="$slots.hint" #hint>
      <slot name="hint" />
    </template>
  </q-select>
</template>

<script lang="ts">
import _ from "lodash";
import { computed, defineComponent, ref, watch } from "vue";
import { ISelectOption, SelectOption } from "src/types/SelectOption";

export default defineComponent({
  name: "SelectComp",
  props: {
    clearable: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: undefined,
    },
    maxLength: {
      type: Number,
      default: 255,
    },
    maxWidth: {
      type: Number,
      default: null,
    },
    modelValue: {
      type: [String, Number, Object],
      default: () => null,
    },
    useInput: {
      type: Boolean,
      default: false,
    },
    validator: {
      type: Function,
      default: () => true,
    },
    validationMessage: {
      type: String,
      default: "",
    },
    valueUniqueIdentifier: {
      type: String,
      default: null,
    },
    options: {
      type: Array as () => ISelectOption[],
      required: true,
    },
  },
  emits: ["update:modelValue", "change"],
  setup(props, { emit }) {
    const inputValue = ref<ISelectOption | null>();
    setInputValue(props.modelValue);

    /* eslint-disable @typescript-eslint/explicit-module-boundary-types */
    function setInputValue(value: any) {
      let matchingOption = null;
      if (value !== null && value !== undefined) {
        if (props.valueUniqueIdentifier === null) {
          matchingOption = props.options.find((o) => _.isEqual(o.value, value));
        } else {
          matchingOption = props.options.find((o) =>
            _.isEqual(o.value[props.valueUniqueIdentifier as keyof typeof o.value], value)
          );
        }
        if (matchingOption === undefined) {
          matchingOption = new SelectOption(value, value);
        }
      }
      inputValue.value = matchingOption;
    }

    const inputRules = computed(() =>
      props.validationMessage
        ? [
            (val: ISelectOption) =>
              props.validator(val) || props.validationMessage,
          ]
        : null
    );

    watch(
      () => props.modelValue,
      (newVal) => {
        setInputValue(newVal);
      },
      { deep: true }
    );

    const outputValue = computed(() => {
      if (inputValue.value != null) {
        if (props.valueUniqueIdentifier === null) {
          return inputValue.value.value;
        } else {
          const value = inputValue.value.value;
          if (typeof value === 'object' && props.valueUniqueIdentifier in value) {
            return value[props.valueUniqueIdentifier];
          }
        }
      }
      // Provide a default return value if none of the conditions are met
      return null;
    });

    function inputValueChange(newVal: ISelectOption | null) {
      inputValue.value = newVal;
      emit("change", newVal == null ? null : outputValue.value);
    }

    watch(
      () => inputValue.value,
      () => {
        emit("update:modelValue", outputValue.value);
      },
      { deep: true }
    );

    const maxInputWidth = computed(() =>
      props.maxWidth ? { width: props.maxWidth + "px" } : ""
    );

    const input = ref();
    function validate() {
      input.value.validate();
    }

    function hasError() {
      return input.value.hasError;
    }

    function resetValidation() {
      input.value.resetValidation();
    }

    return {
      input,
      inputValue,
      maxInputWidth,
      inputRules,
      inputValueChange,
      resetValidation,
      validate,
      hasError,
    };
  },
});
</script>

<style type="text/scss" lang="scss"></style>
