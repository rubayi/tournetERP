<template>
  <card-comp-design :title="t('tourService')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">

          <div class="col-12">
            <input-comp
              v-model="editFormData.serviceNameKor"
              class="full-width"
              clearable
              :label="t('serviceNameKor')"
              outlined
              required
            />
          </div>

          <div class="col-12">
            <input-comp
              v-model="editFormData.serviceNameEng"
              class="full-width select-padding"
              :label="t('serviceNameEng')"
              outlined
            />
          </div>

          <div class="col-12">
            <text-area-comp
              v-model="editFormData.descKor"
              class="full-width"
              clearable
              :label="t('descKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="editFormData.descEng"
              class="full-width select-padding"
              :label="t('descEng')"
              outlined
            />
          </div>


          <div class="col-4">
            <select-comp
              v-model="editFormData.adults"
              class="full-width"
              :label="t('adultsNum')"
              :options="numbers"
              outlined
            />
          </div>

          <div class="col-4">
            <select-comp
              v-model="editFormData.children"
              class="full-width"
              :label="t('childrenNum')"
              :options="numbers"
              outlined
            />
          </div>

          <div class="col-4">
            <select-comp
              v-model="editFormData.maxPeople"
              class="full-width"
              :label="t('maxPeopleNum')"
              :options="numbers"
              outlined
            />
          </div>

          <div class="col-4">
            <select-comp
              v-model="editFormData.pickupYn"
              class="full-width select-padding"
              :label="t('pickupYn')"
              :options="useYnList"
              outlined
              required
            />
          </div>

          <div class="col-4">
            <select-comp
              v-model="editFormData.mealYn"
              class="full-width select-padding"
              :label="t('mealYn')"
              :options="useYnList"
              outlined
              required
            />
          </div>

          <div class="col-4">
            <select-comp
              v-model="editFormData.showingOrder"
              class="full-width"
              :label="t('showingOrder')"
              :options="numbers"
              outlined
            />
          </div>

          <div class="col-3">
            <date-picker-comp
              v-model="editFormData.beginDt"
              class="full-width"
              clearable
              outlined
              :label="t('beginDt')"
            />
          </div>
          <div class="col-3">
            <date-picker-comp
              v-model="editFormData.endDt"
              class="full-width"
              clearable
              outlined
              :label="t('endDt')"
            />
          </div>

          <div class="col-3">
            <date-picker-comp
              v-model="editFormData.bookBeginDt"
              class="full-width"
              clearable
              outlined
              :label="t('bookBeginDt')"
            />
          </div>
          <div class="col-3">
            <date-picker-comp
              v-model="editFormData.bookEndDt"
              class="full-width"
              clearable
              outlined
              :label="t('bookEndDt')"
            />
          </div>


        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>
<script lang="ts">
import { ref, defineComponent } from 'vue';
// Components
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import InputComp from 'src/components/common/InputComp.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { TourServiceForm } from 'src/types/TourServiceForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import i18n from 'src/i18n';
import DatePickerComp from "src/components/common/DatePickerComp.vue";
import TextAreaComp from "src/components/common/TextAreaComp.vue";

export default defineComponent({
  name: 'TourServiceDrawerContent',
  components: {
    TextAreaComp,
    DatePickerComp,
    CardCompDesign,
    SelectComp,
    InputComp
  },
  props: {
    modelValue: {
      type: Object as () => TourServiceForm,
      default: () => new TourServiceForm(),
    },
    serviceUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const editFormData = ref<TourServiceForm>(new TourServiceForm());

    const setServiceUuid = () => {
      editFormData.value.serviceUuid = props.serviceUuid;
    };
    setServiceUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editFormData
    );

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(35, useYnList, locale);

    const numbers = ref<SelectOption[]>([]);
    loadNumOptions();
    function loadNumOptions() {
      for (let i = 0; i <= 70; i++) {
        let tnum = i.toString();
        numbers.value.push({ label: tnum, value: i });
      }
    }

    return {
      t: i18n.global.t,
      useYnList,
      editFormData,
      numbers
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
