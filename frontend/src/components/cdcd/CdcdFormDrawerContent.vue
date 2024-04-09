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
                  v-model="edited.beginDt"
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
                        <q-date v-model="edited.beginDt" minimal>
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
                    v-model="edited.endDt"
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
                        <q-date v-model="edited.endDt" minimal>
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
export default defineComponent({
  name: "CdcdFormDrawerContent",

  props: {
    dataVal: Object,
  },
  emits: ["update:dataVal"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

    const useYnOptions = ref([]);

    const monNumbers = ref([]);
    const yearNumbers = ref([]);

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

    return {
      edited,
      useYnOptions,
      monNumbers,
      yearNumbers,
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
