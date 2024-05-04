<template>
  <card-comp-design title="Code Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4">
            <select-comp
              v-model="editEmpFormData.empDiv"
              class="full-width"
              label="Code Group"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editEmpFormData.empKor"
              class="full-width"
              clearable
              label="Korean Name"
              outlined
              required
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editEmpFormData.empEng"
              class="full-width"
              clearable
              label="Name"
              outlined
              required
            />
          </div>
          <!-- <div class="col-4">
              <input-comp
                v-model="editEmpFormData.codeLvl"
                class="full-width"
                clearable
                label="Code Level"
                outlined
              />
            </div>
            <div class="col-4">
              <input-comp
                v-model="editEmpFormData.useYn"
                class="full-width"
                clearable
                label="Desciption"
                outlined
              />
            </div> -->
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Service
import { CodeService } from 'src/services/CodeService';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpForm } from 'src/types/EmpForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => EmpForm,
      default: () => new EmpForm(),
    },
  },
  setup(props, { emit }) {
    const editEmpFormData = ref<EmpForm>(new EmpForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editEmpFormData
    );

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
      editEmpFormData,
      code1group,
    };
  },
});
</script>

<style lang="scss"></style>
