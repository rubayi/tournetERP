<template>
  <div id="comp-form-drawer-content">

      <div class="flex flex-grow-1 q-pa-md">
        <q-card-section>
          <q-form v-if="edited">
            <q-card-section>
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
                <q-select
                  class="col-3"
                  v-model="edited.couponYn"
                  :options="compRoleOptions"
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
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="호텔등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.optionRate"
                  :options="optionRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="옵션등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.rentcarRate"
                  :options="rentcarRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="렌트카등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.restaurantRate"
                  :options="restaurantRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="식당등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.packRegRate"
                  :options="packRegRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="패키지일반등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.packRate"
                  :options="packRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="패키지전용등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.honeymoonRegRate"
                  :options="honeymoonRegRateOptions"
                  option-value="codeValue"
                  option-label="codeKr"
                  emit-value
                  map-options
                  label="허니문일반등급"
                />
                <q-select
                  class="col-3"
                  v-model="edited.honeymoonRate"
                  :options="honeymoonRateOptions"
                  option-value="codeValue"
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
                  class="col-4"
                  type="text"
                  id="logoFile"
                  v-model="edited.logoFile"
                  label="로고"
                />
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
                              label="설립일"
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

export default defineComponent({
  name: "EmpFormDrawerContent",

  props: {
    dataVal: Object,
  },
  emits: ["update:dataVal", "update:changeFlag"],
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

    const ageNumbers = ref([]);

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
      ageNumbers
    };
  },

});
</script>

<style lang="scss">
#officeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
