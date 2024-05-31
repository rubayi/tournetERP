<template>
  <div class="row">
    <div class="col-8 q-pr-sm">
      <card-comp-design :title="t('primaryiformation')">
        <template #content>
          <q-card-section>
            <div v-if="compFormData != null" class="row q-col-gutter-md">
              <div class="col-5">
                <input-comp
                  v-model="compFormData.compKor"
                  class="full-width"
                  clearable
                  :label="t('krcomp')"
                  outlined
                />
              </div>
              <div class="col-5">
                <input-comp
                  v-model="compFormData.compEng"
                  class="full-width"
                  clearable
                  :label="t('encomp')"
                  outlined
                />
              </div>
              <div class="col-2">
                <input-comp
                  v-model="compFormData.compAbb"
                  class="full-width"
                  clearable
                  :label="t('abbreviation')"
                  outlined
                />
              </div>
              <div class="col-3">
                <select-comp
                  v-model="compFormData.compSector"
                  class="full-width select-comp-padding"
                  :label="t('compsector')"
                  :options="compSectorList"
                  outlined
                />
              </div>
              <div class="col-2">
                <select-comp
                  v-model="compFormData.compRate"
                  :label="t('complvl')"
                  class="full-width select-comp-padding"
                  :options="compRateList"
                  outlined
                />
              </div>
              <div class="col-2">
                <select-comp
                  v-model="compFormData.couponYn"
                  :label="t('couponuseyn')"
                  class="full-width select-comp-padding"
                  :options="couponYnList"
                  outlined
                />
              </div>
              <div class="col-5">
              <!--if hotel-->
                <select-comp
                  v-if="compFormData.compSector == 209"
                  v-model="compFormData.compGroup"
                  :label="t('compgroup')"
                  class="full-width select-comp-padding"
                  :options="compGroupList"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="compFormData.estDate"
                  class="full-width"
                  clearable
                  :label="t('estdt')"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="compFormData.beginDt"
                  class="full-width"
                  clearable
                  :label="t('beginDt')"
                  outlined
                />
              </div>
              <div class="col-4">
                <date-picker-comp
                  v-model="compFormData.endDt"
                  class="full-width"
                  clearable
                  :label="t('endDt')"
                  outlined
                />
              </div>
              <div class="row col-4">
                <input-color-comp
                  v-model="compFormData.compColor"
                  class="full-width"
                  clearable
                  :label="t('compColor')"
                  outlined
                />

                <select-comp
                  v-model="compFormData.prepaidHow"
                  :label="t('assgnprepaid')"
                  class="full-width select-comp-padding"
                  :options="prepaidHowList"
                  outlined
                />
              </div>

              <div class="row col-8">
                <div class="col-4">
                  <label for="file-upload" class="custom-file-upload">
                    {{ t('logofile') }}
                  </label>
                  <input-comp
                    type="file"
                    clearable
                    class="btn btn-info input-style"
                    @change="handleFileChange"
                  />
                </div>
                <div class="col-auto">
                  <q-img
                    class="image-max-width"
                    v-if="compFormData.logoFile != ''"
                    :src="fileUrl + compFormData.logoFile"
                  />
                </div>
                <div class="col-auto" v-if="compFormData.previewImage">
                  <q-img
                    :src="compFormData.previewImage"
                    class="image-max-width"
                    alt="Preview Image"
                  />
                </div>
              </div>
              <div class="col-12">
                <input-comp
                  v-model="compFormData.compMemo"
                  class="full-width"
                  clearable
                  :label="t('memo')"
                  outlined
                />
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>

    <div class="col-4 q-pl-sm">
      <card-comp-design :title="t('compPriceRates')">
        <template #content>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="col-6">
                <select-comp
                  v-model="compFormData.hotelRate"
                  :label="t('hotelrate')"
                  class="full-width select-comp-padding"
                  :options="hotelRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.optionRate"
                  :label="t('optionRate')"
                  class="full-width select-comp-padding"
                  :options="compOptionRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.rentcarRate"
                  :label="t('rentcarRate')"
                  class="full-width select-comp-padding"
                  :options="rentcarRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.restaurantRate"
                  :label="t('restaurantRate')"
                  class="full-width select-comp-padding"
                  :options="restaurantRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.packRegRate"
                  :label="t('packRegRate')"
                  class="full-width select-comp-padding"
                  :options="pkgRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.packRate"
                  :label="t('packRate')"
                  class="full-width select-comp-padding"
                  :options="pkgOnlyRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.honeymoonRegRate"
                  :label="t('honeymoonRegRate')"
                  class="full-width select-comp-padding"
                  :options="honeymoonRateList"
                  outlined
                />
              </div>
              <div class="col-6">
                <select-comp
                  v-model="compFormData.honeymoonRate"
                  :label="t('honeymoonRate')"
                  class="full-width select-comp-padding"
                  :options="honeymoonOnlyRateList"
                  outlined
                />
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import InputColorComp from 'src/components/common/InputColorComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
// Type
import { CompForm } from 'src/types/CompForm';
//import { CompDocumentsgForm } from "src/types/CompDocumentsgForm";
import { SelectOption } from 'src/types/SelectOption';
//import { CompMultiCheckbox } from "src/types/CompMultiCheckbox";

// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

//Fileinfo
import { fileInfo } from 'src/utils/helpers/Fileinfo';
//Lang
import i18n from 'src/i18n';

export default defineComponent({
  name: 'CompFormDrawerPrimary',
  components: {
    CardCompDesign,
    SelectComp,
    InputComp,
    DatePickerComp,
    InputColorComp,
  },
  props: {
    primaryData: {
      type: Object as () => CompForm,
      default: () => new CompForm(),
    },
    compSectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compOptionRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    couponYnList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    compGroupList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    pkgOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    rentcarRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    restaurantRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonOnlyRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    honeymoonRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    prepaidHowList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    hotelRateList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    uploadFile: Function,
  },
  setup(props, { emit }) {
    const compFormData = ref<CompForm>(new CompForm());
    const readonlybtn = ref<boolean>(true);

    const fileUrl = ref(fileInfo);
    const showflag = ref<number | 0>(0);

    useSyncModelValue(
      props,
      'primaryData',
      emit,
      'update:primaryData',
      compFormData
    );

    const handleFileChange = (event: Event) => {
      const target = event.target as HTMLInputElement;
      const file: File | null = (target.files && target.files[0]) || null;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const result: string | null = e.target?.result as string;
          compFormData.value.previewImage = result;

          if (props.uploadFile) {
            props.uploadFile(file);
            showflag.value = 1;
          }
        };
        reader.readAsDataURL(file);
      } else {
        compFormData.value.previewImage = '';
      }
    };

    return {
      t: i18n.global.t,
      compFormData,
      readonlybtn,
      fileUrl,
      handleFileChange,
      showflag,
    };
  },
});
</script>

<style lang="scss">
.fieldset03 > h1:after {
  margin: 0 1px 0 20em;
}
.select-comp-padding {
  padding-bottom: 20px;
}
.custom-file-upload {
  align-items: center;
  background-color: #4482df;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  flex-shrink: 0;
  font-family: 'RM Neue', sans-serif;
  line-height: 2.1;
  padding: 10px 21px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}
.custom-file-upload:active {
  background-color: #1c84ff;
}
.custom-file-upload:hover {
  background-color: #4e9bfa;
}
input[type='file'] {
  display: none;
}
.image-max-width {
  width: 100px;
  height: 100px;
  border: 1px solid #b4b4b4;
  border-radius: 4px;
}
</style>
