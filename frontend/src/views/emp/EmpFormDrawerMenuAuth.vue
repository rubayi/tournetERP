<template>
  <card-comp-design title="Employer Authoroties" class="q-pt-md">
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
            <template v-for="groupIndex in menuMax" :key="groupIndex">
              <!-- Filter menu items for the current group -->
              <template
                v-if="
                  authList.filter((item) => item.groupCode == groupIndex).length
                "
              >
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
                              (item) => item.groupCode == groupIndex
                            )"
                            :key="index"
                          >
                            <div style="display: flex; align-items: center">
                              <q-checkbox
                                :model-value="isChecked(authItem)"
                                value="authItem.menuAuthUuid"
                                @click="
                                  handleCheckboxChange(authItem.menuAuthUuid)
                                "
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
<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
// Type
import { MenuAuthForm } from 'src/types/MenuAuthForm';

export default defineComponent({
  name: 'EmpFormDrawerMenuAuth',
  components: {
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Array as () => MenuAuthForm[],
      default: () => [] as MenuAuthForm[],
    },
    dataVal: {
      type: Array as () => number[],
      default: () => [],
    },
    menuMax: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const authList = ref(props.modelValue);
    const authListEmpId = ref(props.dataVal);

    watch(
      () => props.modelValue,
      (newVal) => {
        authList.value = newVal;
      },
      { deep: true }
    );

    watch(
      () => props.dataVal,
      (newVal) => {
        authListEmpId.value = newVal;
      },
      { deep: true }
    );

    const isChecked = (authItem: MenuAuthForm) => {
      return (
        authListEmpId.value.find((item) => item === authItem.menuAuthUuid) !==
        undefined
      );
    };

    const selectAll = () => {
      authList.value.forEach((item) => {
        if (
          authListEmpId.value &&
          !authListEmpId.value.includes(item.menuAuthUuid)
        ) {
          authListEmpId.value.push(item.menuAuthUuid);
        }
      });
      emit('update:authListEmpId', authListEmpId.value);
    };

    const unselectAll = () => {
      authListEmpId.value = [];
      emit('update:authListEmpId', authListEmpId.value);
    };

    const handleCheckboxChange = (id: number) => {
      const index = authListEmpId.value.indexOf(id);
      if (index !== -1) {
        authListEmpId.value.splice(index, 1);
      } else {
        authListEmpId.value.push(id);
      }
      emit('update:authListEmpId', authListEmpId.value);
    };

    return {
      authList,
      handleCheckboxChange,
      selectAll,
      isChecked,
      unselectAll,
    };
  },
});
</script>

<style lang="scss">
.grid-container {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
}
.item {
  width: 100%;
}
</style>
