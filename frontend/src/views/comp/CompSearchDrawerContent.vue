<template>
  <card-comp-design title="Hawaii Foody Order Filters">
    <template #content>
      <div class="q-pa-lg">
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Date</div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edittournetsearchData.searchsdate"
              class="full-width"
              clearable
              label="Search Begin"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-2 col-xs-2">
            <date-picker-comp
              v-model="edittournetsearchData.searchedate"
              class="full-width"
              clearable
              label="Search End"
              outlined
            >
            </date-picker-comp>
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edittournetsearchData.searchletterfrom"
              class="full-width"
              clearable
              label="From"
              outlined
              stack-label
            />
          </div>
          <div class="col-3 col-xs-3">
            <input-comp
              v-model="edittournetsearchData.searchletterto"
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
              v-model="edittournetsearchData.searchworkdep"
              class="full-width"
              :options="workdeplistgroup"
              outlined
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">Work Order No</div>
          <div class="col-4">
            <input-comp
              v-model="edittournetsearchData.searchworkno"
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
              v-model="edittournetsearchData.searchsubject"
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
              v-model="edittournetsearchData.searchremarks"
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
              v-model="edittournetsearchData.searchcomment"
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
              v-model="edittournetsearchData.searchreceiveddate"
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
              v-model="edittournetsearchData.searchdatecrewassigned"
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
              v-model="edittournetsearchData.searchdatecompleted"
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
              v-model="edittournetsearchData.searchposteddate"
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
              v-model="edittournetsearchData.searchdatesentbacktotraffic"
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
              v-model="edittournetsearchData.searchletterrouteno"
              class="full-width full-height"
              outlined
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-2 q-pt-md q-pl-md">Status</div>
          <div class="col-10 col-xs-10">
            <multiselect-comp
              v-model="edittournetsearchData.searchworkstatus"
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
              v-model="edittournetsearchData.searchcrewassigned"
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
              v-model="edittournetsearchData.searchreceivedby"
              class="full-width"
              :options="receivedbylistgroup"
              outlined
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">Posted by</div>
          <div class="col-4 col-xs-4">
            <multiselect-comp
              v-model="edittournetsearchData.searchpostedby"
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
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import DatePickerComp from "src/components/common/DatePickerComp.vue";
import MultiselectComp from "src/components/common/MultiselectComp.vue";
import InputComp from "src/components/common/InputComp.vue";

//Service
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

//Type
import { SelectOption } from "src/types/SelectOption";
import { CompSearchForm } from "src/types/CompSearchForm";

export default defineComponent({
  name: "CompSearchFormDrawerContent",
  components: {
    DatePickerComp,
    MultiselectComp,
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CompSearchForm,
      default: () => new CompSearchForm(),
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
