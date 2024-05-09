<!-- <template>
  <div></div>
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
    const emergencyForm = ref<EmergencyForm>(new EmergencyForm());
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      emergencyForm
    );

    return {
      emergencyForm,
    };
  },
});
</script>
<style lang="scss"></style> -->
