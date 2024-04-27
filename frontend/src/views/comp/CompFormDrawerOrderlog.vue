<template>
  <card-comp-design title="Work Order Log">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <div class="row q-col-gutter-md">
              <div class="col-6">
                <date-picker-comp
                  v-model="receiveddateVal"
                  class="full-width"
                  clearable
                  label="Date Transmittal Received"
                />
              </div>
              <div class="col-6">
                <input-select-comp
                  v-model="edittournetyorderlogData.receivedby"
                  class="full-width"
                  label="Transmittal Received by"
                  :options="receivedbylistgroup"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-6">
                <date-picker-comp
                  v-model="datecrewassignedVal"
                  class="full-width"
                  clearable
                  label="Date Crew Assigned"
                />
              </div>
              <div class="col-6">
                <date-picker-comp
                  v-model="datecompletedVal"
                  class="full-width"
                  clearable
                  label="Date Work Completed"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-6">
                <date-picker-comp
                  v-model="posteddateVal"
                  class="full-width"
                  clearable
                  label="Date Posted"
                />
              </div>
              <div class="col-6">
                <input-select-comp
                  v-model="edittournetyorderlogData.postedby"
                  class="full-width"
                  label="Posted by"
                  :options="postedbylistgroup"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-12">
                <date-picker-comp
                  v-model="datesentbacktotrafficVal"
                  class="full-width"
                  clearable
                  label="Date Work Order Sent Back to Traffic"
                />
              </div>
            </div>
          </div>
          <div class="col-6">
            <div style="font-weight: bold">- Crew Assigned</div>
            <div
              class="row q-col-gutter-xs q-mt-xs"
              style="margin-bottom: 0.6em"
            >
              <q-checkbox
                v-for="(option, index) in crewassignedlistgroup"
                v-model="crewassignselection"
                class="col-auto"
                :label="option.label"
                :val="option.value"
                :key="index"
              ></q-checkbox>
            </div>
            <div class="row q-col-gutter-md">
              <div class="col-12">
                <text-area-comp
                  v-model="edittournetyorderlogData.comments"
                  class="full-width full-height"
                  clearable
                  label="Comments"
                  outlined
                />
              </div>
            </div>
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
// Component
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import DatePickerComp from "src/components/common/DatePickerComp.vue";
import TextAreaComp from "src/components/common/TextAreaComp.vue";
import InputSelectComp from "src/components/common/InputSelectComp.vue";

// Type
import { TournetWorkorderlogForm } from "src/types/TournetWorkorderlogForm";
import { SelectOption } from "src/types/SelectOption";
import { TournetMultiCheckbox } from "src/types/TournetMultiCheckbox";
// Helper
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";
import DateHelper from "src/utils/helpers/DateHelper";
export default defineComponent({
  name: "TournetFormDrawerOrderlog",
  components: {
    DatePickerComp,
    TextAreaComp,
    InputSelectComp,
    CardCompDesign,
  },
  props: {
    data: {
      type: Object as () => TournetWorkorderlogForm,
      default: () => new TournetWorkorderlogForm(),
    },
    workcrewassign: {
      type: Array as () => TournetMultiCheckbox[],
      default: () => [],
    },
    receiveddate: {
      type: String,
      default: null,
    },
    datecrewassigned: {
      type: String,
      default: null,
    },
    datecompleted: {
      type: String,
      default: null,
    },
    posteddate: {
      type: String,
      default: null,
    },
    datesentbacktotraffic: {
      type: String,
      default: null,
    },
    crewassignedlistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    postedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
    receivedbylistgroup: {
      type: Array as () => string[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const edittournetyorderlogData = ref<TournetWorkorderlogForm>();
    useSyncModelValue(
      props,
      "data",
      emit,
      "update:data",
      edittournetyorderlogData
    );

    const datecompletedVal = ref();
    useSyncModelValue(
      props,
      "datecompleted",
      emit,
      "update:datecompleted",
      datecompletedVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );
    const datecrewassignedVal = ref();
    useSyncModelValue(
      props,
      "datecrewassigned",
      emit,
      "update:datecrewassigned",
      datecrewassignedVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );
    const datesentbacktotrafficVal = ref();
    useSyncModelValue(
      props,
      "datesentbacktotraffic",
      emit,
      "update:datesentbacktotraffic",
      datesentbacktotrafficVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );
    const posteddateVal = ref();
    useSyncModelValue(
      props,
      "posteddate",
      emit,
      "update:posteddate",
      posteddateVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );
    const receiveddateVal = ref();
    useSyncModelValue(
      props,
      "receiveddate",
      emit,
      "update:receiveddate",
      receiveddateVal,
      DateHelper.formatISOStringToShortDateString,
      DateHelper.formatShortDateToISOString
    );

    const refcrewassign = ref<TournetMultiCheckbox[]>([]);
    useSyncModelValue(
      props,
      "workcrewassign",
      emit,
      "update:workcrewassign",
      refcrewassign
    );

    const crewassignselection = ref<number[]>([]);
    // Load crewassign Checkbox
    loadworkcrewassign();
    function loadworkcrewassign() {
      for (var val of props.workcrewassign) {
        crewassignselection.value.push(val.codevalue);
      }
    }

    watch(
      () => crewassignselection.value,
      (newValue) => {
        if (newValue) {
          refcrewassign.value = [];
          for (var x of crewassignselection.value) {
            let tempmulticheckbox = new TournetMultiCheckbox();
            tempmulticheckbox.codevalue = x;
            refcrewassign.value.push(tempmulticheckbox);
          }
        }
      }
    );

    watch(
      () => props.data,
      (newValue) => {
        if (newValue) {
          crewassignselection.value = [];
          loadworkcrewassign();
        }
      }
    );

    return {
      edittournetyorderlogData,
      receiveddateVal,
      posteddateVal,
      datesentbacktotrafficVal,
      datecrewassignedVal,
      datecompletedVal,
      crewassignselection,
      refcrewassign,
    };
  },
});
</script>

<style lang="scss"></style>
