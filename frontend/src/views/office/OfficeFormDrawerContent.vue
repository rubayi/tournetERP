<template>
  <card-comp-design title="Office Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-3">
            <select-comp
              v-model="editofficeformData.officeIsland"
              class="full-width"
              label="Island"
              :options="islandgroup"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editofficeformData.officedepartment"
              class="full-width"
              label="Department"
              :options="departmentgroup"
              outlined
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editofficeformData.officename"
              class="full-width"
              clearable
              label="Office Name"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editofficeformData.officeaddress"
              class="full-width"
              clearable
              label="Address"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editofficeformData.officetel"
              class="full-width"
              clearable
              label="Tel"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editofficeformData.officefax"
              class="full-width"
              clearable
              label="Fax"
              outlined
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col">
            <input-comp
              v-model="editofficeformData.officedescription"
              class="full-width"
              clearable
              label="Desciption"
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

//Type
import { OfficeForm } from "@/types/OfficeForm";
import { SelectOption } from "@/types/SelectOption";

//Service
import { CodeService } from "@/services/CodeService";

//Helper
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "OfficeFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => OfficeForm,
      default: () => new OfficeForm(),
    },
  },
  setup(props, { emit }) {
    const editofficeformData = ref<OfficeForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editofficeformData
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
      editofficeformData,
      islandgroup,
      departmentgroup,
    };
  },
});
</script>

<style lang="scss"></style>
