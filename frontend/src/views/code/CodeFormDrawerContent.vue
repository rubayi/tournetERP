<template>
  <card-comp-design title="Code Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4">
            <select-comp
              v-model="editcodeformData.uprCodeUuid"
              class="full-width"
              label="Code Group"
              :options="uprCodeUuidgroup"
              outlined
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.codeKr"
              class="full-width"
              clearable
              label="Code Name"
              outlined
              required
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.codeEn"
              class="full-width"
              clearable
              label="Code Name(En)"
              outlined
              required
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.codeLvl"
              class="full-width"
              clearable
              label="Code Level"
              outlined
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.useYn"
              class="full-width"
              clearable
              label="Desciption"
              outlined
            />
          </div>
          <div class="col-4">
            <number-comp
              v-model="editcodeformData.codeOrd"
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
import { defineComponent, ref } from "vue";

// Component
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import InputComp from "src/components/common/InputComp.vue";
import SelectComp from "src/components/common/SelectComp.vue";
import NumberComp from "src/components/common/NumberComp.vue";

// Service
import { CodeService } from "src/services/CodeService";

// Type
import { CodeForm } from "src/types/CodeForm";
import { SelectOption } from "src/types/SelectOption";
// Helper
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "CodeFormDrawerContent",
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
    const editcodeformData = ref<CodeForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editcodeformData
    );

    // Loading Group Code Options

    const uprCodeUuidgroup = ref<SelectOption[]>([]);
    loaduprCodeUuidgroupOptions();
    function loaduprCodeUuidgroupOptions() {

      CodeService.getGroupCodeForm(0).then((response) => {
        uprCodeUuidgroup.value = response.map(
          (x) => new SelectOption(x.codeKr, x.codeUuid)
        );
      });
    }

    return {
      editcodeformData,
      uprCodeUuidgroup,
    };
  },
});
</script>

<style lang="scss"></style>
