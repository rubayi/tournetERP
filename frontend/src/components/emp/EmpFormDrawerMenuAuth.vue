<template>
  <div id="emp-form-drawer-menu-content">

    <div class="flex flex-grow-1">
      <q-scroll-area
        style="height: 600px; min-width: 700px;"
      >
        <div class="grid-container">
          <!-- Loop through each group -->
          <template v-for="(groupIndex, maxIdx) in menuMax" :key="maxIdx">
            <!-- Filter menu items for the current group -->
            <template v-if="authList.filter(item => item.groupCode === groupIndex.toString()).length">
              <!-- Show a card-section for each group -->
              <q-card-section :key="'group_' + groupIndex">
                <q-form>
                  <q-card-section>
                    <div class="q-tab-panels q-panel-parent">
                      <div class="q-panel scroll">
                        <q-item-label>{{ '권한' + groupIndex }}</q-item-label>
                        <!-- Render menu items for the current group -->
                        <template v-for="(authItem, index) in authList.filter(item => item.groupCode === groupIndex.toString())" :key="index">
                          <div class="q-pa-sm" style="display: flex; align-items: center;">
                            <q-checkbox v-model="authItem.authYn"
                                        value="authItem.menuAuthUuid"
                                        :checked="isCheckboxChecked(authItem)"
                                        @click="handleCheckboxChange(authItem)"/>
                            <q-item-label>{{ authItem.menuAuthName }}</q-item-label>
                          </div>
                        </template>
                      </div>
                    </div>
                  </q-card-section>
                </q-form>
              </q-card-section>
            </template>
          </template>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import {defineComponent, ref, watch} from "vue";

export default defineComponent({
  name: "EmpFormDrawerMenuContent",

  props: {
    dataVal: Array,
    optionList: Array,
    menuMax: Number,
    reqList: Array,
  },
  setup(props, { emit }) {
    const authList = ref(props.optionList);
    const authListEmpId = ref(props.dataVal);
    const lcReqList = ref(props.reqList);

    watch(() => props.optionList, (newVal) => {
      authList.value = newVal;
    }, { deep: true});

    function handleCheckboxChange(authItem) {
        if (authItem.authYn) {
          lcReqList.value.push({ menuAuthUuid: authItem.menuAuthUuid });
        } else {
          lcReqList.value.push({ menuAuthUuid: authItem.menuAuthUuid, deleteFlag:"Y" });
        }
    }

    watch(lcReqList, (newVal) => {
        emit('update:reqList', newVal);
    }, { deep: true, immediate: true });

    function isCheckboxChecked(authItem) {
      return authListEmpId.value.map(item => item.menuAuthUuid === authItem.menuAuthUuid);
      return true;
    }


    return {
      authList,
      handleCheckboxChange,
      isCheckboxChecked
    };

  },


});
</script>

<style lang="scss">
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* Three columns with equal width */
  gap: 10px; /* Gap between items */
}

/* CSS to ensure items fill the available width within the grid */
.item {
  width: 100%;
}
</style>
