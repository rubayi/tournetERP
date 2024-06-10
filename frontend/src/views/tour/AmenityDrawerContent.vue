<template>
  <card-comp-design :title="t('tourInfos')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">

          <div class="col-12">
            <text-area-comp
              v-model="editFormData.amenityKor"
              class="full-width"
              clearable
              :label="t('amenityKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="editFormData.amenityEng"
              class="full-width select-padding"
              :label="t('amenityEng')"
              outlined
            />
          </div>

          <div class="col-12">
            <text-area-comp
              v-model="editFormData.amenityDescKor"
              class="full-width"
              clearable
              :label="t('amenityDescKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="editFormData.amenityDescEng"
              class="full-width select-padding"
              :label="t('amenityDescEng')"
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
import TextAreaComp from 'src/components/common/TextAreaComp.vue';
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
    CardCompDesign,
    TextAreaComp
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

    const infoTypeList = ref<SelectOption[]>([]);
    loadOptionsList(481, infoTypeList, locale);

    return {
      t: i18n.global.t,
      infoTypeList,
      editFormData,
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
