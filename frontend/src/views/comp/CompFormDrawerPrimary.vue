<template>
  <card-comp-design title="Primary Information">
    <template #content>
      <div class="q-pt-md q-pb-xs q-pl-md q-pr-md">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <div class="row q-col-gutter-md">
              <div class="col-4">
                <date-picker-comp
                  v-model="workdateVal"
                  class="full-width"
                  clearable
                  label="DATE"
                  :readonly="readonlybtn"
                  required="true"
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="edittournetformData.searchCompSector"
                  class="full-width"
                  label="Work Order Office"
                  :options="searchCompSectorlistgroup"
                  :readonly="readonlybtn"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.searchCompMemo"
                  class="full-width"
                  clearable
                  label="FROM:"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.searchCompKor"
                  class="full-width"
                  clearable
                  label="TO:"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.compEng"
                  class="full-width"
                  clearable
                  label="ATTN:"
                  :readonly="readonlybtn"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-2">
                <select-comp
                        v-model="edittournetformData.searchCompRate"
                        label="Company Rate"
                        :options="compRateList"
                />

              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
// Component
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import InputComp from "src/components/common/InputComp.vue";
import DatePickerComp from "src/components/common/DatePickerComp.vue";
import SelectComp from "src/components/common/SelectComp.vue";
// Type
import { CompForm } from "src/types/CompForm";
import { CompDocumentsgForm } from "src/types/CompDocumentsgForm";
import { SelectOption } from "src/types/SelectOption";
import { CompMultiCheckbox } from "src/types/CompMultiCheckbox";

// Store
import store from "src/store";
// Helper
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import DateHelper from "src/utils/helpers/DateHelper";

export default defineComponent({
  name: "CompFormDrawerPrimary",
  components: {
    InputComp,
    DatePickerComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    data: {
      type: Object as () => CompForm,
      default: () => new CompForm(),
    },
    workstatues: {
      type: Array as () => CompMultiCheckbox[],
      default: () => [],
    },
    workdate: {
      type: String,
      default: null,
    },
    suspensedate: {
      type: String,
      default: null,
    },
    searchCompSectorlistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    workstatuslistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    langtypelistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const edittournetformData = ref<CompForm>();
    const documentslist = ref<CompDocumentsgForm[]>([]);
    const refworkstatues = ref<CompMultiCheckbox[]>([]);
    const readonlybtn = ref<boolean>(true);

    useSyncModelValue(
      props,
      "workstatues",
      emit,
      "update:workstatues",
      refworkstatues
    );

    useSyncModelValue(
      props,
      "data",
      emit,
      "update:data",
      edittournetformData
    );

    const workdateVal = ref();
    useSyncModelValue(
      props,
      "workdate",
      emit,
      "update:workdate",
      workdateVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );

    const suspensedateVal = ref();
    useSyncModelValue(
      props,
      "suspensedate",
      emit,
      "update:suspensedate",
      suspensedateVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );

    const statusselection = ref<number[]>([]);
    // Load Statues Checkbox
    function loadworkstatues() {
      for (var val of props.workstatues) {
        statusselection.value.push(val.codevalue);
      }
    }

    watch(
      () => statusselection.value,
      (newValue) => {
        if (newValue) {
          refworkstatues.value = [];
          for (var x of statusselection.value) {
            let tempmulticheckbox = new CompMultiCheckbox();
            tempmulticheckbox.codevalue = x;
            refworkstatues.value.push(tempmulticheckbox);
          }
        }
      }
    );

    function resetreadData() {
      statusselection.value = [];
      if (
        store.getters.currentUserHasApplicationPermission("LOG_A") ||
        store.getters.currentUserHasApplicationPermission("LOG_E")
      ) {
        readonlybtn.value = false;
      } else {
        readonlybtn.value = true;
      }
    }
    function addCompany() {
      documentslist.value.push(new CompDocumentsgForm());
    }

    return {
      edittournetformData,
      addCompany,
      readonlybtn,
      suspensedateVal,
      workdateVal,
      statusselection,
      refworkstatues,
    };
  },
});
</script>

<style lang="scss">
.fieldset03 > h1:after {
  margin: 0 1px 0 20em;
}
</style>
