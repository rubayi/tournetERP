<template>
  <div id="cdcd-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <cdcd-form-drawer-content
          :data-val="edited"
          />

      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { cdcdmngTn  } from "src/store/cdcdmng.module";

// Layout
import CdcdFormDrawerContent from "src/components/cdcd/CdcdFormDrawerContent.vue";
import CdcdMngService from "src/services/cdcdmng.service";

export default defineComponent({
  name: "CdcdFormDrawer",
  components: {
    DrawerComp,
    CdcdFormDrawerContent,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
  },
  emits: ["update:dataVal", "update:openDrawer"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const updateEdited = {};
    const initEdited = {};

    const initialData = ref(props.dataVal);

    watch(() => props.dataVal, (newVal) => {

      edited.value = { ...newVal };

    }, { deep: true });

    async function handleSaveData(data) {

      //업체 정보 관련 req 데이터
      const dataChanged = JSON.stringify(initialData.value) !== JSON.stringify(edited.value);
        console.log(initialData.value);
        console.log(edited.value);
      // Create contactReq object with contactUuids Set and creditCardUuid
      const promises = [];

      if (!dataChanged) {
          alert("변경할 데이터가 없습니다.");
      } else {

        //업체수정
        //if (dataChanged) {

          promises.push(
              cdcdmngTn.actions.updateCdcdmng(
                {
                    commit: () => {},
                    state: {},
                },
                edited.value
                )
                .then(
                    (response) => {
                        alert(response.data.message);
                    },
                    (error) => {
                        console.log("saveEmp failed", error);
                    }
                )
           );

        // }

        //Action after update,delete
        try {
          await Promise.all(promises);
          // Emit the event after all promises have resolved
          emitCloseDrawer();
        } catch (error) {
          console.error("One or more promises failed:", error);
          // Handle error if necessary
        }
      }
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("drawer-closed");
    }

    function resetForm() {
      if (edited.value && edited.value.creditCardUuid != 0) {
        edited.value = updateEdited;
      } else {
        edited.value = { ...initEdited };
      }
    }
    function handleDeleteData(data) {
      emit("delete", edited.value.creditCardUuid);
    }

    onMounted(() => {
      //getContactList();

    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
    };
  },

});
</script>

<style lang="scss">
#officeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
