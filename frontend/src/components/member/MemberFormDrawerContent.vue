<template>
  <card-comp-design title="Member Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-sm">
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.userID"
              class="full-width"
              clearable
              label="User ID"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-if="!editmemberformData.memID"
              v-model="editmemberformData.password"
              class="full-width"
              clearable
              label="Password"
              outlined
            />
            <input-comp
              v-if="editmemberformData.memID"
              v-model="editmemberformData.oldPassword"
              class="full-width"
              clearable
              label="Password"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editmemberformData.officeid"
              class="full-width"
              label="Office"
              :options="officegroup"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.position"
              class="full-width"
              clearable
              label="Position"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.firstName"
              class="full-width"
              clearable
              label="First Name"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.midName"
              class="full-width"
              clearable
              label="Mid Name"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.lastName"
              class="full-width"
              clearable
              label="Last Name"
              outlined
            />
          </div>
          <div class="col-3">
            <checkbox-comp
              v-model="editmemberformData.expiredUser"
              label="Expired"
            />
            <checkbox-comp
              v-model="editmemberformData.displayuser"
              label="Display"
            />
            <checkbox-comp
              v-model="editmemberformData.administrator"
              label="Administrator"
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.phoneNumber"
              class="full-width"
              clearable
              label="Phone"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editmemberformData.email"
              class="full-width"
              clearable
              label="Email"
              outlined
            />
          </div>
          <div class="col-6">
            <multiselect-comp
              v-model="editmemberformData.reportLevelList"
              class="full-width"
              :options="reportlevelgroup"
              outlined
              placeholder="Report Level"
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
import MultiselectComp from "@/components/common/MultiselectComp.vue";
import CheckboxComp from "@/components/common/CheckboxComp.vue";
//TYpe
import { MemberForm } from "@/types/MemberForm";
import { SelectOption } from "@/types/SelectOption";
//Service
import { CodeService } from "@/services/CodeService";
import { OfficeService } from "@/services/OfficeService";
//Helper
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "MemberFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    MultiselectComp,
    CheckboxComp,
  },
  props: {
    modelValue: {
      type: Object as () => MemberForm,
      default: () => new MemberForm(),
    },
  },
  setup(props, { emit }) {
    const editmemberformData = ref<MemberForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editmemberformData
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

    const reportlevelgroup = ref<SelectOption[]>([]);
    loadreportlevelgroupOptions();
    function loadreportlevelgroupOptions() {
      CodeService.getGroupCodeForm(3).then((response) => {
        reportlevelgroup.value = response.map(
          (x) => new SelectOption(x.codename, "_" + x.codeSeq)
        );
      });
    }

    return {
      editmemberformData,
      officegroup,
      reportlevelgroup,
    };
  },
});
</script>

<style lang="scss"></style>
