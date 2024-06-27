<template>
  <div class="row">
    <div class="col-8 q-pr-sm">
      <card-comp-design :title="t('basicIformation')">
        <template #content>
          <q-card-section>
            <div v-if="carFormData != null" class="row q-col-gutter-md">
              <div class="col-4">
                <select-comp
                  v-model="carFormData.carBrand"
                  :label="t('carBrand')"
                  class="full-width select-comp-padding"
                  :options="carBrandList"
                  outlined
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="carFormData.carType"
                  class="full-width select-comp-padding"
                  :label="t('carType')"
                  :options="carTypeList"
                  outlined
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="carFormData.people"
                  :label="t('people')"
                  class="full-width select-comp-padding"
                  :options="peopleList"
                  outlined
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="carFormData.licensePlate"
                  class="full-width"
                  clearable
                  :label="t('licensePlate')"
                  outlined
                />
              </div>
              <div class="col-4">
                <input-comp
                  v-model="carFormData.carVin"
                  class="full-width"
                  clearable
                  :label="t('carVin')"
                  outlined
                />
              </div>
              <div class="col-4">
                <select-comp
                  v-model="carFormData.carYear"
                  :label="t('carYear')"
                  class="full-width select-comp-padding"
                  :options="carYearList"
                  outlined
                />
              </div>
              <div class="col-4">
              <!--if hotel-->
                <select-comp
                  v-model="carFormData.carManager"
                  :label="t('carManager')"
                  class="full-width select-comp-padding"
                  :options="carManagerList"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="carFormData.carEndDt"
                  class="full-width"
                  clearable
                  :label="t('carEndDt')"
                  outlined
                />
              </div>

              <div class="col-12">
                <input-comp
                  v-model="carFormData.memo"
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

  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { CarForm } from 'src/types/CarForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Lang
import i18n from 'src/i18n';

export default defineComponent({
  name: 'CarFormDrawerContent',
  components: {
    CardCompDesign,
    SelectComp,
    InputComp,
    DatePickerComp,
  },
  props: {
    contentData: {
      type: Object as () => CarForm,
      default: () => new CarForm(),
    },
    carTypeList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carBrandList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carManagerList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const carFormData = ref<CarForm>(new CarForm());
    const readonlybtn = ref<boolean>(true);

    const showflag = ref<number | 0>(0);

    //* set year list
    const thisYear = new Date().getFullYear();
    const initYear = thisYear - 25;
    const endYear = thisYear + 3;

    const carYearList = ref<SelectOption[]>([]);

    loadCarYearOptions();
    function loadCarYearOptions() {
      for (let i = initYear; i <= endYear; i++) {
        let tnum = i.toString();
        carYearList.value.push({ label: tnum, value: i });
      }
    }

    //* set year list

    const peopleList = ref<SelectOption[]>([]);

    loadPeopleOptions();
    function loadPeopleOptions() {
      for (let i = 30; i>= 1; i--) {
        let tnum = i.toString();
        peopleList.value.push({ label: tnum, value: i });
      }
    }

    useSyncModelValue(
      props,
      'contentData',
      emit,
      'update:contentData',
      carFormData
    );


    return {
      t: i18n.global.t,
      carFormData,
      readonlybtn,
      showflag,
      carYearList,
      peopleList
    };
  },
});
</script>

<style lang="scss">
.fieldset03 > h1:after {
  margin: 0 1px 0 20em;
}
.select-comp-padding {
  padding-bottom: 20px;
}
.custom-file-upload {
  align-items: center;
  background-color: #4482df;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  flex-shrink: 0;
  font-family: 'RM Neue', sans-serif;
  line-height: 2.1;
  padding: 10px 21px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}
.custom-file-upload:active {
  background-color: #1c84ff;
}
.custom-file-upload:hover {
  background-color: #4e9bfa;
}
input[type='file'] {
  display: none;
}
.image-max-width {
  width: 100px;
  height: 100px;
  border: 1px solid #b4b4b4;
  border-radius: 4px;
}
</style>
