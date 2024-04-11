<template>
  <div id="comp-form-drawer-content">

      <div class="flex flex-grow-1 q-pa-md">
        <q-card-section>
          <q-form v-if="edited">
            <q-card-section class="custom-padding-margin">
              <div class="row q-col-gutter-lg">
                <input id="compUuid" v-model="edited.compUuid" hidden />
                <q-select
                  class="col-3"
                  v-model="edited.compSector"
                  :options="sectorOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="업체유형"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="compKor"
                  v-model="edited.compKor"
                  label="업체명 *"
                  :rules="[(val) => !!val || '한글업체명 입력 해 주십시오.']"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="compEng"
                  v-model="edited.compEng"
                  label="영문업체명"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="compAbb"
                  v-model="edited.compAbb"
                  label="업체약어"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="group"
                  v-model="edited.compGroup"
                  label="그룹"
                />
<!--                <q-select-->
<!--                  class="col-3"-->
<!--                  v-model="edited.minAge"-->
<!--                  :options="ageNumbers"-->
<!--                  option-value="codeUuid"-->
<!--                  option-label="codeKr"-->
<!--                  emit-value-->
<!--                  map-options-->
<!--                  label="제한연령"-->
<!--                />-->
<!--                <q-select-->
<!--                  class="col-3"-->
<!--                  v-model="edited.childAge"-->
<!--                  :options="ageNumbers"-->
<!--                  option-value="codeUuid"-->
<!--                  option-label="codeKr"-->
<!--                  emit-value-->
<!--                  map-options-->
<!--                  label="아이나이"-->
<!--                />-->
<!--                <q-select-->
<!--                  class="col-3"-->
<!--                  v-model="edited.youthAge"-->
<!--                  :options="ageNumbers"-->
<!--                  option-value="codeUuid"-->
<!--                  option-label="codeKr"-->
<!--                  emit-value-->
<!--                  map-options-->
<!--                  label="쥬니어나이"-->
<!--                />-->
                <q-select
                  class="col-3"
                  v-model="edited.couponYn"
                  :options="useYnOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="쿠폰사용가능유무"
                />
                <q-select
                  class="col-3"
                  v-model="edited.prepaidHow"
                  :options="compRoleOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="프리페이드지정"
                />
                <q-select
                  class="col-3"
                  v-model="edited.hotelRate"
                  :options="hotelRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="호텔등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.optionRate"
                  :options="optionRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="옵션등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.rentcarRate"
                  :options="rentcarRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="렌트카등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.restaurantRate"
                  :options="restaurantRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="식당등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.packRegRate"
                  :options="packRegRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="패키지일반등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.packRate"
                  :options="packRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="패키지전용등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.honeymoonRegRate"
                  :options="honeymoonRegRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="허니문일반등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.honeymoonRate"
                  :options="honeymoonRateOptions"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="허니문전용등급"
                />
                <q-input
                  filled
                  v-model="edited.compColor"
                  :rules="['anyColor']"
                  label="회사구분색"
                  class="col-4"
                >
                  <template v-slot:append>
                    <q-icon name="colorize" class="cursor-pointer">
                      <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                        <q-color v-model="edited.compColor" />
                      </q-popup-proxy>
                    </q-icon>
                  </template>
                </q-input>

                <q-input
                  class="col-3"
                  v-model="edited.estDate"
                  mask="####/##/##"
                  :rules="['date']"
                  label="설립일"
                >
                  <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                      <q-popup-proxy
                        cover
                        transition-show="scale"
                        transition-hide="scale"
                      >
                        <q-date v-model="edited.estDate" minimal>
                          <div class="row items-center justify-end">
                            <q-btn
                              v-close-popup
                              label="닫기"
                              color="primary"
                              flat
                            />
                          </div>
                        </q-date>
                      </q-popup-proxy>
                    </q-icon>
                  </template>
                </q-input>
                <div class="q-pa-md">
                  <div class="col-12">
                  <label  for="file-upload" class="custom-file-upload">
                    로고파일
                  </label>
                  <input id="file-upload"
                         type="file"
                         multiple
                         class="btn btn-info"
                         @change="handleFileChange" />
                  <div v-if="previewImage">
                    <q-img :src="previewImage" alt="Preview Image"/>
                  </div>
                  <div>
                    <q-img v-if="edited.logoFile"
                           :src="fileUrl + edited.logoFile"/>
                  </div>
                  </div>
                </div>
              </div>
            </q-card-section>
          </q-form>
        </q-card-section>

      </div>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";
