<template>
  <div class="row">
    <div class="col-6 q-pr-sm">
      <card-comp-design :title="t('empInfo')">
        <template #content>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empEng"
                  class="full-width"
                  clearable
                  :label="t('enname')"
                  outlined
                  required
                />
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empKor"
                  class="full-width"
                  clearable
                  :label="t('krname')"
                  outlined
                  required
                />
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.username"
                  class="full-width"
                  clearable
                  :label="t('username')"
                  outlined
                  required
                />
              </div>
              <div v-if="pwChangeYN" class="col-3">
                <input-comp
                  v-model="editEmpFormData.password"
                  class="full-width"
                  clearable
                  :displayValue="false"
                  :label="t('pw')"
                  outlined
                  required
                  type="password"
                />
              </div>
              <div class="col-3 q-pt-lg">
                <input type="checkbox" v-model="pwChangeYN" />
                <label for="guide-checkbox" class="q-pa-sm">
                  {{ t(`changePW`) }}</label
                >
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empEmail"
                  class="full-width"
                  clearable
                  :label="t('Pemail')"
                  outlined
                />
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empPhone"
                  class="full-width"
                  clearable
                  :label="t('phone')"
                  outlined
                />
              </div>
              <div class="col-8">
                <input-comp
                  v-model="editEmpFormData.empAddress"
                  class="full-width"
                  clearable
                  :label="t('address')"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="editEmpFormData.empDob"
                  class="full-width"
                  clearable
                  :label="t('dob')"
                  outlined
                />
              </div>
              <div class="col-12">
                <input-comp
                  v-model="editEmpFormData.empMemo"
                  class="full-width"
                  clearable
                  :label="t('memo')"
                  outlined
                />
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>

    <div class="col-6 q-pl-sm">
      <card-comp-design :title="t('workInfo')">
        <template #content>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <!-- <div class="col-12 noMarginPadd" /> -->
              <div class="col-4">
                <select-comp
                  v-model="editEmpFormData.empComp"
                  class="full-width select-comp-padding"
                  :label="t('compreport')"
                  :options="CompList"
                  outlined
                  required
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="editEmpFormData.empOffice"
                  class="full-width select-comp-padding"
                  :label="t('office')"
                  :options="OfficeList"
                  outlined
                  required
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="editEmpFormData.empDiv"
                  class="full-width select-comp-padding"
                  :label="t('divname')"
                  :options="DivisionList"
                  outlined
                  required
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="editEmpFormData.empRole"
                  class="full-width select-comp-padding"
                  :label="t('jobRole')"
                  :options="JobRoleList"
                  outlined
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="editEmpFormData.empStatus"
                  class="full-width select-comp-padding"
                  :label="t('empstatus')"
                  :options="WorkStatusList"
                  outlined
                  required
                />
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empEmailBook"
                  class="full-width"
                  clearable
                  :label="t('email')"
                  outlined
                  required
                />
              </div>
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empWorkPhone"
                  class="full-width"
                  clearable
                  :label="t('workphone')"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="editEmpFormData.empBeginDt"
                  class="full-width"
                  clearable
                  :label="t('WSD')"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="editEmpFormData.empEndDt"
                  class="full-width"
                  clearable
                  :label="t('WED')"
                  outlined
                />
              </div>
              <div class="col-4 q-pt-lg">
                <input type="checkbox" v-model="guideYN" />
                <label for="guide-checkbox" class="q-pa-sm">{{
                  t(`guideColor`)
                }}</label>
              </div>
              <div v-if="guideYN" class="col-4">
                <input-color-comp
                  v-model="editEmpFormData.backColor"
                  class="full-width"
                  clearable
                  :label="t('bgColor')"
                />
              </div>
              <div v-if="guideYN" class="col-4">
                <input-color-comp
                  v-model="editEmpFormData.fontColor"
                  class="full-width"
                  clearable
                  :label="t('fontColor')"
                />
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
//Lang
import i18n from 'src/i18n';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import InputColorComp from 'src/components/common/InputColorComp.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpForm } from 'src/types/EmpForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';

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
    const locale = i18n.global.locale.value;
    const guideYN = ref<boolean>(false);
    const pwChangeYN = ref<boolean>(false);
    const editEmpFormData = ref<EmpForm>(new EmpForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editEmpFormData
    );

    const DivisionList = ref<SelectOption[]>([]);
    loadOptionsList(19, DivisionList, locale);

    const CompList = ref<SelectOption[]>([]);
    loadOptionsList(21, CompList, locale);

    const OfficeList = ref<SelectOption[]>([]);
    loadOptionsList(2, OfficeList, locale);

    const JobRoleList = ref<SelectOption[]>([]);
    loadOptionsList(18, JobRoleList, locale);

    const WorkStatusList = ref<SelectOption[]>([]);
    loadOptionsList(15, WorkStatusList, locale);

    return {
      t: i18n.global.t,
      guideYN,
      pwChangeYN,
      editEmpFormData,
      DivisionList,
      CompList,
      OfficeList,
      JobRoleList,
      WorkStatusList,
    };
  },
});
</script>

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
.noMarginPadd {
  margin: 0px;
  padding: 0px;
}
</style>
