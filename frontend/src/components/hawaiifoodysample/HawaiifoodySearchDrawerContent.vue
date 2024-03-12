<template>
  <card-comp-design title="Hawaii Foody Order Filters">
    <template #content>
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Date</div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchsdate"
              class="full-width"
              clearable
              label="Search Begin"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchedate"
              class="full-width"
              clearable
              label="Search End"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edithawaiifoodysearchData.searchletterfrom"
              class="full-width"
              clearable
              label="From"
              outlined
              stack-label
            />
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edithawaiifoodysearchData.searchletterto"
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
            <multiselect-comp
              v-model="edithawaiifoodysearchData.searchworkdep"
              class="full-width"
              :options="workdeplistgroup"
              outlined
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">Work Order No</div>
          <div class="col-4">
            <input-comp
              v-model="edithawaiifoodysearchData.searchworkno"
              class="full-width"
              clearable
              label=""
              outlined
              stack-label
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Subject</div>
          <div class="col-10">
            <input-comp
              v-model="edithawaiifoodysearchData.searchsubject"
              class="full-width"
              clearable
              label=""
              outlined
              stack-label
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Remarks</div>
          <div class="col-10">
            <input-comp
              v-model="edithawaiifoodysearchData.searchremarks"
              class="full-width"
              clearable
              label=""
              outlined
              stack-label
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Comment</div>
          <div class="col-10">
            <input-comp
              v-model="edithawaiifoodysearchData.searchcomment"
              class="full-width"
              clearable
              label=""
              outlined
              stack-label
            />
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Date Received</div>
          <div class="col-4 col-xs-4">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchreceiveddate"
              class="full-width"
              clearable
              label=""
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-2 q-pt-md q-pl-md">Crew Assigned</div>
          <div class="col-4 col-xs-4">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchdatecrewassigned"
              class="full-width"
              clearable
              label=""
              outlined
            >
            </date-picker-comp>
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Work Completed</div>
          <div class="col-4 col-xs-4">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchdatecompleted"
              class="full-width"
              clearable
              label=""
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-2 q-pt-md q-pl-md">Date Posted</div>
          <div class="col-4 col-xs-4">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchposteddate"
              class="full-width"
              clearable
              label=""
              outlined
            >
            </date-picker-comp>
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Send Back to Traffic</div>
          <div class="col-4 col-xs-4">
            <date-picker-comp
              v-model="edithawaiifoodysearchData.searchdatesentbacktotraffic"
              class="full-width"
              clearable
              label=""
              outlined
            >
            </date-picker-comp>
          </div>
        </div>

        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Route</div>
          <div class="col-10 col-xs-10">
            <input-comp
              v-model="edithawaiifoodysearchData.searchletterrouteno"
              class="full-width full-height"
              outlined
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Status</div>
          <div class="col-10 col-xs-10">
            <multiselect-comp
              v-model="edithawaiifoodysearchData.searchworkstatus"
              class="full-width"
              :options="workstatuslistgroup"
              outlined
            />
          </div>
        </div>

        <div class="row q-col-gutter-md q-mt-xs">
          <div class="col-2 q-pt-md q-pl-md">Crew Assigned</div>
          <div class="col-10 col-xs-10">
            <multiselect-comp
              v-model="edithawaiifoodysearchData.searchcrewassigned"
              class="full-width"
              :options="crewassignedlistgroup"
              outlined
            />
          </div>
        </div>

        <div class="row q-col-gutter-md q-mt-xs">
          <div class="col-2 q-pt-md q-pl-md">Received by</div>
          <div class="col-4 col-xs-4">
            <multiselect-comp
              v-model="edithawaiifoodysearchData.searchreceivedby"
              class="full-width"
              :options="receivedbylistgroup"
              outlined
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">Posted by</div>
          <div class="col-4 col-xs-4">
            <multiselect-comp
              v-model="edithawaiifoodysearchData.searchpostedby"
              class="full-width"
              :options="postedbylistgroup"
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
import CardCompDesign from "@/components/common/CardCompDesign.vue";
import DatePickerComp from "@/components/common/DatePickerComp.vue";
import MultiselectComp from "@/components/common/MultiselectComp.vue";
import InputComp from "@/components/common/InputComp.vue";

//Service
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

//Type
import { SelectOption } from "@/types/SelectOption";
import { HawaiifoodySearchForm } from "@/types/HawaiifoodySearchForm";

export default defineComponent({
  name: "HawaiifoodySearchFormDrawerContent",
  components: {
    DatePickerComp,
    MultiselectComp,
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => HawaiifoodySearchForm,
      default: () => new HawaiifoodySearchForm(),
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
    workdeplistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    workstatuslistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const edithawaiifoodysearchData = ref<HawaiifoodySearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      edithawaiifoodysearchData
    );

    return {
      edithawaiifoodysearchData,
    };
  },
});
</script>

<style lang="scss"></style>
