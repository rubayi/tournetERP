<template>
  <card-comp-design title="Primary Information">
    <template #content>
      <div class="q-pt-md q-pb-xs q-pl-md q-pr-md">
        <div v-if="compFormData != null">
          <div class="row q-col-gutter-md">
            <div class="col-12">
              <div class="row q-col-gutter-md">
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.compSector"
                    class="full-width"
                    label="Sector"
                    :options="compSectorList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.compGroup"
                    label="Group"
                    class="full-width"
                    :options="compGroupList"
                  />
                </div>
                <div class="col-3">
                  <date-picker-comp
                    v-model="compFormData.estDate"
                    class="full-width"
                    clearable
                    label="Establishment Date"
                  />
                </div>
                <div class="col-3">
                  <input-color-comp
                    v-model="compFormData.compColor"
                    class="full-width"
                    clearable
                    label="Company Color"
                  />
                </div>
                <div class="col-3">
                  <input-comp
                    v-model="compFormData.compKor"
                    class="full-width"
                    clearable
                    label="Company Name"
                  />
                </div>
                <div class="col-3">
                  <input-comp
                    v-model="compFormData.compEng"
                    class="full-width"
                    clearable
                    label="Company Name(En)"
                  />
                </div>

                <div class="col-3">
                  <input-comp
                    v-model="compFormData.compAbb"
                    class="full-width"
                    clearable
                    label="Abbreviation"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.compRate"
                    label="Company Level"
                    class="full-width"
                    :options="compRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.prepaidHow"
                    label="Assign Prepaid"
                    class="full-width"
                    :options="prepaidHowList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.couponYn"
                    label="Coupon Can Use"
                    class="full-width"
                    :options="couponYnList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.hotelRate"
                    label="Hotel Rate"
                    class="full-width"
                    :options="hotelRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.optionRate"
                    label="Option Rate"
                    class="full-width"
                    :options="compOptionRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.rentcarRate"
                    label="Rent Car Rate"
                    class="full-width"
                    :options="rentcarRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.restaurantRate"
                    label="Restaurant Rate"
                    class="full-width"
                    :options="restaurantRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.packRegRate"
                    label="Package Rate"
                    class="full-width"
                    :options="pkgRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.packRate"
                    label="Package Only Rate"
                    class="full-width"
                    :options="pkgOnlyRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.honeymoonRegRate"
                    label="Honeymoon Rate"
                    class="full-width"
                    :options="restaurantRateList"
                  />
                </div>
                <div class="col-3">
                  <select-comp
                    v-model="compFormData.honeymoonRate"
                    label="Honeymoon Only Rate"
                    class="full-width"
                    :options="honeymoonOnlyRateList"
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
import { defineComponent, ref, watch } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import InputColorComp from 'src/components/common/InputColorComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { CompForm } from 'src/types/CompForm';
//import { CompDocumentsgForm } from "src/types/CompDocumentsgForm";
import { SelectOption } from 'src/types/SelectOption';
//import { CompMultiCheckbox } from "src/types/CompMultiCheckbox";

// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import InputLabelTemplateContent from 'src/components/common/InputLabelTemplateContent.vue';

export default defineComponent({
  name: 'CompFormDrawerPrimary',
  components: {
    CardCompDesign,
    SelectComp,
    InputComp,
    DatePickerComp,
    InputColorComp,
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
    compOptionRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    couponYnList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compGroupList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    rentcarRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    restaurantRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    prepaidHowList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    hotelRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const compFormData = ref<CompForm>(new CompForm());
    const readonlybtn = ref<boolean>(true);

    useSyncModelValue(
      props,
      'primaryData',
      emit,
      'update:primaryData',
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
