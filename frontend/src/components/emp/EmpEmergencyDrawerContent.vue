<template>
  <div id="emp-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      :iconTitle="'health_and_safety'"
      :title="edited.empUuid ? '직원 수정' : '직원 등록'"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <emp-emergency-tn-list :data-val="edited" />
      </div>
    </drawer-comp>
  </div>
</template>
<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
// Layout
import EmpEmergencyTnList from "src/views/emp/EmpEmergencyTnList.vue";

export default defineComponent({
  name: "EmpEmergencyFormDrawer",
  components: {
    DrawerComp,
    EmpEmergencyTnList,
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
    const eOpenDrawer = ref(props.openDrawer);

    const updateEdited = {};
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
      eOpenDrawer,
      windowClose,
    };
  },
});
</script>
