<template>
  <card-comp-design :title="t('tourContacts')">
    <template #serviceName>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <select-comp
              v-model="tourServiceFormData.infoType"
              class="full-width select-padding"
              :label="t('type')"
              :options="infoTypeList"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="tourServiceFormData.serviceNameKor"
              class="full-width"
              clearable
              :label="t('serviceNameKor')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <text-area-comp
              v-model="tourServiceFormData.serviceNameEng"
              class="full-width select-padding"
              :label="t('serviceNameEng')"
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
import { TourServiceForm } from 'src/types/TourServiceForm';
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
      type: Object as () => TourServiceForm,
      default: () => new TourServiceForm(),
    },
    serviceUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const tourServiceFormData = ref<TourServiceForm>(new TourServiceForm());

    const setContactUuid = () => {
      tourServiceFormData.value.serviceUuid = props.serviceUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      tourServiceFormData
    );

    const infoTypeList = ref<SelectOption[]>([]);
    loadOptionsList(481, infoTypeList, locale);

    return {
      t: i18n.global.t,
      infoTypeList,
      tourServiceFormData,
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
