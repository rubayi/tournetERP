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
        <tour-form-drawer-content
            :data-val="edited"
            :comp-list="compList"/>
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { tourTn } from "src/store/tour.module";
import { compTn} from "src/store/comp.module";
// Layout
import TourFormDrawerContent from "src/components/tour/TourFormDrawerContent.vue";
import EventBus from "src/common/EventBus";

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

    const initialData = ref(null);

    const compList = ref([]);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
        initialData.value = { ...newVal };
      },
      { deep: true }
    );

    const promises = [];

    async function handleSaveData(data) {
      //사용자 정보 관련 req 데이터
      const dataChanged =
        JSON.stringify(initialData.value) !== JSON.stringify(edited.value);

      if (dataChanged) {
        //수정
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

    async function handleDeleteData(data) {
      emit("delete", edited.value.tourUuid);

      promises.push(
          tourTn.actions
              .deleteTour(
                  {
                    commit: () => {},
                    state: {},
                  },
                  edited.value.tourUuid
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

    function getCompanyList(){
      const searchReq = {}
      compTn.actions.searchCompList(
            {
              commit: () => {},
              state: {},
            },
            searchReq
            )
            .then(
              (response) => {
                compList.value = response.compList;
                console.log("asdf");
                console.log(compList.value);
              },
              (error) => {
                console.log("saveTour failed", error);
            }
      );
    }

    onMounted(() => {
      //업체목록
      getCompanyList();
    });

    return {
      edited,
      compList,
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
