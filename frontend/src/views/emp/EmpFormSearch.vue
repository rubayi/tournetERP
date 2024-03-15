<template>
  <q-form @submit="searchEmpList">
    <div class="row q-col-gutter-sm" style="max-width: 900px">
      <q-select class="col-3"
                v-model="searchIdx"
                :options="options"
                label="검색방법 *" />
      <q-select v-if="this.searchIdx == '상태'" class="col-3"
                v-model="searchEmpStatus"
                :options="empStatusOptions"
                option-value="codeValue"
                option-label="codeKr"
                emit-value
                map-options
                label="재직상태 *" />
      <q-input v-if="this.searchIdx != '상태'"
               v-model="searchWord"
               type="text"
               label="검색어 *"
      />
      <div class="q-py-md">
        <q-btn label="검색" type="submit" color="primary"/>
        <q-btn style="margin-left: 5px" label="전체검색" color="secondary" @click="onReset"/>
      </div>
      <div class="q-pa-md">
        <q-btn label="+ 사용자등록" color="green" @click="createAction"/>
      </div>
    </div>
  </q-form>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
const dataVal = ref([]);

export default defineComponent({
  name: "EmpFormSearch",
  components: {

  },
  props: {
    dataVal:Array,
  },
  setup(props, { emit }) {

  },
  mounted() {
    console.log('Received dataVal:', dataVal);
  }
});
</script>

<style lang="scss"></style>
