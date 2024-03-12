<template>
  <card-comp-design title="Code Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4">
            <select-comp
              v-model="editcodeformData.code1"
              class="full-width"
              label="Code Group"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-5">
            <input-comp
              v-model="editcodeformData.codename"
              class="full-width"
              clearable
              label="Code Name"
              outlined
              required
            />
          </div>
          <div class="col">
            <input-comp
              v-model="editcodeformData.certduration"
              class="full-width"
              clearable
              label="Certduration"
              outlined
            />
          </div>
        </div>
        <div class="row q-col-gutter-md">
          <div class="col-9">
            <input-comp
              v-model="editcodeformData.desciption"
              class="full-width"
              clearable
              label="Desciption"
              outlined
            />
          </div>
          <div class="col-3">
            <number-comp
              v-model="editcodeformData.codeord"
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
import CardCompDesign from "@/components/common/CardCompDesign.vue";
import InputComp from "@/components/common/InputComp.vue";
import SelectComp from "@/components/common/SelectComp.vue";
import NumberComp from "@/components/common/NumberComp.vue";

// Service
import { CodeService } from "@/services/CodeService";

// Type
import { CodeForm } from "@/types/CodeForm";
import { SelectOption } from "@/types/SelectOption";
// Helper
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

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

    const code1group = ref<SelectOption[]>([]);
    loadcode1groupOptions();
    function loadcode1groupOptions() {
      CodeService.getGroupCodeForm(0).then((response) => {
        code1group.value = response.map(
          (x) => new SelectOption(x.codename, x.code2)
        );
      });
    }

    return {
      editcodeformData,
      code1group,
    };
  },
});
</script>

<style lang="scss"></style>
