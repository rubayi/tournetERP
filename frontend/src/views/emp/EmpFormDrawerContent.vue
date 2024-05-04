<template>
  <card-comp-design title="Employer Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empKor"
              class="full-width"
              clearable
              label="Korean Name"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empEng"
              class="full-width"
              clearable
              label="English Name"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.username"
              class="full-width"
              clearable
              label="Username"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.password"
              class="full-width"
              clearable
              label="Password"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empComp"
              class="full-width"
              label="Company"
              :options="CompList"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empOffice"
              class="full-width"
              label="Office"
              :options="OfficeList"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empDiv"
              class="full-width"
              label="Division (Department)"
              :options="DivisionList"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empTitle"
              class="full-width"
              label="Job Title"
              :options="JobTitleList"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empRole"
              class="full-width"
              label="Job Role"
              :options="JobRoleList"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empStatus"
              class="full-width"
              label="Work Status"
              :options="WorkStatusList"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empEmail"
              class="full-width"
              clearable
              label="Personal Email"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empEmailBook"
              class="full-width"
              clearable
              label="Booking(Work) Email"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empPhone"
              class="full-width"
              clearable
              label="Phone Number"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empWorkPhone"
              class="full-width"
              clearable
              label="Work(Extention) Number"
              outlined
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empAddress"
              class="full-width"
              clearable
              label="Address"
              outlined
            />
          </div>
          <div class="col-3">
            <date-picker-comp
              v-model="editEmpFormData.empDob"
              class="full-width"
              clearable
              label="Date of Birth"
              outlined
            />
          </div>

          <div class="col-3">
            <date-picker-comp
              v-model="editEmpFormData.empBeginDt"
              class="full-width"
              clearable
              label="Hire Date"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <date-picker-comp
              v-model="editEmpFormData.empEndDt"
              class="full-width"
              clearable
              label="Resign Date"
              outlined
              required
            />
          </div>

          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empMemo"
              class="full-width"
              clearable
              label="Memo"
              outlined
            />
          </div>
          <div class="col-3">
            <input-color-comp
              v-model="editEmpFormData.backColor"
              class="full-width"
              clearable
              label="Emp Background Colo"
            />
          </div>
          <div class="col-3">
            <input-color-comp
              v-model="editEmpFormData.fontColor"
              class="full-width"
              clearable
              label="Emp Font(Name) Colo"
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import InputColorComp from 'src/components/common/InputColorComp.vue';
// Service
import { CodeService } from 'src/services/CodeService';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpForm } from 'src/types/EmpForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    DatePickerComp,
    InputColorComp,
  },
  props: {
    modelValue: {
      type: Object as () => EmpForm,
      default: () => new EmpForm(),
    },
  },
  setup(props, { emit }) {
    const editEmpFormData = ref<EmpForm>(new EmpForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editEmpFormData
    );

    const DivisionList = ref<SelectOption[]>([]);
    loadDivision();
    function loadDivision() {
      CodeService.getGroupCodeForm(19).then((response) => {
        DivisionList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    const CompList = ref<SelectOption[]>([]);
    loadCopmany();
    function loadCopmany() {
      CodeService.getGroupCodeForm(21).then((response) => {
        CompList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    const OfficeList = ref<SelectOption[]>([]);
    loadOffice();
    function loadOffice() {
      CodeService.getGroupCodeForm(2).then((response) => {
        CompList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    const JobTitleList = ref<SelectOption[]>([]);
    loadJobTitle();
    function loadJobTitle() {
      CodeService.getGroupCodeForm(17).then((response) => {
        CompList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    const JobRoleList = ref<SelectOption[]>([]);
    loadJobRole();
    function loadJobRole() {
      CodeService.getGroupCodeForm(18).then((response) => {
        CompList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    const WorkStatusList = ref<SelectOption[]>([]);
    loadWorkStatus();
    function loadWorkStatus() {
      CodeService.getGroupCodeForm(15).then((response) => {
        CompList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    return {
      editEmpFormData,
      DivisionList,
      CompList,
      OfficeList,
      JobTitleList,
      JobRoleList,
      WorkStatusList,
    };
  },
});
</script>

<style lang="scss"></style>
