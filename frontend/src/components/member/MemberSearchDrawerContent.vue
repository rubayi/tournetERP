<template>
  <card-comp-design title="Member Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-2 col-xs-2">
            <select-comp
              v-model="editmembersearchData.searchComid"
              class="full-width"
              label="Office"
              :options="officegroup"
              outlined
            />
          </div>

          <div class="col-2 col-xs-2">
            <input-comp
              v-model="editmembersearchData.searchName"
              class="full-width"
              clearable
              label="Member ID"
              outlined
            />
          </div>
          <div class="col-4 col-xs-4">
            <input-comp
              v-model="editmembersearchData.searchfirstname"
              class="full-width"
              clearable
              label="First Name"
              outlined
            />
          </div>
          <div class="col-4 col-xs-4">
            <input-comp
              v-model="editmembersearchData.searchlastname"
              class="full-width"
              clearable
              label="Last Name"
              outlined
            />
          </div>
          <div class="col-4 col-xs-4">
            <input-comp
              v-model="editmembersearchData.searchposition"
              class="full-width"
              clearable
              label="Position"
              outlined
            />
          </div>
          <div class="col-4 col-xs-4">
            <input-comp
              v-model="editmembersearchData.searchemail"
              class="full-width"
              clearable
              label="Email"
              outlined
            />
          </div>
          <div class="col-4 col-xs-4">
            <input-comp
              v-model="editmembersearchData.searchphone"
              class="full-width"
              clearable
              label="Phone"
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
import { OfficeService } from "@/services/OfficeService";

//Helpers
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

//Type
import { SelectOption } from "@/types/SelectOption";
import { MemberSearchForm } from "@/types/MemberSearchForm";

export default defineComponent({
  name: "MemberSearchFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => MemberSearchForm,
      default: () => new MemberSearchForm(),
    },
  },
  setup(props, { emit }) {
    const editmembersearchData = ref<MemberSearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editmembersearchData
    );

    // Loading Office Group Options
    const officegroup = ref<SelectOption[]>([]);
    loadofficegroupOptions();
    function loadofficegroupOptions() {
      OfficeService.getGroupOfficeForm().then((response) => {
        officegroup.value = response.map(
          (x) => new SelectOption(x.officename, x.officeid)
        );
      });
    }

    const departmentgroup = ref<SelectOption[]>([]);
    loaddepartmentgroupOptions();
    function loaddepartmentgroupOptions() {
      CodeService.getGroupCodeForm(2).then((response) => {
        departmentgroup.value = response.map(
          (x) => new SelectOption(x.codename, x.code2)
        );
      });
    }

    return {
      editmembersearchData,
      officegroup,
      departmentgroup,
    };
  },
});
</script>

<style lang="scss"></style>
