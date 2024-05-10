<template>
  <card-comp-design title="Code Information">
    <template #content>
      <q-card-section>
        <div v-if="editcodeformData != null" class="row q-col-gutter-md">
          <div class="col-12">
            <select-comp
              v-model="editcodeformData.uprCodeUuid"
              class="full-width select-comp-padding"
              label="Code Group"
              :options="uprCodeUuidgroup"
              outlined
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="editcodeformData.codeKr"
              class="full-width"
              clearable
              label="Code Name (Kr)"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="editcodeformData.codeEn"
              class="full-width"
              clearable
              label="Code Name(En)"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.codeAbb"
              class="full-width"
              clearable
              label="Code Abbreviation"
              outlined
            />
          </div>
          <div class="col-6">
            <number-comp
              v-model="editcodeformData.codeLvl"
              class="full-width"
              clearable
              label="Code Level"
              outlined
            />
          </div>
          <div class="col-6">
            <number-comp
              v-model="editcodeformData.codeOrd"
              class="full-width"
              label="Code Display Order"
              :max-number="100"
              :min-number="0"
              outlined
              validation-message="Number must be [0-100]"
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editcodeformData.useYn"
              class="full-width"
              clearable
              label="Conde In Use (ex. Y/N)"
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
//Lang
import i18n from "src/i18n";
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import NumberComp from 'src/components/common/NumberComp.vue';
// Type
import { CodeForm } from 'src/types/CodeForm';
import { SelectOption } from 'src/types/SelectOption';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
// CommonCode
import {loadOptionsList} from "src/utils/commoncode/commonCode";

export default defineComponent({
  name: 'CodeFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    NumberComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CodeForm,
      default: () => new CodeForm(),
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const editcodeformData = ref<CodeForm>();
    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editcodeformData
    );

    // Loading Group Code Options
    const uprCodeUuidgroup = ref<SelectOption[]>([]);
    const useYnOptions = ref<SelectOption[]>([]);

    loadOptionsList(0, uprCodeUuidgroup, locale);
    loadOptionsList(343, useYnOptions, locale);

    return {
      editcodeformData,
      uprCodeUuidgroup,
      useYnOptions,
    };
  },
});
</script>

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
</style>
