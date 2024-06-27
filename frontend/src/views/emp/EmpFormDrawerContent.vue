<template>
  <div class="row">
    <div class="col-8 q-pr-sm">
      <card-comp-design :title="t('empInfo')">
        <template #content>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="row col-3">
                <div v-if="!editEmpFormData.previewImage">
                  <q-img
                    style="
                      width: 190px;
                      height: 170px;
                      border: 1px solid #b4b4b4;
                      border-radius: 4px;
                    "
                    :src="fileUrl + editEmpFormData.empImg"
                  />
                </div>
                <q-img
                  v-if="editEmpFormData.previewImage"
                  :src="editEmpFormData.previewImage"
                  style="
                    width: 190px;
                    height: 170px;
                    border: 1px solid #b4b4b4;
                    border-radius: 4px;
                  "
                  alt="Preview Image"
                />
                <input-comp type="file" clearable @change="handleFileChange" />
              </div>

              <div class="row col-9 q-col-gutter-md">
                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.username"
                    class="full-width"
                    clearable
                    :label="t('username')"
                    outlined
                    required
                  />
                </div>

                <div class="col-5">
                  <input-comp
                    v-model="editEmpFormData.empEng"
                    class="full-width"
                    clearable
                    :label="t('enname')"
                    outlined
                    required
                  />
                </div>

                <div class="col-3">
                  <input-comp
                    v-model="editEmpFormData.empKor"
                    class="full-width"
                    clearable
                    :label="t('krname')"
                    outlined
                    required
                  />
                </div>

                <!-- <div class="col-6">
                  <date-picker-comp
                    v-model="editEmpFormData.empDob"
                    class="full-width"
                    clearable
                    :label="t('dob')"
                    outlined
                  />
                </div> -->

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empEmail"
                    class="full-width"
                    clearable
                    :label="t('Pemail')"
                    outlined
                  />
                </div>

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empEmailBook"
                    class="full-width"
                    clearable
                    :label="t('email')"
                    outlined
                    required
                  />
                </div>

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empFax"
                    class="full-width"
                    clearable
                    :label="t('empFax')"
                    outlined
                  />
                </div>

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empPhone"
                    class="full-width"
                    clearable
                    :label="t('phone')"
                    outlined
                  />
                </div>

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empWorkPhone"
                    class="full-width"
                    clearable
                    :label="t('workphone')"
                    outlined
                  />
                </div>

                <div class="col-4">
                  <input-comp
                    v-model="editEmpFormData.empExtenNum"
                    class="full-width"
                    :label="t('empExtenNum')"
                    outlined
                  />
                </div>
              </div>

              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.empAddress"
                  class="full-width"
                  clearable
                  :label="t('address')"
                  outlined
                />
              </div>

              <div class="col-6 q-pr-md">
                <input-comp
                  v-model="editEmpFormData.empMemo"
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
      <card-comp-design :title="t('workInfo')">
        <template #content>
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empComp"
                  class="full-width select-comp-padding"
                  :label="t('compreport')"
                  :options="CompList"
                  outlined
                  required
                />
              </div>

              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empOffice"
                  class="full-width select-comp-padding"
                  :label="t('office')"
                  :options="OfficeList"
                  outlined
                  required
                />
              </div>

              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empTitle"
                  class="full-width select-comp-padding"
                  :label="t('empTitle')"
                  :options="TitleList"
                  outlined
                  required
                />
              </div>

              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empDiv"
                  class="full-width select-comp-padding"
                  :label="t('divname')"
                  :options="DivisionList"
                  outlined
                  required
                />
              </div>

              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empRole"
                  class="full-width select-comp-padding"
                  :label="t('jobRole')"
                  :options="JobRoleList"
                  outlined
                />
              </div>

              <div class="col-6">
                <select-comp
                  v-model="editEmpFormData.empStatus"
                  class="full-width select-comp-padding"
                  :label="t('empstatus')"
                  :options="WorkStatusList"
                  outlined
                  required
                />
              </div>

              <div class="col-6">
                <date-picker-comp
                  v-model="editEmpFormData.empBeginDt"
                  class="full-width"
                  clearable
                  :label="t('WSD')"
                  outlined
                />
              </div>

              <div class="col-6">
                <date-picker-comp
                  v-model="editEmpFormData.empEndDt"
                  class="full-width"
                  clearable
                  :label="t('WED')"
                  outlined
                />
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>

    <div class="col-4 q-pt-sm q-pr-sm">
      <card-comp-design :title="t('empPw')">
        <template #content>
          <q-card-section>
            <div class="col-3 q-pb-lg" v-if="editEmpFormData.empUuid">
              <input type="checkbox" v-model="pwChangeYN" />
              <label for="guide-checkbox" class="q-pa-sm">
                {{ t(`changePW`) }}</label
              >
            </div>

            <div v-if="pwChangeYN" class="row q-col-gutter-md">
              <div class="col-6">
                <input-comp
                  v-model="editEmpFormData.password"
                  class="full-width"
                  clearable
                  :displayValue="false"
                  :label="t('pw')"
                  outlined
                  required
                  type="password"
                />
              </div>

              <div class="col-6">
                <input-comp
                  v-model="repeatedPassword"
                  class="full-width"
                  clearable
                  :displayValue="false"
                  :label="t('repeatPw')"
                  outlined
                  required
                  type="password"
                />
                <div v-if="repeatedPassword != editEmpFormData.password">
                  Passwords do not match.
                </div>
              </div>
            </div>
          </q-card-section>
        </template>
      </card-comp-design>
    </div>

    <div class="col-8 q-pt-sm q-pl-sm">
      <card-comp-design :title="t('guideInfo')">
        <template #content>
          <q-card-section>
            <div class="col-4 q-pb-lg">
              <input type="checkbox" v-model="guideYN" />
              <label for="guide-checkbox" class="q-pa-sm">{{
                t(`guideSetup`)
              }}</label>
            </div>
            <div v-if="guideYN" class="row q-col-gutter-md">
              <div class="col-2">
                <input-color-comp
                  v-model="editEmpFormData.backColor"
                  class="full-width"
                  clearable
                  :label="t('bgColor')"
                />
              </div>
              <div class="col-2">
                <input-color-comp
                  v-model="editEmpFormData.fontColor"
                  class="full-width"
                  clearable
                  :label="t('fontColor')"
                />
              </div>
              <div class="col-3">
                <q-checkbox
                  v-model="car25seatsComputed"
                  value="editEmpFormData.car25seats"
                  :label="t('car25seats')"
                />
              </div>
              <div class="col-3">
                <q-checkbox
                  v-model="car15seatsComputed"
                  :label="t('car15seats')"
                />
              </div>
              <div class="col-2">
                <q-checkbox
                  v-model="carPersonalComputed"
                  :label="t('carPersonal')"
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
import { defineComponent, ref, computed, watch } from 'vue';
//Lang
import i18n from 'src/i18n';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import DatePickerComp from 'src/components/common/DatePickerComp.vue';
import InputColorComp from 'src/components/common/InputColorComp.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { EmpForm } from 'src/types/EmpForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
//Fileinfo
import { fileInfo } from 'src/utils/helpers/Fileinfo';

