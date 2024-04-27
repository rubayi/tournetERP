<template>
  <span id="multi-input-comp">
    <q-select
      v-model="inputValue"
      :clearable="clearable"
      dense
      :error="invalid"
      :error-message="validationMessage"
      :hide-dropdown-icon="!options"
      hide-hint
      :hint="hint"
      input-debounce="0"
      :label="label"
      multiple
      :options="options"
      use-chips
      :use-input="!options"
      @new-value="newValueCallback"
      @update:model-value="validate"
      ref="input"
    />
  </span>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import { ISelectOption } from "src/types/SelectOption";
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "MultiInputComp",
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
      default: "",
    },
    label: {
      type: String,
      default: undefined,
    },
    maxWidth: {
      type: Number,
      default: null,
    },
    modelValue: {
      type: Array,
      default: () => [],
    },
    newValueCallback: {
      type: Function,
      default: (val: any, done: CallableFunction) => {
        done(val, "add-unique");
      },
    },
    validator: {
      type: Function,
      default: () => true,
    },
    validationMessage: {
      type: String,
      default: "",
    },
    options: {
      type: Array as () => ISelectOption[] | string[],
      default: undefined,
    },
  },
  emits: ["update:modelValue", "new-value"],
  setup(props, { emit }) {
    const inputValue = ref<ISelectOption | null>();

    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      inputValue
    );

    const maxInputWidth = computed(() =>
      props.maxWidth ? { width: props.maxWidth + "px" } : ""
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

    return {
      input,
      invalid,
      inputValue,
      maxInputWidth,
      resetValidation,
      validate,
      hasError,
    };
  },
});
</script>

<style type="text/scss" lang="scss">
#multi-input-comp {
  .q-field__messages {
    color: #2851bb; // primary
  }
}
</style>
