<template>
  <card-comp-design :title="t('hotelRoomInfo')">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-md">

          <div class="col-12">
            <template v-for="(roominfo, index) in roomInfoList" :key="index">
                <q-card-section>
                  <q-form>
                    <q-card-section class="q-pa-none">
                      <div class="q-tab-panels q-panel-parent">
                        <div class="q-panel scroll">
                          <q-item-label
                            class="text-center text-bold"
                            style="
                              font-size: 16px;
                              background-color: #f0f0f0;
                              padding-top: 5px;
                              padding-bottom: 5px;
                            "
                          >
                            {{ roominfo.label }}</q-item-label
                          >
                          {{ roominfo.label }}
                        </div>
                      </div>
                    </q-card-section>
                  </q-form>
                </q-card-section>
            </template>
          </div>

        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>
<script lang="ts">
import { ref, defineComponent } from 'vue';
// Components
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
// Type
import { SelectOption } from 'src/types/SelectOption';
import { HotelRoomForm } from 'src/types/HotelRoomForm';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import i18n from 'src/i18n';
import {MenuAuthForm} from "@/types/MenuAuthForm";

export default defineComponent({
  name: 'InfoDrawerContent',
  components: {
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => HotelRoomForm,
      default: () => new HotelRoomForm(),
    },
    roomUuid: {
      type: Number,
      default: 0,
    },
  },

  setup(props, { emit }) {
    const locale = i18n.global.locale.value;
    const hotelRoomData = ref<HotelRoomForm>(new HotelRoomForm());

    const setContactUuid = () => {
      hotelRoomData.value.roomUuid = props.roomUuid;
    };
    setContactUuid();

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      hotelRoomData
    );

    const roomInfoList = ref<SelectOption[]>([]);
    loadOptionsList(510, roomInfoList, locale);

    return {
      t: i18n.global.t,
      roomInfoList,
      hotelRoomData
    };
  },
});
</script>
<style lang="scss">
.select-padding {
  padding-bottom: 20px;
}
</style>
