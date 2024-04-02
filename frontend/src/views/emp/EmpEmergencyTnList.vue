<template>
  <div id="officeform">
    <q-page class="q-pa-md">
      <div class="row justify-end q-pb-sm q-mr-sm">
        <q-btn icon="add" label="연락처" style="color: darkgreen" />
      </div>
      <div id="officeform-grid-container" class="row">
        <table-comp
          id="memberform-grid"
          class="ag-theme-alpine grid"
          :column-defs="colDefs"
          :row-data="emergency"
          :on-cell-clicked="openAction"
        />
      </div>
      <!-- <emp-form-drawer
        :open-drawer="openDrawer"
        :drawer-width="drawerWidth"
        :dataVal="edited"
        :on-close-click="closeAction"
        @update:openDrawer="openDrawer = $event"
        @dataSaved="handlePageChange"
      /> -->
    </q-page>
  </div>
</template>

<script>
import { ref, watch, onMounted } from "vue";
import { EmpEmergencyFormTableConfig } from "src/views/emp/EmpEmergencyFormTableConfig";
import { initialData } from "src/views/emp/EmpEmergencyData";
import TableComp from "src/components/table/TableComp.vue";

export default {
  name: "EmpEmergencyTnList",
  components: {
    TableComp,
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
    const munuAuthList = ref([]);
    const empMunuAuthList = ref([]);
    const initialData = ref(null);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
        initialData.value = { ...newVal };
        getAuthListByEmp();
      },
      { deep: true }
    );

    onMounted(() => {});

    return {
      edited,
      eOpenDrawer,
      empMunuAuthList,
      munuAuthList,
    };
  },
  data() {
    return {
      colDefs: EmpEmergencyFormTableConfig.columns(),
      emergency: [],
      initEdited: initialData,
    };
  },
};
</script>

<style lang="scss">
#officeform {
  #officeform-grid-container {
    flex-grow: 1;

    #officeform-grid {
      height: 100%;
    }
  }
}
</style>
