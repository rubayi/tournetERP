<template>
  <card-comp-design id="maintrafficmemoComp" title="Transmittal">
    <template #content>
      <div class="row q-col-gutter-md text-center q-pa-md">
        <div v-if="showworkorder" class="col-2">
          <q-card
            bordered
            class="cursor-pointer q-hoverable"
            flat
            @click="movetorouter('/traffictransmittal')"
          >
            <q-card-section>
              <i class="fas fa-inbox" style="font-size: 3rem"></i>
            </q-card-section>
            <q-card-section class="q-pt-none"> Work Order </q-card-section>
          </q-card>
        </div>
      </div>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
// Store
import store from "src/store";
import router from "src/router";
//Component
import CardCompDesign from "components/common/CardCompDesign.vue";
export default defineComponent({
  name: "MainTrafficmemoContent",
  components: {
    CardCompDesign,
  },
  setup() {
    const showworkorder = ref<boolean>(false);

    showworkorder.value =
      store.getters.currentUserHasApplicationPermission("LOG_R");

    function movetorouter(routername: string) {
      router.push({ path: routername });
    }
    return {
      showworkorder,
      movetorouter,
    };
  },
});
</script>

<style lang="scss">
#maintrafficmemoComp {
  .q-hoverable:hover {
    color: #ffffff;
    background-color: #005288;
  }
}
</style>
