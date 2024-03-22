<template>
  <div id="emp-form-drawer-menu-content">
    <div class="flex flex-grow-1 q-pa-md">
      <q-card-section>
        <q-form>
          <q-card-section>
            <div class="q-tab-panels q-panel-parent">
              <div class="q-panel scroll">
                <q-item-label class="q-py-0">사용 권한</q-item-label>
                <div class="flex flex-wrap">
                  <template v-for="(authItem, index) in authList" :key="index">
                    <div class="q-pa-sm" style="display: flex; align-items: center; width: 33.33%;">
                      <q-checkbox v-model="authItem.menuAuthUuid" />
                      <q-item-label>{{ authItem.menuAuthName }}</q-item-label>
                    </div>
                  </template>
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
import {defineComponent, onMounted, ref, watch} from "vue";
import { getCommonValue } from "src/utils/common";

export default defineComponent({
  name: "EmpFormDrawerMenuContent",

  props: {
    dataVal: Array,
    optionList: Array,
  },
  setup(props, { emit }) {
    const authList = ref(props.optionList);
    const authListEmpId = ref(props.dataVal);

    watch(() => props.optionList, (newVal) => {

      authList.value = newVal;

      console.log(authList);
    }, { deep: true});

    return {
      authList,
    };

  },
  mounted() {
    console.log('Received dataVal:', this.dataVal);
  }

});
</script>

<style lang="scss">

</style>
