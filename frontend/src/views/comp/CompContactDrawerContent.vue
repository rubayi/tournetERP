<template>
  <card-comp-design :title="t('compContacts')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12">
            <select-comp
              v-model="contactFormData.contactType"
              class="full-width select-padding"
              :label="t('type')"
              :options="ContactTypeList"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <input-comp
              v-model="contactFormData.contactCont"
              class="full-width"
              clearable
              :label="t('contact')"
              outlined
              required
            />
          </div>
          <div class="col-12">
            <select-comp
              v-model="contactFormData.repYn"
              class="full-width select-padding"
              :label="t('usage')"
              :options="RepresentTypeList"
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
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { ContactForm } from 'src/types/ContactForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import i18n from 'src/i18n';

export default defineComponent({
  name: 'CompContactDrawerContent',
  components: {
    CardCompDesign,
    InputComp,
    SelectComp,
  },
  props: {
    modelValue: {
      type: Object as () => ContactForm,
      default: () => new ContactForm(),
    },
    contactUuid: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const contactFormData = ref<ContactForm>(new ContactForm());

    const setContactUuid = () => {
      contactFormData.value.contactUuid = props.contactUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      contactFormData
    );

    const ContactTypeList = ref<SelectOption[]>([]);
    loadOptionsList(342, ContactTypeList, locale);

    const RepresentTypeList = ref<SelectOption[]>([]);
    loadOptionsList(343, RepresentTypeList, locale);

    return {
      t: i18n.global.t,
      ContactTypeList,
      contactFormData,
      RepresentTypeList,
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
