<template>
  <card-comp-design title="Code Filters">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-4 col-xs-12">
            <select-comp
              v-model="editcodesearchData.searchcode1"
              class="full-width"
              label="Code Group"
              :options="code1group"
              outlined
            />
          </div>
          <div class="col-5 col-xs-12">
            <input-comp
              v-model="editcodesearchData.searchname"
              class="full-width"
              clearable
              label="Code Name"
              outlined
              required
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
//Component
import CardCompDesign from "@/components/common/CardCompDesign.vue";
import InputComp from "@/components/common/InputComp.vue";
import SelectComp from "@/components/common/SelectComp.vue";

//Service
import { CodeService } from "@/services/CodeService";
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

//Type
import { SelectOption } from "@/types/SelectOption";
import { CodeSearchForm } from "@/types/CodeSearchForm";

export default defineComponent({
  name: "CodeSearchFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CodeSearchForm,
      default: () => new CodeSearchForm(),
    },
  },
  setup(props, { emit }) {
    const editcodesearchData = ref<CodeSearchForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editcodesearchData
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
      editcodesearchData,
      code1group,
    };
  },
});
</script>

<style lang="scss"></style>
