<template>
  <div id="emp-emergency-edit-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
      :iconTitle="'health_and_safety'"
      :title="edited.emergencyUuid ? '비상연락처 수정' : `비상연락처 추가`"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <card-comp-design title="연락처 정보 입력">
          <template #content>
            <div id="emp-form-drawer-content">
              <q-form v-if="edited">
                <q-card-section class="custom-padding-margin">
                  <div class="row q-col-gutter-lg">
                    <input
                      id="emergencyUuid"
                      v-model="edited.emergencyUuid"
                      hidden
                    />
                    <q-input
                      class="col-3"
                      type="text"
                      id="emerName"
                      v-model="edited.emerName"
                      label="이름 *"
                      stack-label
                      lazy-rules
                      :rules="[
                        (val) => val != '' || '이름을 입력 해 주십시오.',
                      ]"
                    />
                    <q-input
                      class="col-3"
                      type="text"
                      id="emerRelation"
                      v-model="edited.emerRelation"
                      label="관계"
                      lazy-rules
                      :rules="[(val) => !!val || '관계를 입력 해 주십시오.']"
                    />
                    <q-input
                      class="col-3"
                      type="text"
                      id="emerPhone1"
                      v-model="edited.emerPhone1"
                      label="전화번호"
                      lazy-rules
                      :rules="[
                        (val) => !!val || '전화번호를 입력 해 주십시오.',
                      ]"
                    />
                    <q-input
                      class="col-3"
                      type="text"
                      id="emerWorkPhone"
                      v-model="edited.emerWorkPhone"
                      label="직장전화"
                      :rules="[
                        (val) => !!val || '직장전화를 입력 해 주십시오.',
                      ]"
                    />
                    <q-input
                      class="col-3"
                      type="text"
                      id="empEng"
                      v-model="edited.empEng"
                      label="영문이름(Name Eng)"
                    />
                  </div>
                </q-card-section>
              </q-form>
            </div>
          </template>
        </card-comp-design>
      </div>
    </drawer-comp>
  </div>
</template>
<script>
import { ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import CardCompDesign from "src/components/common/CardCompDesign.vue";

export default {
  name: "EmpEmergencyEditDrawer",
  components: {
    DrawerComp,
    CardCompDesign,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
  },
  emits: ["update:dataVal", "update:openDrawer", "update:changeFlag"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const initEdited = {};

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
        initEdited.value = { ...newVal };
      },
      { deep: true }
    );

    const windowClose = () => {
      emit("update:openDrawer", false);
      emit("update:dataVal", initEdited);
    };

    return {
      edited,
      windowClose,
    };
  },
};
</script>
