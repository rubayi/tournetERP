<template>
  <div id="hotel-info-form-drawer">
      <drawer-comp
              :open-drawer="eOpenDrawer"
              :drawerWidth="drawerWidth"
              :on-close-click="onCloseClick"
              @save="handleSaveData"
              @delete="handleDeleteData"
      >
      <q-page class="q-pa-md">
        <div class="row">
          <div class="col q-pr-md flex items-center">
            <span class="part_title text-primary">
              <q-icon name="person" class="q-ml-xs q-mb-xs" size="md"></q-icon
              >호텔정보 등록</span>
          </div>
          <div class="col-12 text-right">
            <q-form v-if="edited">
              <q-card-section class="custom-padding-margin">
                <div class="row q-col-gutter-lg">
                  <q-select
                      class="col-3"
                      v-model="edited.hotelGrp"
                      :options="groupOptions"
                      option-value="codeUuid"
                      option-label="codeKr"
                      emit-value
                      map-options
                      label="그룹"
                  />
                  <q-select
                      class="col-3"
                      v-model="edited.childAgeBreakfast"
                      :options="ageNumbers"
                      option-value="codeUuid"
                      option-label="codeKr"
                      emit-value
                      map-options
                      label="조식어린이나이"
                  />
                  <q-input
                      class="col-3"
                      type="text"
                      id="contactCont"
                      v-model="edited.checkinTime"
                      label="호텔체크인 *"
                  />
                  <q-input
                      class="col-3"
                      type="text"
                      id="contactCont"
                      v-model="edited.checkoutTime"
                      label="호텔체크아웃 *"
                  />
                  <q-input
                      class="col-3"
                      type="text"
                      id="contactCont"
                      v-model="edited.resortFee"
                      label="리조트비용 *"
                  />
                </div>
              </q-card-section>
            </q-form>
          </div>
        </div>
      </q-page>

      </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch, onMounted } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";
// Layout

import HotelService from "src/services/hotelInfo.service";
import {getCommonValue} from "src/utils/common";

export default defineComponent({
  name: "HotelInfoFormDrawer",
  components: {
    DrawerComp,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
  },
  emits: ["update:dataVal", "update:openDrawer", "update:changeFlag"],
  setup(props, { emit }) {
    const eOpenDrawer = ref(props.openDrawer);

    const edited = ref(props.dataVal);

    const checkedContactUuids = ref([]);

    const promises = [];

    const groupOptions = ref([]);
    const ageNumbers = ref([]);

    for (let i = 0
        ; i <= 20; i++) {
      ageNumbers.value.push(i);
    }

    watch(() => props.openDrawer, (newVal) => {
      eOpenDrawer.value = newVal;
    }, { deep: true });

    watch(() => props.dataVal, (newVal) => {
      edited.value = {...newVal};
    }, { deep: true });

    async function handleSaveData(data) {
      console.log(edited.value);
      promises.push(
          HotelService.updateHotel(edited.value).then(
            (response) => {
              alert(response.data.message);
              emitCloseDrawer();
            },
            (error) => {
                console.log("saveComp failed", error);
            }
        )
       );

      try {
        await Promise.all(promises);

      } catch (error) {
        console.error("One or more promises failed:", error);
        // Handle error if necessary
      }
    }



    function emitCloseDrawer() {
      if (typeof props.onCloseClick === 'function') {
        props.onCloseClick();
      }
      emit("update:openDrawer", false);
      emit("drawer-closed");
    }

    async function handleDeleteData(data) {
      promises.push(
          HotelService.deleteHotel(edited.value.hotelUuid).then(
              (response) => {
                alert(response.data.message);
                emitCloseDrawer();
              },
              (error) => {
                console.log("saveComp failed", error);
              }
          )
      );

      try {
        await Promise.all(promises);
      } catch (error) {
        console.error("One or more promises failed:", error);
        // Handle error if necessary
      }
    }

    onMounted(() => {
    });

    /* 공통코드값 가져오기 */
    async function getCommonCode(req, targetDataName) {

      try {
        targetDataName.value = await getCommonValue(req);
      } catch (error) {
        console.error(error);
      }
    }

    //그룹
    getCommonCode({upCode: 518,codeLvl: "1"}, groupOptions);

    return {
      edited,
      ageNumbers,
      eOpenDrawer,
      checkedContactUuids,
      handleSaveData,
      handleDeleteData,
      groupOptions
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
