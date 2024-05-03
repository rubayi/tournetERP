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
              :options="uprCodeUuidgroup"
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
          <div class="col-4">
            <number-comp
              v-model="editEmpFormData.empPhone"
              class="full-width"
              label="Sort Order"
              :max-number="100"
              :min-number="0"
              outlined
              validation-message="Number must be [0-100]"
            />
          </div>
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
import NumberComp from 'src/components/common/NumberComp.vue';

// Service
import { EmpService } from 'src/services/EmpService';
import { EmpSearchForm } from 'src/types/EmpSearchForm';

// Type
import { EmpForm } from 'src/types/EmpForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    NumberComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => EmpForm,
      default: () => new EmpForm(),
    },
  },
  setup(props, { emit }) {
    const editEmpFormData = ref<EmpForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editEmpFormData
    );

    // Loading Group Emp Options

    const uprCodeUuidgroup = ref<SelectOption[]>([]);
    loaduprCodeUuidgroupOptions();
    function loaduprCodeUuidgroupOptions() {
      let empSearchForm = new EmpSearchForm();
      EmpService.getAll(empSearchForm).then((response) => {
        uprCodeUuidgroup.value = response.map(
          (x) => new SelectOption(x.empKor, x.empEng)
        );
      });
    }

    return {
      editEmpFormData,
      uprCodeUuidgroup,
    };
  },
});
</script>

<style lang="scss"></style>
