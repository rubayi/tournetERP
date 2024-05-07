<template>
  <input-comp
    clearable
    :disable="disable"
    :hint="hint"
    lazy-rules="ondemand"
    mask="####/##/##"
    :model-value="inputValue"
    validation-message="Date is Invalid"
    :validator="validator"
    @blur="validate"
    @update:model-value="inputValueChange"
    ref="dateInput"
  >
    <template #append>
      <q-icon class="cursor-pointer" name="event">
        <q-popup-proxy
          transition-hide="scale"
          transition-show="scale"
          ref="qDateProxy"
        >
          <q-date v-model="inputValue" mask="YYYY/MM/DD/">
            <div class="row items-center justify-end">
              <q-btn v-close-popup color="primary" flat label="Close"></q-btn>
            </div>
          </q-date>
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
import { requiredValidator } from "src/utils/helpers/InputValidatorHelper";
import { defineComponent, ref, computed } from "vue";
import DateHelper from "src/utils/helpers/DateHelper";
import { useValidateInputs } from "src/utils/helpers/useValidateInputs";
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

import InputComp from "src/components/common/InputComp.vue";

export default defineComponent({
  name: "DatePickerComp",
  components: { InputComp },
  props: {
    disable: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: String,
      default: "",
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
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    let timeout = 0;
    const inputValue = ref<string | "">(props.modelValue);
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
      "modelValue",
      emit,
      "update:modelValue",
      inputValue
    );

    const dateInput = ref();
    const { inputHasError, validate, hasError } = useValidateInputs([
      dateInput,
    ]);

    function resetValidation() {
      if (props.modelValue !== inputValue.value) {
        inputValue.value = "";
      }
      dateInput.value.resetValidation();
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
      dateInput,
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
