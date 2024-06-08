<template>
  <card-comp-design :title="t('tourContacts')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <select-comp
              v-model="infoFormData.infoType"
              class="full-width select-padding"
              :label="t('type')"
              :options="infoTypeList"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="infoFormData.contentKor"
              class="full-width"
              clearable
              :label="t('contentKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="infoFormData.contentEng"
              class="full-width select-padding"
              :label="t('contentEng')"
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
import SelectComp from 'src/components/common/SelectComp.vue';
import TextAreaComp from 'src/components/common/TextAreaComp.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { InfoForm } from 'src/types/InfoForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import i18n from 'src/i18n';

export default defineComponent({
  name: 'TourContactDrawerContent',
  components: {
    CardCompDesign,
    SelectComp,
    TextAreaComp
  },
  props: {
    modelValue: {
      type: Object as () => InfoForm,
      default: () => new InfoForm(),
    },
    infoUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const infoFormData = ref<InfoForm>(new InfoForm());

    const setContactUuid = () => {
      infoFormData.value.infoUuid = props.infoUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      infoFormData
    );

    const infoTypeList = ref<SelectOption[]>([]);
    loadOptionsList(481, infoTypeList, locale);

    return {
      t: i18n.global.t,
      infoTypeList,
      infoFormData,
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
