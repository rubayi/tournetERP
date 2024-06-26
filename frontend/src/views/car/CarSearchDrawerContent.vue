<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md" v-if="editcarsearchData != null">
          <div class="col-2 q-pt-md q-pl-md">{{ t('compsector') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcarsearchData.searchCarType"
              class="full-width"
              :options="carTypeList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('compgroup') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcarsearchData.searchCarBrand"
              class="full-width"
              :options="carBrandList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('krcomp') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcarsearchData.searchCarManager"
              class="full-width"
              :options="carBrandList"
              outlined
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">{{ t('encomp') }}</div>
          <div class="col-4">
            <input-comp
              v-model="editcarsearchData.searchCarVin"
              class="full-width"
              clearable
              :label="t('carVin')"
              outlined
              stack-label
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">{{ t('complvl') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcarsearchData.searchPeople"
              class="full-width"
              :options="carBrandList"
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
//Lang
import i18n from 'src/i18n';
//Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import InputComp from 'src/components/common/InputComp.vue';

//Service
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Type
import { CarSearchForm } from 'src/types/CarSearchForm';
import { SelectOption } from 'src/types/SelectOption';

export default defineComponent({
  name: 'CarSearchFormDrawerContent',
  components: {
    SelectComp,
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CarSearchForm,
      default: () => new CarSearchForm(),
    },
    carTypeList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    carBrandList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },

  },
  setup(props, { emit }) {
    const editcarsearchData = ref<CarSearchForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcarsearchData
    );

    return {
      t: i18n.global.t,
      editcarsearchData,
    };
  },
});
</script>

<style lang="scss"></style>
