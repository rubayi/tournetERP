<template>
  <card-comp-design title="Employer Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empKor"
              class="full-width"
              clearable
              label="Korean Name"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.empEng"
              class="full-width"
              clearable
              label="English Name"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.username"
              class="full-width"
              clearable
              label="Username"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <input-comp
              v-model="editEmpFormData.password"
              class="full-width"
              clearable
              label="Password"
              outlined
              required
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empDiv"
              class="full-width"
              label="Division (Department)"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empStatus"
              class="full-width"
              label="Work Status"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empTitle"
              class="full-width"
              label="Job Title"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-3">
            <select-comp
              v-model="editEmpFormData.empRole"
              class="full-width"
              label="Job Role"
              :options="code1group"
              outlined
            />
          </div>
          <q-input
            filled
            v-model="editEmpFormData.backColor"
            :rules="['anyColor']"
            label="Emp Background Color"
            class="col-4"
          >
            <template v-slot:append>
              <q-icon name="colorize" class="cursor-pointer">
                <q-popup-proxy
                  cover
                  transition-show="scale"
                  transition-hide="scale"
                >
                  <q-color v-model="editEmpFormData.backColor" />
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
          <q-input
            filled
            v-model="editEmpFormData.fontColor"
            :rules="['anyColor']"
            label="Emp Font(Name) Color"
            class="col-4"
          >
            <template v-slot:append>
              <q-icon name="colorize" class="cursor-pointer">
                <q-popup-proxy
                  cover
                  transition-show="scale"
                  transition-hide="scale"
                >
                  <q-color v-model="editEmpFormData.fontColor" />
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
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
