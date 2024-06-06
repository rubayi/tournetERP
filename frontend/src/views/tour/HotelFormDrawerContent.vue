<template>
  <card-comp-design :title="t('manageTour')">
    <template #content>
      <q-card-section v-if="edittourformData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <input-comp
              v-model="edittourformData.checkinTime"
              class="full-width"
              clearable
              :label="t('checkinTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.checkoutTime"
              class="full-width"
              clearable
              :label="t('checkoutTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="edittourformData.resortFee"
              class="full-width"
              clearable
              :label="t('resortFee')"
              outlined
            />
          </div>
          <!--어린이 나이-->
          <div class="col-6">
            <select-comp
              v-model="edittourformData.childAgeBreakfast"
              class="full-width"
              :label="t('childAgeBreakfast')"
              :options="ageNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="edittourformData.couponYn"
              :label="t('couponuseyn')"
              class="full-width select-comp-padding"
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
import { defineComponent, ref, watch } from 'vue';
import i18n from 'src/i18n';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { HotelForm } from 'src/types/HotelForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

import {loadOptionsList} from "src/utils/commoncode/commonCode";

export default defineComponent({
  name: 'HotelFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => HotelForm,
      default: () => new HotelForm(),
    },

  },
  setup(props, { emit }) {
    const edittourformData = ref<HotelForm>();
    const locale = i18n.global.locale.value;

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      edittourformData
    );

    const ageNumbers = ref<SelectOption[]>([]);

    loadAgeOptions();
    function loadAgeOptions() {
      for (let i = 0; i <= 20; i++) {
        let tnum = i.toString();
        ageNumbers.value.push({ label: tnum, value: i });
      }
    }

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(35, useYnList, locale);

    return {
      t: i18n.global.t,
      edittourformData,
      ageNumbers,
      useYnList,
    };
  },
});
</script>

<style lang="scss"></style>
