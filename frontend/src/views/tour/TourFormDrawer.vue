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

        <tour-form-drawer-content
            :data-val="edited"/>

      <div class="row col-12">
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

      <div v-if="contSector.toLowerCase() == 'hotel'" class="row col-12">
        <div v-if="edited.tourUuid && !lcHotelInfo.hotelUuid" class="q-pa-lg q-gutter-sm" >
          <q-btn
              icon="search"
              label="호텔 정보 등록"
              style="color: darkgreen"
              @click="hotelOpenAction"
          />
        </div>
        <hotel-info-form-drawer
            :open-drawer="hotelOpenDrawer"
            :drawer-width="700"
            :data-val="hotelEdited"
            :on-close-click="hotelCloseAction"
            @update:openDrawer="hotelOpenDrawer = $event"
            @drawer-closed="subDrawerClose"
        />
        <hotel-info v-if="lcHotelInfo.hotelUuid"
            :data-val="lcHotelInfo"
            :open-edit="hotelOpenAction"
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
import TourContactFormDrawer from "src/components/tour/TourContactFormDrawer.vue";
import HotelInfoFormDrawer from "src/components/tour/HotelInfoFormDrawer.vue";
import HotelInfo from "src/views/tour/HotelInfo.vue";
import {contactInitialData} from "src/views/tour/TourContact";
import {hotelInfoInitialData} from "src/views/tour/HotelInfo";

export default defineComponent({
  name: "TourFormDrawer",
  components: {
    TourContactFormDrawer,
    HotelInfoFormDrawer,
    TourContactList,
    DrawerComp,
    TourFormDrawerContent,
    HotelInfo,
  },
  props: {
    contSector: String,
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
    contactList: Array,
    hotelInfo: Object,
    subDrawerClose: Function,
  },
  emits: ["update:dataVal", "update:openDrawer"],
  setup(props, { emit }) {
    const edited = ref(props.dataVal);
    const eOpenDrawer = ref(props.openDrawer);

    const initialData = ref(null);

    const newOpenDrawer = ref(false);
    const contactEdited = ref(contactInitialData);
    const lcContactList = ref(props.contactList);

    const hotelOpenDrawer = ref(false);
    const hotelEdited = ref(hotelInfoInitialData);
    const lcHotelInfo = ref(props.hotelInfo);

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


    watch(() => props.hotelInfo, (newVal) => {
      lcHotelInfo.value = { ...newVal };
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

    function hotelOpenAction() {
      if(lcHotelInfo.value.hotelUuid){
          hotelEdited.value = lcHotelInfo.value;
      } else {
          hotelEdited.value.tourUuid = edited.value.tourUuid;
      }

      hotelOpenDrawer.value = !hotelOpenDrawer.value;
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

    function hotelCloseAction() {
      hotelEdited.value = {
        hotelUuid: 0,
        tourUuid: 0,
        hotelGrp: 0,
        childAgeBreakfast: 0,
        checkinTime: "",
        checkoutTime: "",
        resortFee: "",
      };
      hotelOpenDrawer.value = !hotelOpenDrawer.value;
    }

    onMounted(() => {

    });

    return {
      edited,
      eOpenDrawer,
      handleSaveData,
      handleDeleteData,
      lcContactList,
      contactEdited,
      newOpenDrawer,
      closeAction,
      openAction,
      hotelOpenAction,
      hotelOpenDrawer,
      hotelEdited,
      hotelCloseAction,
      lcHotelInfo
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
