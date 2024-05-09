<template>
  <card-comp-design title="Emergency Contact Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerName"
              class="full-width"
              clearable
              label="Name"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerRelation"
              class="full-width"
              clearable
              label="Relation"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerPhone1"
              class="full-width"
              clearable
              label="Phone"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerWorkPhone"
              class="full-width"
              label="Phone (Work)"
              clearable
              outlined
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerAddress"
              class="full-width"
              clearable
              label="Address"
              outlined
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>
<script lang="ts">
import { ref, defineComponent } from 'vue';
// Components
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
// Type
import { EmergencyForm } from 'src/types/EmergencyForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpEmergencyDrawerContent',
  components: {
    CardCompDesign,
    InputComp,
  },
  props: {
    modelValue: {
      type: Object as () => EmergencyForm,
      default: () => new EmergencyForm(),
    },
  },
  setup(props, { emit }) {
    const emergencyFormData = ref<EmergencyForm>(new EmergencyForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      emergencyFormData
    );

    return {
      emergencyFormData,
    };
  },
});
</script>
<style lang="scss"></style>
