<template>
  <card-comp-design :title="t('hotelAmetnities')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <select-comp
              v-model="editFormData.amenityType"
              class="full-width select-padding"
              :label="t('amenityType')"
              :options="AmenityTypeList"
              outlined
            />
          </div>

          <div class="col-12">
            <input-comp
              v-model="editFormData.amenityDescKor"
              class="full-width"
              clearable
              :label="t('amenityDescKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="editFormData.amenityDescEng"
              class="full-width select-padding"
              :label="t('amenityDescEng')"
              outlined
            />
          </div>
          <div class="col-12">
            <select-comp
              v-model="editFormData.useYn"
              class="full-width select-padding"
              :label="t('usage')"
              :options="useYnList"
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
import SelectComp from "src/components/common/SelectComp.vue";
// Type
import { SelectOption } from 'src/types/SelectOption';
import { AmenityForm } from 'src/types/AmenityForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import i18n from 'src/i18n';


export default defineComponent({
  name: 'InfoDrawerContent',
  components: {
    SelectComp,
    CardCompDesign,
    InputComp
  },
  props: {
    modelValue: {
      type: Object as () => AmenityForm,
      default: () => new AmenityForm(),
    },
    amenityUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const editFormData = ref<AmenityForm>(new AmenityForm());

    const setContactUuid = () => {
      editFormData.value.amenityUuid = props.amenityUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editFormData
    );

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(35, useYnList, locale);

    const AmenityTypeList = ref<SelectOption[]>([]);
    loadOptionsList(494, AmenityTypeList, locale);

    return {
      t: i18n.global.t,
      useYnList,
      editFormData,
      AmenityTypeList
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
