<template>
  <span id="input-select-comp">
    <q-select
      v-model="inputValue"
      :clearable="clearable"
      dense
      :disable="disable"
      :error="invalid"
      :error-message="validationMessage"
      fill-input
      :hide-dropdown-icon="!options"
      hide-selected
      input-debounce="0"
      :label="label"
      :maxlength="maxLength"
      option-label="codename"
      option-value="code2"
      :options="options"
      stack-label
      :use-input="true"
      @input-value="setInput"
      @new-value="newValueCallback"
      @update:model-value="validate"
      ref="input"
    />
  </span>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from 'vue';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'InputSelectComp',
  props: {
    clearable: {
      type: Boolean,
      default: false,
    },
    error: {
      type: Boolean,
      default: false,
    },
    hint: {
      type: String,
      default: '',
    },
    label: {
      type: String,
      default: undefined,
    },
    maxLength: {
      type: Number,
      default: 200,
    },
    maxWidth: {
      type: Number,
      default: null,
    },
    modelValue: {
      type: String,
      Object,
      default: '',
    },
    optionLabel: {
      type: String,
      default: 'label',
    },
    optionValue: {
      type: String,
      default: 'value',
    },
    newValueCallback: {
      type: Function,
      default: (val: unknown, done: CallableFunction) => {
        done(val, 'add-unique');
      },
    },
    validator: {
      type: Function,
      default: () => true,
    },
    validationMessage: {
      type: String,
      default: '',
    },
    options: {
      type: Array,
      default: undefined,
    },
    modelValueFormatFunction: {
      type: Function,
      default: (val: unknown) => {
        return val;
      },
    },
    emitValueFormatFunction: {
      type: Function,
      default: (val: unknown) => {
        return val;
      },
    },
    disable: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue', 'new-value', 'input-value-change'],
  setup(props, { emit }) {
    const inputValue = ref();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue,
      props.modelValueFormatFunction as (any: unknown) => unknown,
      props.emitValueFormatFunction as (any: unknown) => unknown
    );

    const maxInputWidth = computed(() =>
      props.maxWidth ? { width: props.maxWidth + 'px' } : ''
    );

    const input = ref();
    const invalid = ref<boolean>(false);
    function validate() {
      if (props.validator) {
        invalid.value = !props.validator(inputValue.value);
      }
    }

    function hasError() {
      return invalid.value;
    }

    function resetValidation() {
      invalid.value = false;
    }

    function setInput(value: unknown) {
      if (input.value) {
        input.value.add(value, 'add-unique');
      }
    }

    return {
      input,
      invalid,
      inputValue,
      maxInputWidth,
      resetValidation,
      validate,
      hasError,
      setInput,
    };
  },
});
</script>

<style type="text/scss" lang="scss"></style>
