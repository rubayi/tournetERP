<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editempsearchData.searchEmpDiv"
              class="full-width select-comp-padding"
              :label="t('byGroup')"
              :options="groupNames"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editempsearchData.searchEmpKor"
              class="full-width"
              clearable
              :label="t('byNameKr')"
              outlined
              required
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editempsearchData.searchEmpEng"
              class="full-width"
              clearable
              :label="t('byNameEn')"
              outlined
              required
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
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
//Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpSearchForm } from 'src/types/EmpSearchForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpSearchFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => EmpSearchForm,
      default: () => new EmpSearchForm(),
    },
    groupNames: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const editempsearchData = ref<EmpSearchForm>(new EmpSearchForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editempsearchData
    );

    return {
      t: i18n.global.t,
      editempsearchData,
    };
  },
});
</script>

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
</style>
