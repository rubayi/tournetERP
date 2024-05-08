<template>
  <card-comp-design title="Code Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editcodesearchData.searchUprCodeUuid"
              class="full-width select-comp-padding"
              label="Filter by Code Group"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCodeEn"
              class="full-width"
              clearable
              label="Filter By English Code Name"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchCodeKr"
              class="full-width"
              clearable
              label="Filter By Korean Code Name"
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
import SelectComp from 'src/components/common/SelectComp.vue';

//Service
import { CodeService } from 'src/services/CodeService';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Type
import { SelectOption } from 'src/types/SelectOption';
import { CodeSearchForm } from 'src/types/CodeSearchForm';

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
    const editcodesearchData = ref<CodeSearchForm>(new CodeSearchForm());
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
          (x) => new SelectOption(x.codeEn, x.codeUuid)
        );
      });
    }
    return {
      editcodesearchData,
      code1group,
    };
  },
});
</script>

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
</style>
