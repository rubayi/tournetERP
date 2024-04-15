<template>
  <div id="tour-contact-list">
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
              >투어 연락처 등록</span>
          </div>
          <div class="col-12 text-right">
            <q-form v-if="edited">
              <q-card-section class="custom-padding-margin">
                <div class="row q-col-gutter-lg">
                  <input id="tourUuid" v-model="edited.tourUuid" hidden />
                  <q-select
                      class="col-3"
                      v-model="edited.contactType"
                      :options="typeOptions"
                      option-value="codeUuid"
                      option-label="codeKr"
                      emit-value
                      map-options
                      label="유형"
                  />
                  <q-input
                      class="col-3"
                      type="text"
                      id="contactCont"
                      v-model="edited.contactCont"
                      label="내용 *"
                  />
                  <q-select
                      class="col-3"
                      v-model="edited.repYn"
                      :options="repYnOptions"
                      option-value="codeUuid"
                      option-label="codeKr"
                      emit-value
                      map-options
                      label="대표유무"
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

import TrContactService from "src/services/tourcontact.service";
import {getCommonValue} from "src/utils/common";

export default defineComponent({
  name: "TourContactFormDrawer",
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

    const typeOptions = ref([]);
    const repYnOptions = ref([]);

    watch(() => props.openDrawer, (newVal) => {
      eOpenDrawer.value = newVal;
    }, { deep: true });

    watch(() => props.dataVal, (newVal) => {
      edited.value = {...newVal};
    }, { deep: true });

    async function handleSaveData(data) {
      console.log(edited.value);
      promises.push(
          TrContactService.updateContact(edited.value).then(
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
          TrContactService.deleteContact(edited.value.contactUuid).then(
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

    /* 공통코드값 가져오기 */
    async function getCommonCode(req, targetDataName) {
      try {
        targetDataName.value = await getCommonValue(req);
      } catch (error) {
        console.error(error);
      }
    }

    onMounted(() => {
    });

    //연락처유형
    getCommonCode({upCode: 539,codeLvl: "1"}, typeOptions);
    //사용유무
    getCommonCode({upCode: 515,codeLvl: "1"}, repYnOptions);

    return {
      edited,
      eOpenDrawer,
      typeOptions,
      repYnOptions,
      checkedContactUuids,
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
