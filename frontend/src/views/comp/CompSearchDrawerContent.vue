<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md" v-if="editcompsearchData != null">
          <div class="col-2 q-pt-md q-pl-md">{{ t('compgroup') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcompsearchData.searchCompGroup"
              class="full-width"
              :options="compGroupList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('compsector') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcompsearchData.searchCompSector"
              class="full-width"
              :options="compSectorList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('krcomp') }}</div>
          <div class="col-4">
            <input-comp
              v-model="editcompsearchData.searchCompKor"
              class="full-width"
              clearable
              :label="t('krcomp')"
              outlined
              stack-label
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">{{ t('encomp') }}</div>
          <div class="col-4">
            <input-comp
              v-model="editcompsearchData.searchCompEng"
              class="full-width"
              clearable
              :label="t('encomp')"
              outlined
              stack-label
            />
          </div>
          <div class="col-2 q-pt-md q-pl-md">{{ t('complvl') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcompsearchData.searchCompRate"
              class="full-width"
              :options="compRateList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('couponuseyn') }}</div>
          <div class="col-4">
            <select-comp
              v-model="editcompsearchData.searchCouponYn"
              class="full-width"
              :options="couponYnList"
              outlined
            />
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('beginDt') }}</div>
          <div class="col-4 q-pl-md">
            <date-picker-comp
              v-model="editcompsearchData.searchBeginDt"
              class="full-width"
              clearable
              :label="t('beginDt')"
              outlined
            >
            </date-picker-comp>
          </div>

          <div class="col-2 q-pt-md q-pl-md">{{ t('endDt') }}</div>
          <div class="col-4 q-pl-md">
            <date-picker-comp
              v-model="editcompsearchData.searchEndDt"
              class="full-width"
              clearable
              :label="t('endDt')"
              outlined
            >
            </date-picker-comp>
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
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import InputComp from 'src/components/common/InputComp.vue';

//Service
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Type
import { CompSearchForm } from 'src/types/CompSearchForm';
import { SelectOption } from '@/types/SelectOption';

export default defineComponent({
  name: 'CompSearchFormDrawerContent',
  components: {
    DatePickerComp,
    SelectComp,
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CompSearchForm,
      default: () => new CompSearchForm(),
    },
    compSectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compGroupList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    couponYnList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const editcompsearchData = ref<CompSearchForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcompsearchData
    );

    return {
      t: i18n.global.t,
      editcompsearchData,
    };
  },
});
</script>

<style lang="scss"></style>
