<template>
  <div id="emp-form-drawer-menu-auth">
    <q-card-section>
      <q-form v-if="edited">
        <q-card-section>
          <div class="row q-col-gutter-lg">
            <input id="empUuid" v-model="edited.empUuid" hidden />
            <q-input
              class="col-6"
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
          </div>
        </q-card-section>
      </q-form>
    </q-card-section>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";
import { getCommonValue } from "src/utils/common";


export default defineComponent({
  name: "EmpFormDrawer",
  components: {
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
  },
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const workOptions = ref([]);
    const divOptions = ref([]);
    const titleOptions = ref([]);
    const empRoleOptions = ref([]);
    const dobTypeOptions = ref([]);
    const countryOptions = ref([]);
    const empStatusOptions = ref([]);

    function handleSaveData(data) {
      emit("save", edited.value);
    }

    function handleDeleteData(data) {
      emit("delete", edited.value.empUuid);
    }

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

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
      }
    );

    return {
      edited,
      divOptions,
      workOptions,
      titleOptions,
      empRoleOptions,
      dobTypeOptions,
      countryOptions,
      handleSaveData,
      handleDeleteData,
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
