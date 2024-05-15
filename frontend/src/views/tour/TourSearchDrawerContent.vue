<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchMngNameKor"
              class="full-width"
              :label="t('mngNameKor')"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchMngNameEng"
              class="full-width"
              clearable
              :label="t('mngNameEng')"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCardNumber"
              class="full-width"
              clearable
              :label="t('carNum')"
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
//Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
//Service
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
//Type
import { CdcdSearchForm } from 'src/types/CdcdSearchForm';
import i18n from 'src/i18n';

export default defineComponent({
  name: 'CdcdSearchFormDrawerContent',
  components: {
    InputComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CdcdSearchForm,
      default: () => new CdcdSearchForm(),
    },
  },
  setup(props, { emit }) {
    const editcodesearchData = ref<CdcdSearchForm>(new CdcdSearchForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcodesearchData
    );

    return {
      t: i18n.global.t,
      editcodesearchData,
    };
  },
});
</script>

<style lang="scss"></style>
