<template>
  <q-card-section>
        <q-form v-if="edited">
          <q-card-section>
            <div class="row q-col-gutter-lg">
              <input
                id="empUuid"
                v-model="edited.empUuid"
                hidden
              />
              <q-input
                class="col-6"
                type="text"
                id="username"
                v-model="edited.username"
                label="사용자명(username) *"
                stack-label
                lazy-rules
                :rules="[ val => val != '' || '사용자명을 입력 해 주십시오.']"
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
                        :rules="[ val => !!val  || '암호를 입력 해 주십시오.']"
                />
                <q-input
                        class="col-3"
                        type="text"
                        id="empKor"
                        v-model="edited.empKor"
                        label="이름(한글이름) *"
                        lazy-rules
                        :rules="[ val => !!val  || '한글이름 입력 해 주십시오.']"
                />
                <q-input
                        class="col-3"
                        type="text"
                        id="empEng"
                        v-model="edited.empEng"
                        label="영문이름(Name Eng)"
                />
<!--                <q-select-->
<!--                        class="col-3"-->
<!--                        v-model="edited.empWorkType"-->
<!--                        :options="workOptions"-->
<!--                        option-value="codeValue"-->
<!--                        option-label="codeKr"-->
<!--                        emit-value-->
<!--                        map-options-->
<!--                        label="근무형태" />-->
<!--                <q-select-->
<!--                        class="col-3"-->
<!--                        v-model="edited.empDiv"-->
<!--                        :options="divOptions"-->
<!--                        option-value="codeValue"-->
<!--                        option-label="codeKr"-->
<!--                        emit-value-->
<!--                        map-options-->
<!--                        label="부서명" />-->
<!--                <q-select-->
<!--                        class="col-3"-->
<!--                        v-model="edited.empTitle"-->
<!--                        :options="titleOptions"-->
<!--                        option-value="codeValue"-->
<!--                        option-label="codeKr"-->
<!--                        emit-value-->
<!--                        map-options-->
<!--                        label="직위" />-->
<!--                <q-select-->
<!--                        class="col-3"-->
<!--                        v-model="edited.empRole"-->
<!--                        :options="empRoleOptions"-->
<!--                        option-value="codeValue"-->
<!--                        option-label="codeKr"-->
<!--                        emit-value-->
<!--                        map-options-->
<!--                        label="직책" />-->

                <q-input
                        class="col-6"
                        type="text"
                        id="username"
                        v-model="edited.empPhone"
                        label="핸드폰 *"
                        lazy-rules
                        :rules="[ val => val && val.length > 0 || '핸드폰 번호를 입력 해 주십시오.']"
                />

                <q-input
                        class="col-6"
                        type="text"
                        id="empWorkPhone"
                        v-model="edited.empWorkPhone"
                        label="내선번호(Work Phone) "
                />

                <q-input
                        class="col-6"
                        type="text"
                        v-model="edited.empEmail"
                        label="이메일* "
                        lazy-rules
                        :rules="[ val => val && val.length > 0 || '이메일을 입력 해 주십시오.']"
                />

                <q-input
                        class="col-6"
                        type="text"
                        id="empEmailBook"
                        v-model="edited.empEmailBook"
                        label="예약이메일(Email)"
                />
<!--                <q-select-->
<!--                        class="col-6"-->
<!--                        v-model="edited.empDobType"-->
<!--                        :options="dobTypeOptions"-->
<!--                        option-value="codeValue"-->
<!--                        option-label="codeKr"-->
<!--                        emit-value-->
<!--                        map-options-->
<!--                        label="생일타입" />-->
            </div>
          </q-card-section>
        </q-form>

  </q-card-section>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

const dataVal = ref([]);
export default defineComponent({
  name: "EmpFormDrawerContent",
  components: {

  },
  props: {
    dataVal: Object,
  },
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

      return {
          edited,
      };
  },
  mounted() {
    console.log('111Received dataVal:', this.dataVal);
  }
});
</script>

<style lang="scss"></style>
<script setup lang="ts">
</script>
