<template>
  <div id="emp-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      :iconTitle="'health_and_safety'"
      :title="'비상연락처'"
    >
      <emp-emergency-tn-list :data-val="edited" />
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
    dataVal: Number,
    onCloseClick: Function,
  },
  setup(props) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);
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
