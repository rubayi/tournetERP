<template>
  <input-comp
    v-model="inputValue"
    class="text-area-comp full-width"
    :label="title"
    :max-length="maxLength"
    :readonly="disable || readonly"
    :stack-label="true"
    type="textarea"
    :validation-message="validationMessage"
    :validator="validator"
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
  </input-comp>
</template>

<script lang="ts">
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";
import { defineComponent, ref } from "vue";
import InputComp from "@/components/common/InputComp.vue";

export default defineComponent({
  name: "TextAreaComp",
  components: { InputComp },
  props: {
    title: {
      type: String,
      default: "",
    },
    modelValue: {
      type: String,
      default: "",
    },
    maxLength: {
      type: Number,
      default: 2000,
    },
    validator: {
      type: Function,
      default: () => true,
    },
    validationMessage: {
      type: String,
      default: "",
    },
    disable: {
      type: Boolean,
      default: false,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const inputValue = ref<string>("");
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      inputValue
    );

    const input = ref();
    function validate() {
      input.value.validate();
    }

    function hasError() {
      return input.value.hasError();
    }

    function resetValidation() {
      input.value.resetValidation();
    }
    return {
      inputValue,
      input,
      validate,
      hasError,
      resetValidation,
    };
  },
});
</script>

<style lang="scss">
.text-area-comp {
  textarea {
    height: 5.4rem;
  }
}
</style>
