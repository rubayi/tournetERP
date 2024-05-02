<template>
  <card-comp-design title="Hawaii Foody Order Filters">
    <template #content>
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Date</div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edittournetsearchData.searchBeginDt"
              class="full-width"
              clearable
              label="Search Begin"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edittournetsearchData.searchEndDt"
              class="full-width"
              clearable
              label="Search End"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edittournetsearchData.searchCompKor"
              class="full-width"
              clearable
              label="From"
              outlined
              stack-label
            />
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edittournetsearchData.searchCompEng"
              class="full-width"
              clearable
              label="To"
              outlined
              stack-label
            />
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Work Order Office</div>
          <div class="col-4">
            <select-comp
              v-model="edittournetsearchData.searchCompSector"
              class="full-width"
              :options="compSectorlistgroup"
              outlined
            />
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Remarks</div>
          <div class="col-10">
            <input-comp
              v-model="edittournetsearchData.searchCompMemo"
              class="full-width"
              clearable
              label=""
              outlined
              stack-label
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Company Rate</div>
          <div class="col-10">
            <select-comp
              v-model="edittournetsearchData.searchCompRate"
              class="full-width"
              :options="compRateList"
              outlined
            />
          </div>
        </div>


        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Coupon</div>
          <div class="col-10">
            <select-comp
              v-model="edittournetsearchData.searchCouponYn"
              class="full-width"
              :options="couponYnList"
              outlined
            />
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Comment</div>
          <div class="col-10">
            <select-comp
              v-model="edittournetsearchData.searchCompGroup"
              class="full-width"
              :options="compGroupList"
              outlined
            />
          </div>
        </div>
      </div>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
//Component
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import DatePickerComp from "src/components/common/DatePickerComp.vue";
import SelectComp from "src/components/common/SelectComp.vue";
import InputComp from "src/components/common/InputComp.vue";

//Service
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

//Type
import { CompSearchForm } from "src/types/CompSearchForm";

export default defineComponent({
  name: "CompSearchFormDrawerContent",
  components: {
    DatePickerComp,
    SelectComp,
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CompSearchForm,
      default: () => new CompSearchForm(),
    },

  },
  setup(props, { emit }) {
    const edittournetsearchData = ref<CompSearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      edittournetsearchData
    );

    return {
      edittournetsearchData,
    };
  },
});
</script>

<style lang="scss"></style>
