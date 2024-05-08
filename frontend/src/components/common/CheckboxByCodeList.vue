<!-- <template>
  <div
    v-for="(n, index) in options"
    :class="colnum"
    :value="index"
    :key="index"
  >
    <q-checkbox
      v-model="inputValue"
      checked-icon="task_alt"
      :disable="disabled"
      :label="n.label"
      unchecked-icon="panorama_fish_eye"
      :val="n.value"
    >
      <slot name="label" />
    </q-checkbox>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
//Type
import { SelectOption } from 'src/types/SelectOption';

export default defineComponent({
  name: 'CheckboxByCodeList',
  props: {
    modelValue: {
      type: [String, Number, Object],
      default: () => null,
    },
    colnum: {
      type: String,
      default: 'col-4',
    },
    options: {
      type: Array as () => SelectOption[],
      required: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const inputValue = ref<boolean>(false);

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      inputValue
    );

    return {
      inputValue,
    };
  },
});
</script>

<style lang="scss"></style> -->
