<template>
  <div id="hotel-info">

    <div class="flex flex-grow-1">
      <q-scroll-area
        style="height: 600px; min-width: 700px;"
      >
        <div class="q-pa-md">
          <div v-if="lcHotelInfo" class="example-row-column-width" >
              <div class="col-1 center">
                <q-btn @click="deleteHotelInfo(lcHotelInfo.hotelUuid)" flat round
                ><q-icon name="delete_forever"/>
                </q-btn>
              </div>
              <div class="col-2 center">
                호텔그룹: {{ lcHotelInfo.hotelGrpName}}
              </div>
              <div class="col-2 center">
                호텔등급: {{ lcHotelInfo.hotelLvlName}}
              </div>
              <div class="col-2 center">
                조식어린이나이: {{ lcHotelInfo.childAgeBreakfast }}
              </div>
              <div class="col-7">
                호텔체크인: {{ lcHotelInfo.checkinTime }}
              </div>
              <div class="col-2 center">
                호텔체크아웃: {{ lcHotelInfo.checkoutTime}}
              </div>
              <div class="col-2 center">
                리조트비용: {{ lcHotelInfo.resortFee}}
              </div>
            </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import {defineComponent, onMounted, ref, watch} from "vue";
import HotelService from "src/services/hotelInfo.service";

export default defineComponent({
  name: "TourContactList",
  components: {},
  props: {
    dataVal: Array,
    subDrawerClose: Function
  },
  setup(props, { emit }) {

    const lcHotelInfo = ref(props.dataVal);

    watch(() => props.dataVal, (newVal) => {
      lcHotelInfo.value = newVal;
    }, { deep: true });

    function emitCloseDrawer() {
        emit("drawer-closed");
    }

    function deleteHotelInfo(hotelUuid) {
      const confirmation = window.confirm("연락처를 삭제 하시겠습니까?");
      if (confirmation) {
        HotelService.deleteHotel(hotelUuid).then(
            (response) => {
              alert(response.data.message);
              emitCloseDrawer();
            },
            (error) => {
              console.log("saveComp failed", error);
            }
        )
      }
    }

    return {
      lcHotelInfo,
      deleteHotelInfo
    };

  },


});
</script>

<style lang="scss">

.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* Three columns with equal width */
  gap: 10px; /* Gap between items */
}

/* CSS to ensure items fill the available width within the grid */
.item {
  width: 100%;
}

</style>
<style lang="sass">
.example-row-column-width
  .center
    text-align: center
  .row > div
    padding: 5px 5px 5px 8px
    background: rgba(#999,.15)
    border: 1px solid rgba(#999,.2)
  .row + .row
    margin-top: 1rem
</style>
