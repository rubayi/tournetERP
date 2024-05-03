<template>
  <card-comp-design title="Code Information">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">

          <div class="col-4">
            <input-comp
              v-model="editcodeformData.mngNameKor"
              class="full-width"
              clearable
              label="Card Manage Name"
              outlined
              required
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.mngNameEng"
              class="full-width"
              clearable
              label="Card Manage Name(En)"
              outlined
              required
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.cardNumber"
              class="full-width"
              label="Card Number"
              outlined
            />
          </div>
          <div class="col-4">
            <input-comp
              v-model="editcodeformData.nameOnCard"
              class="full-width"
              clearable
              label="Name on Card"
              outlined
              required
            />
          </div>
          <div class="col-4">
            {{editcodeformData.expMonth}}
            <select-comp
              v-model="editcodeformData.expMonth"
              class="full-width"
              label="Exp Month"
              :options="monNumbers"
              outlined
            />
          </div>
          <div class="col-4">
            {{editcodeformData.expYear}}
            <select-comp
              v-model="editcodeformData.expYear"
              class="full-width"
              label="Exp Year"
              :options="yearNumbers"
              outlined
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
import { CdcdForm } from "src/types/CdcdForm";
import { SelectOption } from "src/types/SelectOption";
// Helper
import { useSyncModelValue } from "src/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "CdcdFormDrawerContent",
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => CdcdForm,
      default: () => new CdcdForm(),
    },
  },
  setup(props, { emit }) {
    const editcodeformData = ref<CdcdForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editcodeformData
    );

    // Loading Group Code Options

    const yearNumbers = ref<SelectOption[]>([]);
    const monNumbers = ref<SelectOption[]>([]);

    loadcdMonthOptions();

    function loadcdMonthOptions() {

      for (let i = 0
        ; i <= 20; i++) {
        let tnum = i.toString().padStart(2, '0');
        monNumbers.value.push({ label: tnum, value: parseInt(tnum) });
      }
    }

    loadcdYearOptions();
    function loadcdYearOptions() {
      let currentYear = +new Date().getFullYear();
      for (let t = currentYear; t <= (currentYear + 20); t++) {
        let label = t.toString();
        yearNumbers.value.push({ label: label, value: t });
      }
    }

    return {
      editcodeformData,
      monNumbers,
      yearNumbers
    };
  },
});
</script>

<style lang="scss"></style>
