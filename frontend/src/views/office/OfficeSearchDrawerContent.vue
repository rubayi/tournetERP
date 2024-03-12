<template>
  <card-comp-design title="Office Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-6 col-xs-6">
            <select-comp
              v-model="editofficesearchData.searchIsland"
              class="full-width"
              label="Island"
              :options="islandgroup"
              outlined
            />
          </div>
          <div class="col-6 col-xs-6">
            <select-comp
              v-model="editofficesearchData.searchType"
              class="full-width"
              label="Office Department"
              :options="departmentgroup"
              outlined
            />
          </div>
          <div class="col-12 col-xs-12">
            <input-comp
              v-model="editofficesearchData.searchName"
              class="full-width"
              clearable
              label="Office Name"
              outlined
              required
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
import { OfficeSearchForm } from "@/types/OfficeSearchForm";

export default defineComponent({
  name: "OfficeSearchFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => OfficeSearchForm,
      default: () => new OfficeSearchForm(),
    },
  },
  setup(props, { emit }) {
    const editofficesearchData = ref<OfficeSearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editofficesearchData
    );

    // Loading Island Group Options
    const islandgroup = ref<SelectOption[]>([]);
    loadislandgroupOptions();
    function loadislandgroupOptions() {
      CodeService.getGroupCodeForm(1).then((response) => {
        islandgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }

    // Loading Department Options
    const departmentgroup = ref<SelectOption[]>([]);
    loaddepartmentgroupOptions();
    function loaddepartmentgroupOptions() {
      CodeService.getGroupCodeForm(2).then((response) => {
        departmentgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.codeSeq)
        );
      });
    }

    return {
      editofficesearchData,
      islandgroup,
      departmentgroup,
    };
  },
});
</script>

<style lang="scss"></style>
