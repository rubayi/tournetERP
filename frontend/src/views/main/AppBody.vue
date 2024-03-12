<template>
  <div id="mainform">
    <q-page class="q-pa-md column">
      <div class="row q-col-gutter-md">
        <div class="col-7 q-qa-md">
          <main-trafficmemo-content ref="maintrafficmemoComp" />
        </div>
        <div class="col-5 q-qa-md">
          <main-setting-content ref="mainsettingComp" />
        </div>
      </div>
    </q-page>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
// Layout
import MainTrafficmemoContent from "@/views/main/MainTrafficmemoContent.vue";
import MainSettingContent from "@/views/main/MainSettingContent.vue";

export default defineComponent({
  name: "MainForm",
  components: {
    MainTrafficmemoContent,
    MainSettingContent,
  },
  setup() {
    const mainh3facilitiesComp = ref();
    const mainsettingComp = ref();

    return {
      mainh3facilitiesComp,
      mainsettingComp,
    };
  },
});
</script>

<style lang="scss"></style>
