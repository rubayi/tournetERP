<template>
  <div id="tour-form-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      @save="handleSaveData"
      @delete="handleDeleteData"
      :iconTitle="'person'"
      :title="edited.tourUuid ? '상품 수정' : '상품 등록'"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <tour-form-drawer-content :data-val="edited" />

      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { tourTn } from "src/store/tour.module";
import { auth } from "src/store/auth.module";
// Layout
import TourFormDrawerContent from "src/components/tour/TourFormDrawerContent.vue";

export default defineComponent({
  name: "TourFormDrawer",
  components: {
    DrawerComp,
    TourFormDrawerContent,
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

    const initialData = ref(null);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
        initialData.value = { ...newVal };
      },
      { deep: true }
    );

    async function handleSaveData(data) {
      //사용자 정보 관련 req 데이터
      const dataChanged =
        JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      const promises = [];
      if (
        dataChanged
      ) {
        if (edited.value.tourUuid != 0) {
          //사용자 수정
          if (dataChanged) {
            promises.push(
              tourTn.actions
                .updateTour(
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
                    console.log("saveTour failed", error);
                  }
                )
            );
          }
        } else {
          //사용자 등록
          promises.push(
            auth.actions
              .register(
                {
                  commit: () => {},
                  state: {},
                },
                edited.value
              )
              .then(
                (response) => {
                  alert(response.message);
                },
                (error) => {
                  console.log("saveTour failed", error);
                }
              )
          );
        }

        //Action after update,delete
        try {
          await Promise.all(promises);
          // Emit the event after all promises have resolved
          emitCloseDrawer();
        } catch (error) {
          console.error("One or more promises failed:", error);
          // Handle error if necessary
        }
      } else {
        alert("변경할 데이터가 없습니다.");
      }
    }

    function emitCloseDrawer() {
      emit("update:openDrawer", false);
      emit("dataSaved");
    }

    function resetForm() {
      if (edited.value && edited.value.tourUuid != 0) {
        edited.value = updateEdited;
      } else {
        edited.value = { ...initEdited };
      }
    }
    function handleDeleteData(data) {
      emit("delete", edited.value.tourUuid);
    }

    onMounted(() => {
      //getAuthList();
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
