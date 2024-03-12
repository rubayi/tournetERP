<template>
  <div id="multiselect-comp">
    <Multiselect
      v-model="inputValue"
      :disabled="disable"
      :loading="loading"
      mode="tags"
      :options="options"
      :searchable="searchable"
      @input="input"
      @open="$emit('open')"
      ref="multiselect"
    >
      <template #clear>
        <div class="icon-background"></div>
        <i class="fas fa-times-circle" @click="clear" />
      </template>
    </Multiselect>
  </div>
</template>

<script>
import Multiselect from "@vueform/multiselect";
import { defineComponent, onMounted, ref, watch } from "vue";

export default defineComponent({
  name: "MultiselectComp",
  components: {
    Multiselect,
  },
  props: {
    modelValue: {
      type: Array,
      default: () => [],
    },
    options: {
      type: Array,
      default: () => [],
    },
    loading: {
      type: Boolean,
      default: false,
    },
    resetvalue: {
      type: Boolean,
      default: false,
    },
    searchable: {
      type: Boolean,
      default: true,
    },
    disable: {
      type: Boolean,
      default: false,
    },
  },
  emits: {
    "update:modelValue": null,
    "on-change": null,
    open: null,
  },
  setup(props, { emit }) {
    const inputValue = ref(props.modelValue);

    function clear() {
      inputValue.value = [];
    }

    function input(event) {
      emit("update:modelValue", event);
      emit("on-change");
    }

    watch(
      () => props.resetvalue,
      (newValue) => {
        if (newValue == true) {
          clear();
        }
      }
    );

    watch(
      () => props.modelValue,
      (newVal) => (inputValue.value = newVal),
      { deep: true }
    );
    onMounted(() => {
      applyEventBlockToOptions();
    });

    // This stop the option dropdown from closing when the scroll bar is clicked
    function applyEventBlockToOptions() {
      const multiselectOptions = [
        ...document.getElementsByClassName("multiselect-options"),
      ];

      multiselectOptions.forEach((elem) => {
        elem.addEventListener("mousedown", function (event) {
          event.preventDefault();
        });
      });
    }

    return {
      input,
      inputValue,
      clear,
    };
  },
});
</script>

<style src="@vueform/multiselect/themes/default.css"></style>

<style lang="scss">
#multiselect-comp {
  width: 100%;

  .multiselect-input {
    width: 100%;
    min-height: 24px;
    max-height: 110px;
    border-color: #babfc7;
    border-width: 1px;
    overflow: auto;
    flex-direction: column;
  }

  .icon-background {
    width: 15px;
    height: 15px;
    background-color: white;
    z-index: 3;
    border-radius: 50%;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 29px;
  }
  .multiselect-input > i.fas.fa-times-circle {
    padding-right: 9px;
    z-index: 3;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 20px;
  }

  .multiselect-tag {
    background-color: var(--q-primary);
  }

  .multiselect-tag i:before {
    color: #c3c3c3;
  }

  .multiselect-options > .multiselect-option {
    font-size: 14px;
  }

  .multiselect-spinner:after,
  .multiselect-spinner:before {
    border-top-color: var(--q-primary);
  }
}
</style>
