<template>
  <card-comp-design title="Report Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-6 col-xs-6">
            <select-comp
              v-model="editreportsearchData.searchReportGroup"
              class="full-width"
              label="Report Type"
              :options="reporttypegroup"
              outlined
            />
          </div>
          <div class="col-6 col-xs-6">
            <select-comp
              v-model="editreportsearchData.searchReportType"
              class="full-width"
              label="Report Level"
              :options="reportlevelgroup"
              outlined
            />
          </div>
          <div class="col-12 col-xs-12">
            <input-comp
              v-model="editreportsearchData.searchName"
              class="full-width"
              clearable
              label="Report Name"
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
import InputComp from "@/components/common/InputComp.vue";
import SelectComp from "@/components/common/SelectComp.vue";

//Service
import { CodeService } from "@/services/CodeService";
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

//Type
import { SelectOption } from "@/types/SelectOption";
import { ReportSearchForm } from "@/types/ReportSearchForm";

export default defineComponent({
  name: "ReportSearchFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => ReportSearchForm,
      default: () => new ReportSearchForm(),
    },
  },
  setup(props, { emit }) {
    const editreportsearchData = ref<ReportSearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editreportsearchData
    );

    // Loading Report Type Options
    const reporttypegroup = ref<SelectOption[]>([]);
    loadreporttypegroupOptions();
    function loadreporttypegroupOptions() {
      CodeService.getGroupCodeForm(4).then((response) => {
        reporttypegroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    // Loading Report Level Options
    const reportlevelgroup = ref<SelectOption[]>([]);
    loadreportlevelgroupOptions();
    function loadreportlevelgroupOptions() {
      CodeService.getGroupCodeForm(3).then((response) => {
        reportlevelgroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    return {
      editreportsearchData,
      reporttypegroup,
      reportlevelgroup,
    };
  },
});
</script>

<style lang="scss"></style>
