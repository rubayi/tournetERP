<template>
  <div id="input-comp">
    <q-input
      v-if="type !== 'file'"
      v-model="inputValue"
      :autogrow="autogrow"
      :class="inputClass"
      :clearable="clearable"
      :color="color"
      dense
      :disable="disable"
      :error="error"
      :hint="hint"
      :label="label"
      :label-color="labelcolor"
      label-slot
      :mask="mask"
      :maxlength="maxLength"
      :outlined="outlined"
      :placeholder="placeholder"
      :prefix="prefix"
      :readonly="readonly"
      :rules="rules"
      stack-label
      :style="maxInputWidth"
      :type="type"
      @blur="emitBlur"
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
    </q-input>
    <div v-else>
      <input id="file-upload" type="file" class="hidden" @blur="emitBlur" />
      <label for="file-upload" class="custom-file-upload">
        <q-icon class="icon-style" name="cloud_upload" />
        <span class="span-style">{{ t('uploadImage') }}</span>
      </label>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from 'vue';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import i18n from 'src/i18n';

export default defineComponent({
  name: 'InputComp',
  props: {
    clearable: {
      type: Boolean,
      default: true,
    },
    disable: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: undefined,
    },
    color: {
      type: String,
      default: '',
    },
    error: {
      type: Boolean,
      default: false,
    },
    hint: {
      type: String,
      default: '',
    },
    inputClass: {
      type: String,
      default: '',
    },

    labelcolor: {
      type: String,
      default: '',
    },
    mask: {
      type: String,
      default: '',
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
      type: String,
      default: '',
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String as () =>
        | 'number'
        | 'text'
        | 'search'
        | 'email'
        | 'date'
        | 'textarea'
        | 'time'
        | 'file'
        | 'password'
        | 'tel'
        | 'url'
        | 'datetime-local'
        | undefined,
      default: 'text',
    },
    validator: {
      type: Function,
      default: () => true,
    },
    validationMessage: {
      type: String,
      default: '',
    },
    placeholder: {
      type: String,
      default: '',
    },
    prefix: {
      type: String,
      default: '',
    },
    outlined: {
      type: Boolean,
      default: false,
    },
    autogrow: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue', 'input-blur'],
  setup(props, { emit }) {
    const rules = computed(() =>
      props.validationMessage
        ? [(val: string) => props.validator(val) || props.validationMessage]
        : undefined
    );

    const inputValue = ref<string>('');

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue
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

    const maxInputWidth = computed(() =>
      props.maxWidth ? { width: props.maxWidth + 'px' } : ''
    );

    function emitBlur(event: Event) {
      emit('input-blur', event);
    }

    return {
      t: i18n.global.t,
      input,
      rules,
      inputValue,
      maxInputWidth,
      resetValidation,
      validate,
      hasError,
      emitBlur,
    };
  },
});
</script>

<style lang="scss">
#input-comp {
  .q-field--dense .q-field__bottom {
    font-size: 12px;
  }
  .q-field--outlined:hover .q-field__control:before {
    border-color: primary;
  }
}
.icon-style {
  padding: 0 10px;
  font-size: 20px;
  color: white;
}
.span-style {
  font-weight: bold;
  color: white;
  font-size: 15px;
}
.input-style {
  background-color: rgb(187, 187, 187);
  color: white;
  max-height: 40px;
}
</style>
