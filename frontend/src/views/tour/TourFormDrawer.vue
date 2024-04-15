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
            :data-val="edited"/>

      <div v-if="edited.tourUuid" class="q-pa-lg q-gutter-sm" >
        <q-btn
            icon="search"
            label="연락처 등록"
            style="color: darkgreen"
            @click="openAction"
        />
      </div>
      <tour-contact-form-drawer
          :open-drawer="newOpenDrawer"
          :drawer-width="700"
          :data-val="contactEdited"
          :on-close-click="closeAction"
          @update:openDrawer="newOpenDrawer = $event"
          @drawer-closed="subDrawerClose"
      />
      <tour-contact-list
          :data-val="lcContactList"
          @drawer-closed="subDrawerClose"
      />
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
import { tourTn } from "src/store/tour.module";

// Layout
import TourFormDrawerContent from "src/components/tour/TourFormDrawerContent.vue";
import TourContactList from "src/views/tour/TourContactList.vue";
import {contactInitialData} from "src/views/tour/TourContact";
import TourContactFormDrawer from "src/components/tour/TourContactFormDrawer.vue";

export default defineComponent({
  name: "TourFormDrawer",
  components: {
    TourContactFormDrawer,
    TourContactList,
    DrawerComp,
    TourFormDrawerContent,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
    contactList: Array,
    subDrawerClose: Function,
  },
  emits: ["update:dataVal", "update:openDrawer", "update:changeFlag"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const initialData = ref(null);

    const compList = ref([]);

    const newOpenDrawer = ref(false);
    const contactEdited = ref(contactInitialData);
    const lcContactList = ref(props.contactList);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal };
      },
      { deep: true }
    );

    watch(() => props.contactList, (newVal) => {
      lcContactList.value = { ...newVal };
    }, { deep: true });

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

    function openAction() {

      contactEdited.value.tourUuid = edited.value.tourUuid;
      newOpenDrawer.value = !newOpenDrawer.value;
    }

    function closeAction() {
      contactEdited.value = {
        contactUuid: 0,
        tourUuid: 0,
        contactType: "",
        repYn: "",
        contactCont: "",
      };
      newOpenDrawer.value = !newOpenDrawer.value;
    }

    // function getCompanyList(){
    //   const searchReq = {}
    //   compTn.actions.searchCompList(
    //         {
    //           commit: () => {},
    //           state: {},
    //         },
    //         searchReq
    //         )
    //         .then(
    //           (response) => {
    //             compList.value = response.compList;
    //             console.log(compList.value);
    //           },
    //           (error) => {
    //             console.log("saveTour failed", error);
    //         }
    //   );
    // }

    onMounted(() => {
      //업체목록
      //getCompanyList();
    });

    return {
      edited,
      compList,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      lcContactList,
      contactEdited,
      newOpenDrawer,
      closeAction,
      openAction
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
