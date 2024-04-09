<template>
  <div id="cdcd-form-drawer-content">

      <div class="flex flex-grow-1 q-pa-md">
        <q-card-section>
          <q-form v-if="edited">
            <q-card-section class="custom-padding-margin">
              <div class="row q-col-gutter-lg">
                <input id="creditCardUuid" v-model="edited.creditCardUuid" hidden />
                <q-input
                  class="col-3"
                  type="text"
                  id="mngNameKor"
                  v-model="edited.mngNameKor"
                  label="카드관리명 *"
                  :rules="[(val) => !!val || '한글카드관리명 입력 해 주십시오.']"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="mngNameEng"
                  v-model="edited.mngNameEng"
                  label="영문카드관리명"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="group"
                  v-model="edited.nameOnCard"
                  label="명의자"
                />
                <q-input
                    class="col-3"
                    type="text"
                    id="cardNumber"
                    v-model="edited.cardNumber"
                    label="카드번호"
                />
                <q-select
                  class="col-3"
                  v-model="edited.expMonth"
                  :options="monNumbers"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="유효일달"
                />
                <q-select
                  class="col-3"
                  v-model="edited.expYear"
                  :options="yearNumbers"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="유효일년"
                />

                <q-input
                  class="col-3"
                  v-model="edited.estDate"
                  mask="####/##/##"
                  :rules="['date']"
                  label="시작일"
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


                <q-input
                    class="col-3"
                    v-model="edited.estDate"
                    mask="####/##/##"
                    :rules="['date']"
                    label="종료일"
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

    const monNumbers = ref([]);
    const yearNumbers = ref([]);


    const fileUrl = fileInfo;

    const previewImage = ref(null);


    for (let i = 0
      ; i <= 20; i++) {
      monNumbers.value.push(i.toString().padStart(2, '0'));
    }

    const currentYear = +new Date().getFullYear();
    for (let t = currentYear; t <= (currentYear + 20); t++) {
      yearNumbers.value.push(t);
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
      monNumbers,
      fileUrl,
      previewImage,
      handleFileChange,
      yearNumbers
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
