<template>
  <card-comp-design
    id="mainsettingComp"
    v-if="showuser || showreport || showoffice || showcode"
    title="System Setting"
  >
    <template #content>
      <div class="row q-col-gutter-md text-center q-pa-md">
        <div v-if="showuser" class="col-3">
          <q-card
            bordered
            class="cursor-pointer q-hoverable"
            flat
            @click="movetorouter('/members')"
          >
            <q-card-section>
              <i class="fas fa-users" style="font-size: 3rem"></i>
            </q-card-section>
            <q-card-section class="q-pt-none"> Users </q-card-section>
          </q-card>
        </div>
        <div v-if="showoffice" class="col-3">
          <q-card
            bordered
            class="cursor-pointer q-hoverable"
            flat
            @click="movetorouter('/office')"
          >
            <q-card-section>
              <i class="far fa-building" style="font-size: 3rem"></i>
            </q-card-section>
            <q-card-section class="q-pt-none"> Office </q-card-section>
          </q-card>
        </div>
        <div v-if="showreport" class="col-3">
          <q-card
            bordered
            class="cursor-pointer q-hoverable"
            flat
            @click="movetorouter('/report')"
          >
            <q-card-section>
              <i class="fas fa-print" style="font-size: 3rem"></i>
            </q-card-section>
            <q-card-section class="q-pt-none"> Reports </q-card-section>
          </q-card>
        </div>
        <div v-if="showcode" class="col-3">
          <q-card
            bordered
            class="cursor-pointer q-hoverable"
            flat
            @click="movetorouter('/code')"
          >
            <q-card-section>
              <i class="fas fa-cogs" style="font-size: 3rem"></i>
            </q-card-section>
            <q-card-section class="q-pt-none"> Code </q-card-section>
          </q-card>
        </div>
      </div>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
// Store
import store from 'src/store';
import router from 'src/router';
//Component
import CardCompDesign from 'components/common/CardCompDesign.vue';
export default defineComponent({
  name: 'MainsettingComp',
  components: {
    CardCompDesign,
  },
  setup() {
    const showuser = ref<boolean>(false);
    const showoffice = ref<boolean>(false);
    const showreport = ref<boolean>(false);
    const showcode = ref<boolean>(false);

    showuser.value =
      store.getters.currentUserHasApplicationPermission('USER_R');
    showoffice.value =
      store.getters.currentUserHasApplicationPermission('OFFICE_R');
    showreport.value =
      store.getters.currentUserHasApplicationPermission('REP_R');
    showcode.value = store.getters.currentUserHasApplicationPermission('COD_R');
    function movetorouter(routername: string) {
      router.push({ path: routername });
    }
    return {
      showuser,
      showoffice,
      showreport,
      showcode,
      movetorouter,
    };
  },
});
</script>

<style lang="scss">
#mainsettingComp {
  .q-hoverable:hover {
    color: #ffffff;
    background-color: #005288;
  }
}
</style>
