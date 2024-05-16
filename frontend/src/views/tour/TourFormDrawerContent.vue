<template>
  <card-comp-design :title="t('manageTour')">
    <template #content>
      <q-card-section v-if="edittourformData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourArea"
              class="full-width"
              :label="t('tourArea')"
              :options="tourAreaList"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourAreaSub"
              class="full-width"
              :label="t('tourAreaSub')"
              :options="tourAreaSubList"
              outlined
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.tourKor"
              class="full-width"
              clearable
              :label="t('tourKor')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.tourEng"
              class="full-width"
              clearable
              :label="t('tourEng')"
              outlined
              required
            />
          </div>
          <!--유아 나이-->
          <div class="col-6">
            <select-comp
              v-model="edittourformData.minAge"
              class="full-width"
              :label="t('minAge')"
              :options="ageNumbers"
              outlined
            />
          </div>
          <!--어린이 나이-->
          <div class="col-6">
            <select-comp
              v-model="edittourformData.childAge"
              class="full-width"
              :label="t('childAge')"
              :options="ageNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.prepaidMethod"
              class="full-width"
              :label="t('prepaidMethod')"
              :options="prepaidHowList"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.tourOrd"
              class="full-width"
              :label="t('tourOrd')"
              :options="ordNumbers"
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
    tourAreaList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    tourAreaSubList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    sectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    prepaidHowList: {
      type: Array as () => SelectOption[],
      default: () => [],
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
    const ageNumbers = ref<SelectOption[]>([]);
    const ordNumbers = ref<SelectOption[]>([]);

    loadcdMonthOptions();

    function loadcdMonthOptions() {
      for (let i = 0; i <= 20; i++) {
        let tnum = i.toString().padStart(2, '0');
        monNumbers.value.push({ label: tnum, value: i });
      }
    }

    loadcdAgeOptions();
    function loadcdAgeOptions() {
      for (let i = 0; i <= 20; i++) {
        let tnum = i.toString();
        ageNumbers.value.push({ label: tnum, value: i });
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

    loadcdOrdOptions();
    function loadcdOrdOptions() {
      for (let i = 0; i <= 70; i++) {
        let tnum = i.toString();
        ordNumbers.value.push({ label: tnum, value: i });
      }
    }

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(515, useYnList, locale);

    return {
      t: i18n.global.t,
      edittourformData,
      monNumbers,
      yearNumbers,
      ageNumbers,
      ordNumbers,
      useYnList,
    };
  },
});
</script>

<style lang="scss"></style>
