<template>
  <card-comp-design :title="t('compContacts')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">
          <div v-if="!carImgFormData.previewImage">
            <q-img
              v-if="carImgFormData.imgFile != ''"
              :src="fileUrl + carImgFormData.imgFile"
              style="
                width: 190px;
                height: 170px;
                border: 1px solid #b4b4b4;
                border-radius: 4px;
              "
            />
          </div>
          <q-img
            v-if="carImgFormData.previewImage"
            :src="carImgFormData.previewImage"
            alt="Preview Image"
            style="
              width: 190px;
              height: 170px;
              border: 1px solid #b4b4b4;
              border-radius: 4px;
            "
          />
          <input-comp type="file" clearable @change="handleFileChange" />
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
// Type
import { CarImgForm } from 'src/types/CarImgForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import i18n from 'src/i18n';
import { fileInfo } from 'src/utils/helpers/Fileinfo';

export default defineComponent({
  name: 'CarImgDrawerContent',
  components: {
    CardCompDesign,
    InputComp,
  },
  props: {
    modelValue: {
      type: Object as () => CarImgForm,
      default: () => new CarImgForm(),
    },
    carImgUuid: {
      type: Number,
      default: 0,
    },
    uploadFile: Function,
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const carImgFormData = ref<CarImgForm>(new CarImgForm());
    const showflag = ref<number | 0>(0);
    const fileUrl = ref(fileInfo);

    const setContactUuid = () => {
      carImgFormData.value.carImgUuid = props.carImgUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      carImgFormData
    );

    const handleFileChange = (event: Event) => {
      const target = event.target as HTMLInputElement;
      const file: File | null = (target.files && target.files[0]) || null;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const result: string | null = e.target?.result as string;
          carImgFormData.value.previewImage = result;

          if (props.uploadFile) {
            props.uploadFile(file);
            showflag.value = 1;
          }
        };
        reader.readAsDataURL(file);
      } else {
        carImgFormData.value.previewImage = '';
      }
    };

    return {
      t: i18n.global.t,
      carImgFormData,
      handleFileChange,
      fileUrl,
      showflag,
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
