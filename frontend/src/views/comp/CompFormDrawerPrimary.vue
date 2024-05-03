<template>
  <card-comp-design title="Primary Information">
    <template #content>
      <div class="q-pt-md q-pb-xs q-pl-md q-pr-md">
        <div v-if="compFormData != null">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <div class="row q-col-gutter-md">
              <div class="col-4">
                <select-comp
                  v-model="compFormData.compSector"
                  class="full-width"
                  label="Sector"
                  :options="compSectorList"
                />
              </div>

              <div class="col-4">
                <input-comp
                  v-model="compFormData.compKor"
                  class="full-width"
                  clearable
                  label="Company Name"
                  :readonly="readonlybtn"
                />

              </div>
              <div class="col-4">
                <input-comp
                  v-model="compFormData.compEng"
                  class="full-width"
                  clearable
                  label="Company Name(En)"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-label-template-content
                  v-model="compFormData.compColor"
                  class="full-width"
                  clearable
                  label="Company Color"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="compFormData.compAbb"
                  class="full-width"
                  clearable
                  label="Abbreviation"
                  :readonly="readonlybtn"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-4">
                <select-comp
                        v-model="compFormData.compRate"
                        label="Company Rate"
                        :options="compRateList"
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="compFormData.estDate"
                  class="full-width"
                  clearable
                  label="DATE"
                  :readonly="readonlybtn"
                  required="true"
                />
              </div>
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
//import { CompDocumentsgForm } from "src/types/CompDocumentsgForm";
import { SelectOption } from "src/types/SelectOption";
import { CompMultiCheckbox } from "src/types/CompMultiCheckbox";

// Store
import store from "src/store";
// Helper
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import DateHelper from "src/utils/helpers/DateHelper";
import InputLabelTemplateContent from "src/components/common/InputLabelTemplateContent.vue";

export default defineComponent({
  name: "CompFormDrawerPrimary",
  components: {
    InputLabelTemplateContent,
    CardCompDesign,
    SelectComp,
    InputComp,
    DatePickerComp,
  },
  props: {
    primaryData: {
      type: Object as () => CompForm,
      default: () => new CompForm(),
    },
    compSectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const compFormData = ref<CompForm>();
    const readonlybtn = ref<boolean>(true);

    useSyncModelValue(
      props,
      "primaryData",
      emit,
      "update:primaryData",
      compFormData
    );

    watch(
      () => props.primaryData,
      (newValue) => {
        compFormData.value = newValue;
        console.log(compFormData.value);
        console.log(props.compRateList);
      }
    );

    return {
      compFormData,
      //addCompany,
      readonlybtn,
    };
  },
});
</script>

<style lang="scss">
.fieldset03 > h1:after {
  margin: 0 1px 0 20em;
}
</style>