export default defineComponent({
  name: 'EmpFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    CardCompDesign,
    DatePickerComp,
    InputColorComp,
  },
  props: {
    modelValue: {
      type: Object as () => EmpForm,
      default: () => new EmpForm(),
    },
    uploadFile: Function,
  },
  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const guideYN = ref<boolean>(false);
    const pwChangeYN = ref<boolean>(false);
    const editEmpFormData = ref<EmpForm>(new EmpForm());
    const showflag = ref<number | 0>(0);
    const fileUrl = ref(fileInfo);
    const repeatedPassword = ref<string>('');

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editEmpFormData
    );

    const DivisionList = ref<SelectOption[]>([]);
    loadOptionsList(19, DivisionList, locale);

    const CompList = ref<SelectOption[]>([]);
    loadOptionsList(21, CompList, locale);

    const OfficeList = ref<SelectOption[]>([]);
    loadOptionsList(2, OfficeList, locale);

    const TitleList = ref<SelectOption[]>([]);
    loadOptionsList(18, TitleList, locale);

    const JobRoleList = ref<SelectOption[]>([]);
    loadOptionsList(16, JobRoleList, locale);

    const WorkStatusList = ref<SelectOption[]>([]);
    loadOptionsList(15, WorkStatusList, locale);

    const handleFileChange = (event: Event) => {
      const target = event.target as HTMLInputElement;
      const file: File | null = (target.files && target.files[0]) || null;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const result: string | null = e.target?.result as string;
          editEmpFormData.value.previewImage = result;

          if (props.uploadFile) {
            props.uploadFile(file);
            showflag.value = 1;
          }
        };
        reader.readAsDataURL(file);
      } else {
        editEmpFormData.value.previewImage = '';
      }
    };

    watch(
      editEmpFormData,
      (newValue) => {
        if (
          (newValue.backColor && newValue.backColor.trim() !== '') ||
          (newValue.fontColor && newValue.fontColor.trim() !== '')
        ) {
          guideYN.value = true;
        }

        if (
          newValue.car25seats == 'true' ||
          newValue.car15seats == 'true' ||
          newValue.carPersonal == 'true'
        ) {
          guideYN.value = true;
        }

        if (newValue.empUuid == 0) {
          pwChangeYN.value = true;
        } else {
          pwChangeYN.value = false;
        }
      },
      { deep: true }
    );

    const car25seatsComputed = computed({
      get: () => {
        const value = editEmpFormData.value.car25seats == 'true';
        console.log('car25seatsComputed get:', value);
        return value;
      },
      set: (value) => {
        editEmpFormData.value.car25seats = value.toString();
        console.log(
          'car25seatsComputed set:',
          editEmpFormData.value.car25seats
        );
      },
    });

    const car15seatsComputed = computed({
      get: () => editEmpFormData.value.car15seats == 'true',
      set: (value) => {
        editEmpFormData.value.car15seats = value.toString();
      },
    });

    const carPersonalComputed = computed({
      get: () => editEmpFormData.value.carPersonal == 'true',
      set: (value) => {
        editEmpFormData.value.carPersonal = value.toString();
      },
    });

    return {
      t: i18n.global.t,
      guideYN,
      pwChangeYN,
      editEmpFormData,
      DivisionList,
      CompList,
      OfficeList,
      TitleList,
      JobRoleList,
      WorkStatusList,
      fileUrl,
      repeatedPassword,
      handleFileChange,
      car25seatsComputed,
      car15seatsComputed,
      carPersonalComputed,
    };
  },
});
</script>

<style lang="scss">
.select-comp-padding {
  padding-bottom: 20px;
}
.noMarginPadd {
  margin: 0px;
  padding: 0px;
}
</style>