import { getCommonValue } from "src/utils/common";
import { fileInfo } from "src/services/fileInfo";
export default defineComponent({
  name: "CompFormDrawerContent",

  props: {
    dataVal: Object,
    uploadFile: Function,
  },
  emits: ["update:dataVal"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

    const sectorOptions = ref([]);
    const hotelRateOptions = ref([]);
    const optionRateOptions = ref([]);
    const rentcarRateOptions = ref([]);
    const restaurantRateOptions = ref([]);

    const packRegRateOptions = ref([]);
    const packRateOptions = ref([]);
    const honeymoonRegRateOptions = ref([]);
    const honeymoonRateOptions = ref([]);

    const useYnOptions = ref([]);

    const ageNumbers = ref([]);
    const fileUrl = fileInfo;

    const previewImage = ref(null);

    for (let i = 0
      ; i <= 20; i++) {
      ageNumbers.value.push(i);
    }

    watch(() => props.dataVal, (newVal) => {
      emit('update:dataVal', newVal);
      edited.value = newVal;
    }, { deep: true, immediate: true });

    /* 공통코드값 가져오기 */
    async function getCommonCode(req, targetDataName) {
      try {
        targetDataName.value = await getCommonValue(req);
      } catch (error) {
        console.error(error);
      }
    }

    const handleFileChange = (event) => {
      const file = event.target.files[0];
      if (file) {
        // Read the selected file and set the previewImage variable with its data URL
        const reader = new FileReader();
        reader.onload = (e) => {
          previewImage.value = e.target.result;
          props.uploadFile(file);
        };
        reader.readAsDataURL(file);
      } else {
        previewImage.value = null;
      }
    };

    //유형
    getCommonCode({upCode: 22,codeLvl: "1"},sectorOptions);
    //호텔업체등급
    getCommonCode({upCode: 239,codeLvl: "1"},hotelRateOptions);
    //옵션업체등급
    getCommonCode({upCode: 240,codeLvl: "1"},optionRateOptions);
    //렌트카등급
    getCommonCode({upCode: 241,codeLvl: "1"},rentcarRateOptions);
    //식당등급
    getCommonCode({upCode: 242,codeLvl: "1"},restaurantRateOptions);

    //패키지일반등급
    getCommonCode({upCode: 353,codeLvl: "1"},packRegRateOptions);
    //패키지전용등급
    getCommonCode({upCode: 354,codeLvl: "1"},packRateOptions);
    //허니문일반등급
    getCommonCode({upCode: 355,codeLvl: "1"},honeymoonRegRateOptions);
    //허니문전용등급
    getCommonCode({upCode: 356,codeLvl: "1"},honeymoonRateOptions);
    //사용유무
    getCommonCode({upCode: 515,codeLvl: "1"},useYnOptions);

    return {
      edited,
      sectorOptions,
      hotelRateOptions,
      restaurantRateOptions,
      rentcarRateOptions,
      optionRateOptions,
      packRegRateOptions,
      packRateOptions,
      honeymoonRegRateOptions,
      honeymoonRateOptions,
      useYnOptions,
      ageNumbers,
      fileUrl,
      previewImage,
      handleFileChange
    };
  },

});
</script>

<style lang="scss">
.custom-padding-margin {
  padding: 5px; /* Adjust as needed */
  margin: 5px; /* Adjust as needed */
}
.custom-file-upload {
  align-items: center;
  background-color: #0276FF;
  border-radius: 5px;
  border-style: none;
  box-shadow: rgba(255, 255, 255, 0.26) 0 1px 2px inset;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  flex-shrink: 0;
  font-family: "RM Neue",sans-serif;
  font-size: 100%;
  line-height: 1.15;
  margin: 0;
  padding: 10px 21px;
  text-align: center;
  text-transform: none;
  transition: color .13s ease-in-out,background .13s ease-in-out,opacity .13s ease-in-out,box-shadow .13s ease-in-out;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.custom-file-upload:active {
  background-color: #006AE8;
}

.custom-file-upload:hover {
  background-color: #1C84FF;
}
input[type="file"] {
  display: none;
}
</style>
