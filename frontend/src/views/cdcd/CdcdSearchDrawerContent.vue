<template>
  <card-comp-design :title="t('subSearchTitle')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editcodesearchData.searchUprCodeUuid"
              class="full-width"
              :label="t('byGroup')"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCodeKr"
              class="full-width"
              clearable
              :label="t('byCodeEn')"
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
//Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';

//Service
import { CodeService } from 'src/services/CodeService';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Type
import { SelectOption } from 'src/types/SelectOption';
import { CodeSearchForm } from 'src/types/CodeSearchForm';

import i18n from 'src/i18n';

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
  },
  setup(props, { emit }) {
    const editcodesearchData = ref<CodeSearchForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcodesearchData
    );

    // Loading Group Code Options
    const code1group = ref<SelectOption[]>([]);
    loadcode1groupOptions();
    function loadcode1groupOptions() {
      CodeService.getGroupCodeForm(0).then((response) => {
        code1group.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeLvl)
        );
      });
    }
    return {
      t: i18n.global.t,
      editcodesearchData,
      code1group,
    };
  },
});
</script>

<style lang="scss"></style>
