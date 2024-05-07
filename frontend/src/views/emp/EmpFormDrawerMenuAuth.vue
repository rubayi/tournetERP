<template>
  <card-comp-design title="Employer Authoroties">
    <template #content>
      <div id="emp-form-drawer-menu-content">
        <q-scroll-area style="height: 600px; min-width: 75%">
          <div class="row justify-end">
            <q-btn
              class="q-mr-md"
              label="Select All"
              @click="selectAll"
              style="color: darkgreen"
            />
            <q-btn
              class="q-mr-md"
              label="Clear All"
              @click="unselectAll"
              style="color: darkred"
            />
          </div>
          <div class="grid-container">
            <!-- Loop through each group -->
            <template v-for="(groupIndex, maxIdx) in menuMax" :key="maxIdx">
              <!-- Filter menu items for the current group -->
              <template
                v-if="
                  authList.filter(
                    (item) => item.groupCode === groupIndex.toString()
                  ).length
                "
              >
                <!-- Show a card-section for each group -->
                <q-card-section :key="'group_' + groupIndex">
                  <q-form>
                    <q-card-section class="q-pa-none">
                      <div class="q-tab-panels q-panel-parent">
                        <div class="q-panel scroll">
                          <q-item-label
                            class="text-center text-bold"
                            style="
                              font-size: 16px;
                              background-color: #f0f0f0;
                              padding-top: 5px;
                              padding-bottom: 5px;
                            "
                          >
                            {{ '권한 ' + groupIndex }}</q-item-label
                          >
                          <!-- Render menu items for the current group -->
                          <template
                            v-for="(authItem, index) in authList.filter(
                              (item) => item.groupCode === groupIndex.toString()
                            )"
                            :key="index"
                          >
                            <div style="display: flex; align-items: center">
                              <q-checkbox
                                v-model="authItem.authYn"
                                value="authItem.menuAuthUuid"
                                @click="handleCheckboxChange(authItem)"
                              />
                              <q-item-label>{{
                                authItem.menuAuthName
                              }}</q-item-label>
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
    </template>
  </card-comp-design>
</template>

<script>
import { defineComponent, ref } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
// Type
import { EmpAuthForm } from 'src/types/EmpAuthForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';

export default defineComponent({
  name: 'EmpFormDrawerMenuContent',
  components: {
    CardCompDesign,
  },
  //   props: {
  //     modelValue: {
  //       type: Object as () => EmpAuthForm,
  //       default: () => new EmpAuthForm(),
  //     },
  //   },
  setup(props, { emit }) {
    const authList = ref(props.optionList);
    const authListEmpId = ref(props.dataVal);
    const lcReqList = ref(props.reqList);

    // watch(
    //   () => props.optionList,
    //   (newVal) => {
    //     authList.value = newVal;
    //   },
    //   { deep: true }
    // );

    // watch(
    //   () => props.dataVal,
    //   (newVal) => {
    //     authListEmpId.value = newVal;
    //   },
    //   { deep: true }
    // );

    function selectAll() {
      authList.value.forEach((item) => {
        item.authYn = true;
        handleCheckboxChange(item);
      });
    }

    function unselectAll() {
      authList.value.forEach((item) => {
        item.authYn = false;
        handleCheckboxChange(item);
      });
    }

    function handleCheckboxChange(authItem) {
      authItem.authYn = !!authItem.authYn; // Toggle the authYn property
      const existingIndex = lcReqList.value.findIndex(
        (item) => item.menuAuthUuid === authItem.menuAuthUuid
      );
      if (existingIndex !== -1) {
        // Update the existing object
        if (!authItem.authYn) {
          // If the checkbox is unchecked, mark it for deletion
          lcReqList.value[existingIndex].deleteFlag = 'Y';
        } else {
          // If the checkbox is checked, remove the deleteFlag
          delete lcReqList.value[existingIndex].deleteFlag;
        }
      } else {
        // Push a new object
        lcReqList.value.push({
          menuAuthUuid: authItem.menuAuthUuid,
          deleteFlag: authItem.authYn ? undefined : 'Y',
        });
      }
    }

    // watch(
    //   lcReqList,
    //   (newVal) => {
    //     emit('update:reqList', newVal);
    //   },
    //   { deep: true, immediate: true }
    // );

    // function isCheckboxChecked(authItem) {
    //   const match = authListEmpId.value.find(item => item.menuAuthUuid === authItem.menuAuthUuid);
    //
    //   // If a match is found, set authItem.authYn to true, otherwise set it to false
    //   authItem.authYn = !!match; // !! converts match to a boolean
    //   // Return the result of the match
    //   return !!match;
    // }

    return {
      authList,
      handleCheckboxChange,
      selectAll,
      unselectAll,
    };
  },
});
</script>

<style lang="scss">
.grid-container {
  display: grid;
  grid-template-columns: repeat(6, 1fr); /* Three columns with equal width */
}

/* CSS to ensure items fill the available width within the grid */
.item {
  width: 100%;
}
</style>
