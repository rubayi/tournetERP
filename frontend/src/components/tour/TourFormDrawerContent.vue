<template>
  <div id="tour-form-drawer-content">

      <div class="flex flex-grow-1 q-pa-md">
        <q-card-section>
          <q-form v-if="edited">
            <q-card-section class="custom-padding-margin">
              <div class="row q-col-gutter-lg">
                <input id="tourUuid" v-model="edited.tourUuid" hidden />

                <q-select
                    class="col-3"
                    v-model="edited.tourArea"
                    :options="areaMidOptions"
                    option-value="codeUuid"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="지역"
                />
                <q-select
                    class="col-3"
                    v-model="edited.tourAreaSub"
                    :options="areaSmlOptions"
                    option-value="codeUuid"
                    option-label="codeKr"
                    emit-value
                    map-options
                    label="서브지역"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="tourKor"
                  v-model="edited.tourKor"
                  label="상품명 *"
                  :rules="[(val) => !!val || '한글상품명 입력 해 주십시오.']"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="tourEng"
                  v-model="edited.tourEng"
                  label="영문상품명"
                />
                <q-input
                  class="col-3"
                  type="text"
                  id="group"
                  v-model="edited.tourGroup"
                  label="그룹"
                />
                <q-select
                  class="col-3"
                  v-model="edited.minAge"
                  :options="ageNumbers"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="제한연령"
                />
                <q-select
                  class="col-3"
                  v-model="edited.childAge"
                  :options="ageNumbers"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="아이나이"
                />
                <q-select
                  class="col-3"
                  v-model="edited.youthAge"
                  :options="ageNumbers"
                  option-value="codeUuid"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="쥬니어나이"
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
  name: "TourFormDrawerContent",

  props: {
    dataVal: Object,
    uploadFile: Function,
  },
  emits: ["update:dataVal"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

    const sectorOptions = ref([]);
    const areaBigOptions = ref([]);
    const areaMidOptions = ref([]);
    const areaSmlOptions = ref([]);

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

    //상품유형
    getCommonCode({upCode: 22,codeLvl: "1"}, sectorOptions);
    //중지역
    getCommonCode({upCode: 31,codeLvl: "2"}, areaMidOptions);
    //소지역
    getCommonCode({upCode: 144,codeLvl: "3"}, areaSmlOptions);

    //지역에 따른 하위지역 보여주기
    watch(() => edited.value.tourArea, async (newVal) => {
      if (newVal) {
        edited.value.tourAreaSub = "";
        const selectedAreaMidOption = areaMidOptions.value.find(option => option.codeUuid === newVal);
        if (selectedAreaMidOption) {
          const areaSmlReq = { upCode: selectedAreaMidOption.codeUuid, codeLvl: "3" };
          console.log(areaSmlReq);
          await getCommonCode(areaSmlReq, areaSmlOptions);
        }
      }
    }, { deep: true});

    return {
      edited,
      sectorOptions,
      areaBigOptions,
      areaMidOptions,
      areaSmlOptions,
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
