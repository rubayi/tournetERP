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
                  v-model="edittournetformData.workdep"
                  class="full-width"
                  label="Work Order Office"
                  :options="workdeplistgroup"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.workno"
                  class="full-width"
                  clearable="false"
                  label="Work Order No"
                  max-length="9"
                  :readonly="readonlybtn"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.letterfrom"
                  class="full-width"
                  clearable
                  label="FROM:"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.letterto"
                  class="full-width"
                  clearable
                  label="TO:"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="edittournetformData.letterattn"
                  class="full-width"
                  clearable
                  label="ATTN:"
                  :readonly="readonlybtn"
                />
              </div>
            </div>

            <div class="row q-col-gutter-md">
              <div class="col-10">
                <input-comp
                  v-model="edittournetformData.workrouteno"
                  autogrow
                  class="full-width full-height"
                  label="Route :"
                  :readonly="readonlybtn"
                />
              </div>
              <div class="col-2">
                <select-comp
                        v-model="edittournetformData.langtype.id"
                        label="Language"
                        :options="langtypelistgroup"
                />

              </div>
            </div>
          </div>
          <div class="col-6">
            <div class="row q-col-gutter-md">
              <div class="col-12">
                <input-comp
                  v-model="edittournetformData.lettersubject"
                  autogrow
                  class="full-width full-height"
                  label="Subject:"
                  outlined
                  :readonly="readonlybtn"
                />
              </div>
            </div>
            <div class="row q-col-gutter-md">
              <div class="col-12">
                <text-area-comp
                  v-model="edittournetformData.remarks"
                  class="full-width full-height"
                  clearable
                  label="Remarks:"
                  max-length="10000"
                  outlined
                  :readonly="readonlybtn"
                />
              </div>
            </div>
          </div>
        </div>

        <div class="row q-col-gutter-xs">
          <div class="col-6">
            <div class="fieldset03 q-mt-xs q-mb-none">
              <h1><span>We are forwarding the following :</span></h1>
              <div class="row q-col-gutter-xs">
                <div class="col-5 q-pt-sm">
                  <q-btn
                    v-show="!readonlybtn"
                    label="+ Add"
                    size="sm"
                    @click="addContactList"
                  />
                </div>
                <div class="col-auto">
                  <q-checkbox
                    v-model="edittournetformData.attached"
                    :disable="readonlybtn"
                    label="Attached"
                    val="true"
                  />
                </div>
                <div class="col-auto">
                  <q-checkbox
                    v-model="edittournetformData.separatesheet"
                    :disable="readonlybtn"
                    label="Under Separate Sheet"
                    val="true"
                  />
                </div>
              </div>

              <div class="no-padding q-mb-md">
                <q-markup-table
                  id="copiesactionTable"
                  bordered
                  flat
                  separator="vertical"
                >
                  <thead class="bg-blue-grey-1">
                    <tr>
                      <th class="text-center no-padding" style="width: 60px">
                        COPIES
                      </th>
                      <th class="text-center no-padding">DESCRIPTION</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="item in edittournetformData.ContactList"
                      :key="item.idx"
                    >
                      <td class="text-center q-pl-sm q-pr-sm no-padding">
                        <input-comp
                          v-model="item.workcopies"
                          class="full-width q-pl-md q-pr-md text-center"
                          clearable="false"
                          :readonly="readonlybtn"
                        />
                      </td>
                      <td class="text-left q-pl-sm q-pr-sm no-padding">
                        <input-comp
                          v-model="item.workdescription"
                          class="full-width q-pl-md q-pr-md"
                          clearable="false"
                          :readonly="readonlybtn"
                        />
                      </td>
                    </tr>
                  </tbody>
                </q-markup-table>
              </div>

              <div class="row q-col-gutter-md">
                <div class="col-3">Copies sent to:</div>
                <div class="col-9">
                  <input-comp
                    v-model="edittournetformData.copiessendto"
                    class="full-width"
                    clearable
                    label=""
                    outlined
                    :readonly="readonlybtn"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="col-6">
            <div class="fieldset03 q-mt-xs q-mb-none">
              <h1><span>These are transmitted as checked below :</span></h1>
              <div class="row">
                <q-checkbox
                  v-for="(option, index) in workstatuslistgroup"
                  v-model="statusselection"
                  class="col-6"
                  :label="option.label"
                  :val="option.value"
                  :key="index"
                ></q-checkbox>
                <div
                  v-show="edittournetformData.workstatus == 68"
                  class="col-3 offset-2"
                >
                  <input-comp
                    v-model="edittournetformData.resubmitcopies"
                    class="full-width"
                    clearable
                    label="Copies"
                    outlined
                    :readonly="readonlybtn"
                  />
                </div>
                <div
                  v-show="edittournetformData.workstatus == 69"
                  class="col-6 offset-6"
                >
                  <date-picker-comp
                    v-model="suspensedateVal"
                    class="full-width"
                    clearable
                    label="Suspense Date"
                    outlined
                    :readonly="readonlybtn"
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
import TextAreaComp from "src/components/common/TextAreaComp.vue";
import SelectComp from "src/components/common/SelectComp.vue";
// Type
import { CompForm } from "src/types/CompForm";
import { TournetDocumentsgForm } from "src/types/TournetDocumentsgForm";
import { SelectOption } from "src/types/SelectOption";
import { TournetMultiCheckbox } from "src/types/TournetMultiCheckbox";

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
    TextAreaComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    data: {
      type: Object as () => CompForm,
      default: () => new CompForm(),
    },
    workstatues: {
      type: Array as () => TournetMultiCheckbox[],
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
    workdeplistgroup: {
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
    const ContactList = ref<TournetDocumentsgForm[]>([]);
    const refworkstatues = ref<TournetMultiCheckbox[]>([]);
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
            let tempmulticheckbox = new TournetMultiCheckbox();
            tempmulticheckbox.codevalue = x;
            refworkstatues.value.push(tempmulticheckbox);
          }
        }
      }
    );

    watch(
      () => props.data,
      (newValue) => {
        loadworkstatues();

      }
    );
    function resetreadData() {
      statusselection.value = [];
      if (
        store.getters.currentUserHasApplicationPermission("COMP_R") ||
        store.getters.currentUserHasApplicationPermission("COMP_W")
      ) {
        readonlybtn.value = false;
      } else {
        readonlybtn.value = true;
      }
    }
    function addContactList() {
      ContactList.value.push(new TournetDocumentsgForm());
    }

    return {
      edittournetformData,
      addContactList,
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
