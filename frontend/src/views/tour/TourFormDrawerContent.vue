<template>
  <card-comp-design :title="t('manageCreditCard')">
    <template #content>
      <q-card-section v-if="edittourformData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourArea"
              class="full-width"
              :label="t('expmonth')"
              :options="monNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourAreaSub"
              class="full-width"
              :label="t('expmonth')"
              :options="monNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.tourKor"
              class="full-width"
              clearable
              :label="t('mngNameKor')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.tourEng"
              class="full-width"
              clearable
              :label="t('mngNameEng')"
              outlined
              required
            />
          </div>

          <div class="col-6">
            <input-comp
              v-model="edittourformData.codeAbb"
              class="full-width"
              clearable
              :label="t('nameOnCard')"
              outlined
              required
            />
          </div>

          <div class="col-6">
            <select-comp
              v-model="edittourformData.prepaidMethod"
              class="full-width"
              :label="t('expyear')"
              :options="yearNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <date-picker-comp
              v-model="edittourformData.beginDt"
              class="full-width"
              clearable
              outlined
              :label="t('beginDt')"
            />
          </div>
          <div class="col-6">
            <date-picker-comp
              v-model="edittourformData.endDt"
              class="full-width"
              clearable
              outlined
              :label="t('endDt')"
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourArea"
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
import { TourForm } from 'src/types/TourForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import {loadOptionsList} from "src/utils/commoncode/commonCode";

export default defineComponent({
  name: 'TourFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    DatePickerComp,
  },
  props: {
    modelValue: {
      type: Object as () => TourForm,
      default: () => new TourForm(),
    },
  },
  setup(props, { emit }) {
    const edittourformData = ref<TourForm>();
    const locale = i18n.global.locale.value;

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      edittourformData
    );

    // Loading Group Tour Options

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

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(515, useYnList, locale);

    return {
      t: i18n.global.t,
      edittourformData,
      monNumbers,
      yearNumbers,
      useYnList,
    };
  },
});
</script>

<style lang="scss"></style>
