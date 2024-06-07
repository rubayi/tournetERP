<template>
  <input-comp
    clearable
    :disable="disable"
    :hint="hint"
    :rules="['anyColor']"
    :model-value="inputValue"
    @update:model-value="inputValueChange"
    ref="colorInput"
  >
    <template #append>
      <q-icon class="cursor-pointer" name="colorize">
        <q-popup-proxy transition-hide="scale" transition-show="scale">
          <q-color v-model="inputValue" />
        </q-popup-proxy>
      </q-icon>
    </template>
    <template v-if="$slots.label" #label>
      <slot name="label" />
    </template>
    <template v-if="$slots.hint" #hint>
      <slot name="hint" />
    </template>
  </input-comp>
</template>

<script lang="ts">
import { requiredValidator } from 'src/utils/helpers/InputValidatorHelper';
import { defineComponent, ref, computed } from 'vue';
import DateHelper from 'src/utils/helpers/DateHelper';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

import InputComp from 'src/components/common/InputComp.vue';
import { useValidateInputs } from 'src/utils/helpers/useValidateInputs';

export default defineComponent({
  name: 'InputColorComp',
  components: { InputComp },
  props: {
    disable: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: String,
      default: '',
    },
    formatToMonth: {
      type: Number,
      default: null,
    },
    formatToDay: {
      type: Number,
      default: null,
    },
    formatToYear: {
      type: Number,
      default: null,
    },
    hint: {
      type: String,
      default: null,
    },
    required: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    let timeout = 0;
    const inputValue = ref<string | undefined>(props.modelValue);
    const formatDate = computed(
      () => props.formatToMonth || props.formatToDay || props.formatToYear
    );

    function inputValueChange(newVal: string) {
      inputValue.value = newVal;
      if (formatDate.value) {
        // Trigger scaling of inputValue
        if (timeout) {
          clearTimeout(timeout);
        }
        if (validator(newVal)) {
          // Timer to pause for user input
          timeout = window.setTimeout(() => {
            inputValue.value = DateHelper.formatToMonthDayYearShortDateString(
              newVal,
              props.formatToMonth,
              props.formatToDay,
              props.formatToYear
            );
            validate();
          }, 500);
        }
      }
    }

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue
    );

    const colorInput = ref();
    const { inputHasError, validate, hasError } = useValidateInputs([
      colorInput,
    ]);

    function resetValidation() {
      if (props.modelValue !== inputValue.value) {
        inputValue.value = '';
      }
      colorInput.value.resetValidation();
    }

    const validator = (dateString: string): boolean => {
      return (
        DateHelper.isValidShortDateString(dateString) &&
        computedRequiredValidator.value(dateString)
      );
    };

    const computedRequiredValidator = computed(() =>
      props.required ? (str: string) => requiredValidator(str) : () => true
    );

    return {
      inputValue,
      inputValueChange,
      colorInput,
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
