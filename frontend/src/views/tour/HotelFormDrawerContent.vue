<template>
  <card-comp-design :title="t('hotelBasicInfo')">
    <template #content>
      <q-card-section v-if="hotelformData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <input-comp
              v-model="hotelformData.checkinTime"
              class="full-width"
              clearable
              :label="t('checkinTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="hotelformData.checkoutTime"
              class="full-width"
              clearable
              :label="t('checkoutTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <number-comp
              v-model="hotelformData.resortFee"
              class="full-width"
              clearable
              :label="t('resortFee')"
              outlined
            />
          </div>
          <!--어린이 나이-->
          <div class="col-6">
            <select-comp
              v-model="hotelformData.childAgeBreakfast"
              class="full-width"
              :label="t('childAgeBreakfast')"
              :options="ageNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="hotelformData.couponUse"
              :label="t('couponuseyn')"
              class="full-width select-comp-padding"
              :options="useYnList"
              outlined
            />
          </div>
<!--          <div class="col-6">-->
<!--            <select-comp-->
<!--              v-model="hotelformData.hotelLvl"-->
<!--              :label="t('hotelLvl')"-->
<!--              class="full-width select-comp-padding"-->
<!--              :options="hotelLevelList"-->
<!--              outlined-->
<!--            />-->
<!--          </div>-->
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import {defineComponent, ref, onMounted, watch} from 'vue';
// Component
import CardCompDesign from 'src/components/common/CardCompDesign.vue';
import InputComp from 'src/components/common/InputComp.vue';
import SelectComp from 'src/components/common/SelectComp.vue';
import NumberComp from 'src/components/common/NumberComp.vue';
// Type
import { HotelForm } from 'src/types/HotelForm';
import { SelectOption } from 'src/types/SelectOption';
// Store
import store from 'src/store';
// Helper
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { loadOptionsList } from 'src/utils/commoncode/commonCode';
import { HotelService } from 'src/services/HotelService';
// helper
import i18n from 'src/i18n';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';

export default defineComponent({
  name: 'HotelFormDrawerContent',
  components: {
    InputComp,
    SelectComp,
    NumberComp,
    CardCompDesign,
  },
  props: {
    modelValue: {
      type: Object as () => HotelForm,
      default: () => new HotelForm(),
    },
    tourUuid: {
      type: Number,
      default: 0,
    },
    deleteFlag: {
      type: Boolean,
      default: false,
    },
    deleteHotel: {
      type: Number,
      default: 0,
    },
  },
  setup(props, { emit }) {
    const loading = ref<boolean>(false);
    const hotelformData = ref<HotelForm>(new HotelForm());
    const locale = i18n.global.locale.value;
    const showinsertbutton = ref<boolean>(false);
    const lcDeleteFlag = ref<boolean>(false);
    const lcDeleteHotel = ref<number>(0);

    watch(
      () => props.deleteFlag,
      (newValue) => {
        lcDeleteFlag.value = newValue;
      },
      { deep: true, immediate: true }
    );

    watch(
      () => props.deleteHotel,
      (newValue) => {
        lcDeleteHotel.value = newValue;
      },
      { deep: true, immediate: true }
    );

    watch(hotelformData, () => {
      lcDeleteHotel.value = hotelformData.value.hotelUuid;
      lcDeleteFlag.value = !!hotelformData.value.hotelUuid;

    });

    const loadData = () => {
      loading.value = true;
      hotelformData.value = props.modelValue;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        HotelService.searchHotelByTour(props.tourUuid)
          .then((response) => {
            if (response) {
              hotelformData.value = response;
              lcDeleteHotel.value = hotelformData.value.hotelUuid;
              loading.value = false;
            }
        });
      }
      lcDeleteFlag.value =
        store.getters.currentUserHasApplicationPermission('CONT_D');
    };

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      hotelformData
    );

    const ageNumbers = ref<SelectOption[]>([]);

    loadAgeOptions();
    function loadAgeOptions() {
      for (let i = 0; i <= 20; i++) {
        let tnum = i.toString();
        ageNumbers.value.push({ label: tnum, value: i });
      }
    }

    const useYnList = ref<SelectOption[]>([]);
    loadOptionsList(35, useYnList, locale);

    const hotelLevelList = ref<SelectOption[]>([]);
    loadOptionsList(30, hotelLevelList, locale);

    onMounted(loadData);

    return {
      t: i18n.global.t,
      hotelformData,
      ageNumbers,
      useYnList,
      hotelLevelList,
    };
  },
});
</script>

<style lang="scss"></style>
