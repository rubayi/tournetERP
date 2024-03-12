<template>
  <card-comp-design title="Report Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-3">
            <select-comp
              v-model="editreportformData.reportType"
              class="full-width"
              label="Report Type"
              :options="reporttypegroup"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editreportformData.reportlevel"
              class="full-width"
              label="Report Level"
              :options="reportlevelgroup"
              outlined
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editreportformData.reportName"
              class="full-width"
              clearable
              label="Report Name"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editreportformData.reporttitle"
              class="full-width"
              clearable
              label="Report Title"
              outlined
            />
          </div>
          <div class="col-3">
            <number-comp
              v-model="editreportformData.sortord"
              class="full-width"
              label="Sort Order"
              :max-number="100"
              :min-number="0"
              outlined
              validation-message="Number must be [0-100]"
            />
          </div>
          <div class="col-3">
            <checkbox-comp
              v-model="editreportformData.expired"
              label="Expired"
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col">
            <input-comp
              v-model="editreportformData.methodName"
              class="full-width"
              clearable
              label="Method Name"
              outlined
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

//Component
import CardCompDesign from "@/components/common/CardCompDesign.vue";
import CheckboxComp from "@/components/common/CheckboxComp.vue";
import InputComp from "@/components/common/InputComp.vue";
import SelectComp from "@/components/common/SelectComp.vue";
import NumberComp from "@/components/common/NumberComp.vue";

//Type
import { ReportForm } from "@/types/ReportForm";
import { SelectOption } from "@/types/SelectOption";

//Service
import { CodeService } from "@/services/CodeService";
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "ReportFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    CheckboxComp,
    NumberComp,
  },
  props: {
    modelValue: {
      type: Object as () => ReportForm,
      default: () => new ReportForm(),
    },
  },
  setup(props, { emit }) {
    const editreportformData = ref<ReportForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editreportformData
    );

    // Loading Report Type Options
    const reporttypegroup = ref<SelectOption[]>([]);
    loadreporttypegroupOptions();
    function loadreporttypegroupOptions() {
      CodeService.getGroupCodeForm(4).then((response) => {
        reporttypegroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }

    // Loading Report Level Options
    const reportlevelgroup = ref<SelectOption[]>([]);
    loadreportlevelgroupOptions();
    function loadreportlevelgroupOptions() {
      CodeService.getGroupCodeForm(3).then((response) => {
        reportlevelgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }

    return {
      editreportformData,
      reporttypegroup,
      reportlevelgroup,
    };
  },
});
</script>

<style lang="scss"></style>
