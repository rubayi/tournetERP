<template>
  <card-comp-design :title="t('emerContact')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerName"
              class="full-width"
              clearable
              :label="t('emergencyName')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerRelation"
              class="full-width"
              clearable
              :label="t('relation')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerPhone1"
              class="full-width"
              clearable
              :label="t('phone')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerWorkPhone"
              class="full-width"
              :label="t('workphone')"
              clearable
              outlined
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="emergencyFormData.emerAddress"
              class="full-width"
              clearable
              :label="t('address')"
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
import i18n from 'src/i18n';

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
    empUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const emergencyFormData = ref<EmergencyForm>(new EmergencyForm());
    const setEmpUuid = () => {
      emergencyFormData.value.empUuid = props.empUuid;
    };

    setEmpUuid();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      emergencyFormData
    );

    return {
      t: i18n.global.t,
      emergencyFormData,
    };
  },
});
</script>
<style lang="scss"></style>
