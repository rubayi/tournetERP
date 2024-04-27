<template>
  <div id="month-range" class="row q-col-gutter-md">
    <div class="col-6">
      <input-comp
        v-model="editableStartDate"
        :clearable="clearable"
        :error="isInvalid"
        :label="startDateLabel"
        type="month"
      />
    </div>
    <div class="col-6">
      <input-comp
        v-model="editableEndDateVal"
        :clearable="clearable"
        :error="isInvalid"
        :label="endDateLabel"
        type="month"
      />
    </div>
  </div>
  <div>
    <span v-if="isInvalid" class="text-negative">
      {{ errorMessage }}
    </span>
  </div>
</template>

<script lang="ts">
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import moment from "moment";
import { computed, defineComponent, ref, watch } from "vue";

import InputComp from "src/components/common/InputComp.vue";

export default defineComponent({
  name: "MonthRange",
  components: { InputComp },
  props: {
    clearable: {
      type: Boolean,
      default: true,
    },
    startDate: {
      type: String,
      default: "",
    },
    startDateLabel: {
      type: String,
      default: "Start Date",
    },
    endDate: {
      type: String,
      default: "",
    },
    endDateLabel: {
      type: String,
      default: "End Date",
    },
    required: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, { emit }) {
    const editableStartDate = ref(moment().format("YYYY-MM").toString());
    useSyncModelValue(
      props,
      "startDate",
      emit,
      "update:startDate",
      editableStartDate
    );

    const editableEndDateVal = ref(moment().format("YYYY-MM").toString());
    useSyncModelValue(
      props,
      "endDate",
      emit,
      "update:endDate",
      editableEndDateVal
    );

    function dateRangeValidator() {
      return editableStartDate.value <= editableEndDateVal.value;
    }

    const isInvalid = ref(false);

    function validate() {
      isInvalid.value =
        (props.required && bothInputsNotEntered.value) ||
        endDateIsBeforeStartDate.value;
    }

    const errorMessage = computed(() => {
      let message = "";
      if (props.required && bothInputsNotEntered.value) {
        message = "Both start and end date are required";
      } else if (endDateIsBeforeStartDate.value) {
        message = "End date cannot be before start date";
      }
      return message;
    });

    const bothInputsNotEntered = computed(() => {
      return !editableStartDate.value || !editableEndDateVal.value;
    });

    const endDateIsBeforeStartDate = computed(() => {
      return (
        moment(editableStartDate.value, "YYYY-MM") >
        moment(editableEndDateVal.value, "YYYY-MM")
      );
    });

    function resetValidation() {
      isInvalid.value = false;
    }

    function hasError() {
      return isInvalid.value;
    }

    watch([editableStartDate, editableEndDateVal], () => {
      validate();
    });

    return {
      editableStartDate,
      editableEndDateVal,
      dateRangeValidator,
      isInvalid,
      validate,
      resetValidation,
      errorMessage,
      hasError,
    };
  },
});
</script>

<style lang="scss">
#month-range {
  label.q-field.row.no-wrap.items-start.q-field--standard.q-input.q-field--float.q-field--labeled.q-field--dense.q-field--with-bottom {
    padding-bottom: 6px;
  }
}
</style>
