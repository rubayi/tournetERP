<template>
  <card-comp-design :title="t('manageCreditCard')">
    <template #content>
      <q-card-section v-if="editcodeformData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.mngNameKor"
              class="full-width"
              clearable
              :label="t('mngNameKor')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.mngNameEng"
              class="full-width"
              clearable
              :label="t('mngNameEng')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.cardNumber"
              class="full-width"
              :label="t('carNum')"
              outlined
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.nameOnCard"
              class="full-width"
              clearable
              :label="t('nameOnCard')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="editcodeformData.expMonth"
              class="full-width"
              :label="t('expmonth')"
              :options="monNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="editcodeformData.expYear"
              class="full-width"
              :label="t('expyear')"
              :options="yearNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <date-picker-comp
              v-model="editcodeformData.beginDt"
              class="full-width"
              clearable
              outlined
              :label="t('beginDt')"
            />
          </div>
          <div class="col-6">
            <date-picker-comp
              v-model="editcodeformData.endDt"
              class="full-width"
              clearable
              outlined
              :label="t('endDt')"
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="editcodeformData.useYn"
              class="full-width"
              :label="t('usage')"
              :options="useYnList"
              outlined
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import i18n from 'src/i18n';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { CdcdForm } from 'src/types/CdcdForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
// Service
import { CodeService } from 'src/services/CodeService';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';

export default defineComponent({
  name: 'CdcdFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    DatePickerComp,
  },
  props: {
    modelValue: {
      type: Object as () => CdcdForm,
      default: () => new CdcdForm(),
    },
  },
  setup(props, { emit }) {
    const editcodeformData = ref<CdcdForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcodeformData
    );

    // Loading Group Code Options

    const yearNumbers = ref<SelectOption[]>([]);
    const monNumbers = ref<SelectOption[]>([]);

    loadcdMonthOptions();

    function loadcdMonthOptions() {
      for (let i = 0; i <= 20; i++) {
        let tnum = i.toString().padStart(2, '0');
        monNumbers.value.push({ label: tnum, value: parseInt(tnum) });
      }
    }

    loadcdYearOptions();
    function loadcdYearOptions() {
      let currentYear = +new Date().getFullYear();
      for (let t = currentYear; t <= currentYear + 20; t++) {
        let label = t.toString();
        yearNumbers.value.push({ label: label, value: t });
      }
    }

    loadCouponYnListOptions();
    const useYnList = ref<SelectOption[]>([]);
    function loadCouponYnListOptions() {
      CodeService.getGroupCodeForm(515).then((response) => {
        useYnList.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }
    return {
      t: i18n.global.t,
      editcodeformData,
      monNumbers,
      yearNumbers,
      useYnList,
    };
  },
});
</script>

<style lang="scss"></style>
