<template>
  <q-select
    dense
    :display-value="`${
      inputValue.districtid ? inputValue.sectorname : '- All -'
    }`"
    :model-value="inputValue"
    option-value="districtid"
    :options="options"
    stack-label
    @update:model-value="inputValueChange"
    ref="input"
  >
    <template #option="scope">
      <q-item v-bind="scope.itemProps">
        <q-item-section>
          <q-item-label
            >{{ scope.opt.islandname }} |
            {{ scope.opt.districtname }}</q-item-label
          >
          <q-item-label caption>{{ scope.opt.sectorname }}</q-item-label>
        </q-item-section>
      </q-item>
    </template>
  </q-select>
</template>

<script lang="ts">
import _ from "lodash";
import { computed, defineComponent, ref, watch } from "vue";
import { CourtPoliceForm } from "@/types/CourtPoliceForm";

export default defineComponent({
  name: "SelectPoliceComp",
  props: {
    clearable: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: [String, Number, Object],
      default: () => null,
    },
    useInput: {
      type: Boolean,
      default: false,
    },
    options: {
      type: Array as () => CourtPoliceForm[],
      required: true,
    },
  },
  emits: ["update:modelValue", "change"],
  setup(props, { emit }) {
    const inputValue = ref<CourtPoliceForm | null>();
    setInputValue(props.modelValue);
    function setInputValue(value: any) {
      let matchingOption = null;
      if (value !== null && value !== undefined) {
        matchingOption = props.options.find((o) =>
          _.isEqual(o.districtid, value)
        );
        if (matchingOption === undefined) {
          matchingOption = new CourtPoliceForm();
        }
      }
      inputValue.value = matchingOption;
    }

    watch(
      () => props.modelValue,
      (newVal) => {
        setInputValue(newVal);
      },
      { deep: true }
    );

    const outputValue = computed(() => {
      if (inputValue.value != null) {
        return inputValue.value.districtid;
      } else {
        return null;
      }
    });

    function inputValueChange(newVal: CourtPoliceForm | null) {
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

    const input = ref();

    return {
      input,
      inputValue,
      inputValueChange,
    };
  },
});
</script>

<style type="text/scss" lang="scss"></style>
