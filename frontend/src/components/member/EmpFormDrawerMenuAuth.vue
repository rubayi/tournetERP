<template>
  <div id="emp-form-drawer-content">

    <div class="flex flex-grow-1 q-pa-md">
      <q-card-section>
        <q-form v-if="edited">
          <q-card-section>
            <div class="row q-col-gutter-lg">
              <input id="empUuid" v-model="edited.empUuid" hidden />
              <q-input
                class="col-3"
                type="text"
                id="username"
                v-model="edited.username"
                label="사용자명(username) *"
                stack-label
                lazy-rules
                :rules="[
                    (val) => val != '' || '사용자명을 입력 해 주십시오.',
                  ]"
              />
              <q-input
                v-if="edited.empUuid != 0"
                class="col-3"
                type="password"
                id="password"
                v-model="edited.password"
                label="암호"
                hint="입력하지 않으면 변경되지 않습니다."
              />
              <q-input
                v-if="edited.empUuid == 0"
                class="col-3"
                type="password"
                id="password"
                v-model="edited.password"
                label="암호* "
                lazy-rules
                :rules="[(val) => !!val || '암호를 입력 해 주십시오.']"
              />
              <q-input
                class="col-3"
                type="text"
                id="empKor"
                v-model="edited.empKor"
                label="이름(한글이름) *"
                :rules="[(val) => !!val || '한글이름 입력 해 주십시오.']"
              />
              <q-input
                class="col-3"
                type="text"
                id="empEng"
                v-model="edited.empEng"
                label="영문이름(Name Eng)"
              />
              <q-select
                class="col-3"
                v-model="edited.empWorkType"
                :options="workOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="근무형태"
              />
              <q-select
                class="col-3"
                v-model="edited.empDiv"
                :options="divOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="부서명"
              />
              <q-select
                class="col-3"
                v-model="edited.empTitle"
                :options="titleOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="직위"
              />
              <q-select
                class="col-3"
                v-model="edited.empRole"
                :options="empRoleOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="직책"
              />
              <q-select
                class="col-4"
                v-model="edited.empStatus"
                :options="empStatusOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="재직상태"
              />
              <q-input
                class="col-4"
                type="text"
                id="username"
                v-model="edited.empPhone"
                label="핸드폰 *"
                :rules="[
                    (val) =>
                      (val && val.length > 0) ||
                      '핸드폰 번호를 입력 해 주십시오.',
                  ]"
              />
              <q-input
                class="col-4"
                type="text"
                id="empWorkPhone"
                v-model="edited.empWorkPhone"
                label="내선번호(Work Phone) "
              />
              <q-input
                class="col-3"
                type="text"
                v-model="edited.empEmail"
                label="이메일* "
                :rules="[
                    (val) =>
                      (val && val.length > 0) || '이메일을 입력 해 주십시오.',
                  ]"
              />
              <q-input
                class="col-3"
                type="text"
                id="empEmailBook"
                v-model="edited.empEmailBook"
                label="예약이메일(Email)"
              />
              <q-select
                class="col-3"
                v-model="edited.empDobType"
                :options="dobTypeOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="생일타입"
              />
              <q-input
                class="col-3"
                v-model="edited.empDob"
                mask="####/##/##"
                :rules="['date']"
              >
                <template v-slot:append>
                  <q-icon name="event" class="cursor-pointer">
                    <q-popup-proxy
                      cover
                      transition-show="scale"
                      transition-hide="scale"
                    >
                      <q-date v-model="edited.empDob" minimal>
                        <div class="row items-center justify-end">
                          <q-btn
                            v-close-popup
                            label="Close"
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
                type="text"
                id="empZip"
                v-model="edited.empZip"
                label="우편번호(Zip)"
              />
              <q-input
                class="col-3"
                type="text"
                id="empCity"
                v-model="edited.empCity"
                label="도시(City) "
              />
              <q-input
                class="col-3"
                type="text"
                id="empState"
                v-model="edited.empState"
                label="주/도(State) "
              />
              <q-select
                class="col-3"
                v-model="edited.empCountry"
                :options="countryOptions"
                option-value="codeUuid"
                option-label="codeKr"
                emit-value
                map-options
                label="국가(Country)"
              />
              <q-input
                class="col-6"
                bottom-slots
                type="text"
                id="empAddress1"
                v-model="edited.empAddress1"
                label="주소1(Address1) "
              />
              <q-input
                class="col-6"
                bottom-slots
                type="text"
                id="empAddress2"
                v-model="edited.empAddress2"
                label="주소2(Address2) "
              />

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
  emits: ["update:dataVal"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

    const workOptions = ref([]);
    const divOptions = ref([]);
    const titleOptions = ref([]);
    const empRoleOptions = ref([]);
    const dobTypeOptions = ref([]);
    const countryOptions = ref([]);
    const empStatusOptions = ref([]);

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

    //부서명
    getCommonCode({upCode: 19, codeLvl: "1" }, divOptions);
    //직위
    getCommonCode({upCode: 17, codeLvl: "1" }, titleOptions);
    //국가코드
    getCommonCode({upCode: 1, codeLvl: "1"}, countryOptions);
    //생일타입
    getCommonCode({upCode: 222,codeLvl: "1"},dobTypeOptions);
    //직책
    getCommonCode({upCode: 18,codeLvl: "1"},empRoleOptions);
    //근무형태
    getCommonCode({ upCode: 16, codeLvl: "1" }, workOptions);
    //재직상태
    getCommonCode({upCode: 15,codeLvl: "1"},empStatusOptions);


    return {
      edited,
      divOptions,
      workOptions,
      titleOptions,
      empRoleOptions,
      dobTypeOptions,
      countryOptions,
      empStatusOptions
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
