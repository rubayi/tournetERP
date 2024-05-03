<template>
  <card-comp-design title="Employer Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editempsearchData.searchEmpDiv"
              class="full-width"
              label="Employer Division"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editempsearchData.searchEmpKor"
              class="full-width"
              clearable
              label="Employer Korean Name"
              outlined
              required
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editempsearchData.searchEmpEng"
              class="full-width"
              clearable
              label="Employer Name"
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
// import { EmpService } from 'src/services/EmpService';
import { CodeService } from 'src/services/CodeService';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpSearchForm } from 'src/types/EmpSearchForm';

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
  },
  setup(props, { emit }) {
    const editempsearchData = ref<EmpSearchForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editempsearchData
    );

    // Loading Group Code Options
    const code1group = ref<SelectOption[]>([]);

    loadDivision();
    function loadDivision() {
      CodeService.getGroupCodeForm(19).then((response) => {
        code1group.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }
    return {
      editempsearchData,
      code1group,
    };
  },
});
</script>

<style lang="scss"></style>
