<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div id="officeform">
    <q-page class="q-pa-md">
      <div class="row justify-end q-pb-sm q-mr-sm">
        <q-btn
          icon="add"
          label="연락처"
          style="color: darkgreen"
          @click="createAction"
        />
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
      <emp-emergency-edit-drawer
        :open-drawer="eOpenDrawer"
        :drawer-width="1000"
        :dataVal="edited"
        :on-close-click="closeAction"
        @dataSaved="handlePageChange"
      />
    </q-page>
  </div>
</template>

<script>
import { ref, watch } from "vue";
import { EmpEmergencyFormTableConfig } from "src/views/emp/EmpEmergencyFormTableConfig";
import { initialData } from "src/views/emp/EmpEmergencyData";
import TableComp from "src/components/table/TableComp.vue";
import EmpEmergencyEditDrawer from "src/components/emp/EmpEmergencyEditDrawer.vue";

export default {
  name: "EmpEmergencyTnList",
  components: {
    TableComp,
    EmpEmergencyEditDrawer,
  },
  props: {
    dataVal: Object,
    onCloseClick: Function,
  },
  setup(props) {
    const empUuid = ref(props.dataVal);

    watch(
      () => props.dataVal,
      (newVal) => {
        empUuid.value = { ...newVal };
      },
      { deep: true }
    );

    return {
      empUuid,
    };
  },
  data() {
    return {
      colDefs: EmpEmergencyFormTableConfig.columns(),
      emergency: [],
      edited: initialData,
      initEdited: initialData,
      eOpenDrawer: false,
    };
  },
  methods: {
    createAction() {
      this.edited = initialData;
      this.eOpenDrawer = true;
    },

    closeAction() {
      this.edited = initialData;
      this.eOpenDrawer = !this.eOpenDrawer;
    },

    openAction(params) {
      this.edited = params.data;
      this.eOpenDrawer = true;
    },

    handlePageChange() {
      this.onReset();
    },

    searchEmergency() {
      this.$store
        .dispatch(
          `empEmergencyTn/selectEmployeeEmergencyByEmpUuid`,
          this.empUuid
        )
        .then((resMap) => {
          this.emergency = resMap.data;
        });
    },
    onReset() {
      this.emergency = [];
      this.searchEmergency();
    },
  },
  created() {
    this.searchEmergency();
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
