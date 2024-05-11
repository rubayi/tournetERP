<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editcodesearchData.searchUprCodeUuid"
              class="full-width select-comp-padding"
              :label="t('byGroup')"
              :options="groupNames"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCodeEn"
              class="full-width"
              clearable
              :label="t('byCodeEn')"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCodeKr"
              class="full-width"
              clearable
              :label="t('byCodeKr')"
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
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
//Type
import { SelectOption } from 'src/types/SelectOption';
import { CodeSearchForm } from 'src/types/CodeSearchForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'CodeSearchFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CodeSearchForm,
      default: () => new CodeSearchForm(),
    },
    groupNames: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  setup(props, { emit }) {
    const editcodesearchData = ref<CodeSearchForm>(new CodeSearchForm());
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

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
</style>
