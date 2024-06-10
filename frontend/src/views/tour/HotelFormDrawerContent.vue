<template>
  <card-comp-design :title="t('hotelBasicInfo')">
    <template #content>
      <q-card-section v-if="editHotelData">
        <div class="row q-col-gutter-md">
          <div class="col-6">
            <input-comp
              v-model="editHotelData.checkinTime"
              class="full-width"
              clearable
              :label="t('checkinTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <input-comp
              v-model="editHotelData.checkoutTime"
              class="full-width"
              clearable
              :label="t('checkoutTime')"
              outlined
              required
            />
          </div>
          <div class="col-6">
            <number-comp
              v-model="editHotelData.resortFee"
              class="full-width"
              clearable
              :label="t('resortFee')"
              outlined
            />
          </div>
          <!--어린이 나이-->
          <div class="col-6">
            <select-comp
              v-model="editHotelData.childAgeBreakfast"
              class="full-width"
              :label="t('childAgeBreakfast')"
              :options="ageNumbers"
              outlined
            />
          </div>
          <div class="col-6">
            <select-comp
              v-model="editHotelData.couponUse"
              :label="t('couponuseyn')"
              class="full-width select-comp-padding"
              :options="useYnList"
              outlined
            />
          </div>
<!--          <div class="col-6">-->
<!--            <select-comp-->
<!--              v-model="editHotelData.hotelLvl"-->
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
import { defineComponent, ref, onMounted } from 'vue';
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
  },
  setup(props, { emit }) {
    const loading = ref<boolean>(false);
    const editHotelData = ref<HotelForm>(new HotelForm());
    const locale = i18n.global.locale.value;
    const showinsertbutton = ref<boolean>(false);

    const loadData = () => {
      loading.value = true;
      editHotelData.value = props.modelValue;
      showinsertbutton.value =
        store.getters.currentUserHasApplicationPermission('CONT_WU');
      if (store.getters.currentUserHasApplicationPermission('CONT_R')) {
        HotelService.searchHotelByTour(props.tourUuid).then((response) => {
          if (response) {
            editHotelData.value = response;
            loading.value = false;
          }
        });
      }
    };

    function saveUpdatedHotelData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      if (editHotelData.value) {
        editHotelData.value.tourUuid = props.tourUuid;

        HotelService.saveHotelForm(editHotelData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            emit('hotelform-saved', response);
          })
          .catch((error) => {
            notificationHelper.createErrorNotification(
              notificationHelper.formatResponseToErrorMessage(error.response)
            );
          })
          .finally(() => {
            notificationHelper.dismissOngoingNotification();
            loading.value = false;
          });
      }
    }

    function deleteHotelData() {
      loading.value = true;
      HotelService.deleteHotelForm(props.tourUuid)
        .then((response) => {
          notificationHelper.createSuccessNotification(
            i18n.global.t('deletesucess')
          );
          emit('hotelform-deleted', response);
        })
        .catch((error) => {
          notificationHelper.createErrorNotification(
            notificationHelper.formatResponseToErrorMessage(error.response)
          );
        })
        .finally(() => {
          notificationHelper.dismissOngoingNotification();
          loading.value = false;
        });
    }

    useSyncModelValue(
      props,
      'modelValue',
      emit,
      'update:modelValue',
      editHotelData
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
      editHotelData,
      ageNumbers,
      useYnList,
      hotelLevelList,
      saveUpdatedHotelData,
      deleteHotelData,
    };
  },
});
</script>

<style lang="scss"></style>
