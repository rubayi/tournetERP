<template>
  <div id="tour-form-drawer">
    <drawer-comp
      v-model="openDrawer"
      v-model:loading="loading"
      cancel-buttonicon="fa fa-chevron-right"
      center-title
      :confirm-button-color="confirmbuttoncolor"
      :confirm-button-label="confirmbuttonlabel"
      :delete-button-label="deletebuttonlabel"
      :cancel-button-label="cancelbuttonlabel"
      :reset-button-label="resetbuttonlabel"
      :confirm-icon="confirmicon"
      icon-title="fas fa-cogs"
      :show-confirm-button="showconfirmbutton"
      :show-delete-button="showdeletebutton"
      :show-print-button="false"
      side="right"
      :title="title"
      :width="70"
      @cancel-clicked="closeDrawer"
      @confirm-clicked="saveUpdatedTourData"
      @delete-clicked="openDeleteConfirm = true"
      ref="drawerComp"
    >
      <div>
        <q-tabs v-model="tab" align="left">
          <q-tab name="content"
                 :label="t('tourBasic')" class="q-ml-md" />
          <q-tab
            name="tourcontact"
            :label="t('contactList')"
            v-if="tourformData.tourUuid"
          />
          <q-tab
            v-if="hotelYn == 'Y'  && tourformData.tourUuid != 0"
            name="hotel"
            :label="t('hotelInfoBasic')"
          />

          <q-tab
            v-if="hotelYn == 'Y'  && tourformData.tourUuid != 0"
            name="amenity"
            :label="t('hotelAmenity')"
          />
          <q-tab
            v-if="tourformData.tourUuid"
            name="info"
            :label="t('tourInfo')"
          />

          <q-tab
            v-if="tourformData.tourUuid"
            name="tourService"
            :label="t('tourService')"
          />
        </q-tabs>

        <q-tab-panels v-model="tab">
          <q-tab-panel name="content">
            <tour-form-drawer-content
              v-model="tourformData"
              :hotel-yn = "hotelYn"
              :tour-area-list="tourAreaList"
              :tour-area-sub-list="tourAreaSubList"
              :sector-list="sectorList"
              :company-list="companyList"
              :hotel-grp-list = "hotelGrpList"
              :prepaid-how-list="prepaidHowList"
              ref="tourFormDrawerContent"
            />
          </q-tab-panel>

          <q-tab-panel name="tourcontact">
            <tour-contact-list
              v-if="tourformData.tourUuid"
              :tour-uuid="tourformData.tourUuid"
              ref="tourContactList"
            />
          </q-tab-panel>

          <q-tab-panel name="hotel">
            <hotel-form-drawer-content
              v-model="hotelformData"
              v-if="hotelYn == 'Y' && tourformData.tourUuid != 0"
              :tour-uuid="tourformData.tourUuid"
              :delete-flag="deleteFlag"
              :delete-hotel="deleteHotelUuid"
              @update:deleteFlag="deleteFlag = $event"
              @update:deleteHotel="deleteHotelUuid = $event"
              ref="hotelformDrawerContent"
            />
          </q-tab-panel>

          <q-tab-panel name="amenity">
            <amenity-list
              v-if="hotelYn == 'Y' && tourformData.tourUuid != 0"
              :tour-uuid="tourformData.tourUuid"
              ref="amenityList"
            />
          </q-tab-panel>

          <q-tab-panel name="info">
            <info-list
              v-if="tourformData.tourUuid"
              :tour-uuid="tourformData.tourUuid"
              ref="infoList"
            />
          </q-tab-panel>

          <q-tab-panel name="tourService">
            <tour-service-list
              v-if="tourformData.tourUuid"
              :hotel-yn = "hotelYn"
              :tour-uuid="tourformData.tourUuid"
              ref="tourServiceList"
            />
          </q-tab-panel>

        </q-tab-panels>
      </div>
    </drawer-comp>
  </div>
  <dialog-comp
    v-model="openDeleteConfirm"
    :action-button-label="deletebuttonlabel"
    max-width="500px"
    :modal-title="deleteTitle"
    @confirm-clicked="deleteTourForm"
  >
    <template #htmlContent>
      <div>{{ t('deleteconfirmmsg') }}</div>
    </template>
  </dialog-comp>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
//Lang
import i18n from 'src/i18n';
// Components
import DrawerComp from 'src/components/drawers/DrawerComp.vue';
import DialogComp from 'src/components/common/DialogComp.vue';
// View Layout
import TourFormDrawerContent from 'src/views/tour/TourFormDrawerContent.vue';
import HotelFormDrawerContent from 'src/views/tour/HotelFormDrawerContent.vue';
import TourContactList from 'src/views/tour/TourContactList.vue';
import InfoList from 'src/views/tour/InfoList.vue';
import TourServiceList from 'src/views/tour/TourServiceList.vue';
import AmenityList from 'src/views/tour/AmenityList.vue';

// Services
import { TourService } from 'src/services/TourService';
// Types
import { TourForm } from 'src/types/TourForm';
import { SelectOption } from 'src/types/SelectOption';
import { HotelForm } from "src/types/HotelForm";
import { AmenityForm } from "src/types/AmenityForm";

// Store
import store from 'src/store';
//helper
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import { HotelService } from "src/services/HotelService";

export default defineComponent({
  name: 'TourFormDrawer',
  components: {
    TourContactList,
    DrawerComp,
    DialogComp,
    TourFormDrawerContent,
    HotelFormDrawerContent,
    InfoList,
    TourServiceList,
    AmenityList
  },
  props: {
    tourSeq: {
      type: Number,
      default: 0,
    },
    hotelYn: {
      type: String,
      default: "N",
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    tourCategory: {
      type: Number,
      default: 0,
    },
    tourAreaList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    tourAreaSubList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    sectorList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    companyList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    hotelGrpList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
    prepaidHowList: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: [
    'update:modelValue',
    'update:drawerData',
    'tourform-saved',
    'tourform-deleted',
    'tourform-drawer-closed',
    'hotelform-saved',
  ],
  setup(props, { emit }) {
    const title = i18n.global.t('manageTour');
    const tourformData = ref<TourForm>(new TourForm());
    const hotelformData = ref<HotelForm>(new HotelForm());
    const loading = ref<boolean>(false);
    const openDrawer = ref<boolean>(false);
    const confirmbuttoncolor = ref<string>('primary');
    const confirmbuttonlabel = ref<string>(i18n.global.t('change'));
    const deletebuttonlabel = ref<string>(i18n.global.t('delete'));
    const deleteTitle = ref<string>(i18n.global.t('deleteTitle'));
    const resetbuttonlabel = ref<string>(i18n.global.t('reset'));
    const cancelbuttonlabel = ref<string>(i18n.global.t('cancel'));
    const confirmicon = ref<string>('plus');
    const showconfirmbutton = ref<boolean>(false);
    const showdeletebutton = ref<boolean>(false);
    const tourformDrawerContent = ref();
    const hotelformDrawerContent = ref();
    const drawerComp = ref();
    const openDeleteConfirm = ref<boolean>(false);
    const deleteFlag = ref<boolean>(false);
    const deleteHotelUuid = ref<number>(0);
    const lcTourCategory = ref(props.tourCategory);
    const tab = ref<string>('content');
    const lcHotelYn = ref<string>(props.hotelYn);

    watch(
      () => props.modelValue,
      (newValue) => {
        openDrawer.value = newValue;
      }
    );
    watch(
      () => props.tourCategory,
      (newValue) => {
        lcTourCategory.value = newValue;
      },
      { deep: true, immediate: true }
    );
    watch(
      () => openDrawer.value,
      (newValue) => {
        emit('update:modelValue', newValue);
        getTourFormData();
      }
    );

    // Reset Drawer
    function resetDrawer() {
      tourformData.value = new TourForm();

      if (props.tourSeq != 0) {
        confirmbuttoncolor.value = 'warning';
        confirmbuttonlabel.value = i18n.global.t('change');
        confirmicon.value = 'edit';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_WU');
        showdeletebutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_D');
      } else {
        confirmbuttoncolor.value = 'primary';
        confirmbuttonlabel.value = i18n.global.t('add');
        confirmicon.value = 'add';
        showconfirmbutton.value =
          store.getters.currentUserHasApplicationPermission('CONT_WU');
        showdeletebutton.value = false;
      }
    }

    // Loading One Data
    function getTourFormData() {
      resetDrawer();
      if (props.tourSeq != 0) {
        loading.value = true;
        TourService.getOneTourForm(props.tourSeq)
          .then((response) => {
            tourformData.value = response;
          })
          .finally(() => {
            loading.value = false;
          });
      }
    }

    //Add & Edit
    function saveUpdatedTourData() {
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification(i18n.global.t('saving'));
      loading.value = true;
      tourformData.value.tourCategory = lcTourCategory.value;

      if (tourformData.value) {
        TourService.saveTourForm(tourformData.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('saved')
            );
            if (props.tourSeq != 0) {
              emit('tourform-saved', response);
              tourformData.value = new TourForm(response);
            } else {
              emit('tourform-saved', response);
              closeDrawer();
            }
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

      if (props.tourSeq != 0 && hotelformData.value) {
        hotelformData.value.tourUuid = props.tourSeq;

        HotelService.saveHotelForm(hotelformData.value)
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

    //Delete Data
    function deleteAction() {
      openDeleteConfirm.value = true;
    }
    function deleteTourForm() {
      loading.value = true;

      if(!deleteFlag.value) {
        TourService.deleteTourForm(props.tourSeq)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('deletesucess')
            );
            emit('tourform-deleted', response);
            closeDrawer();
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

      if(deleteFlag.value && deleteHotelUuid.value !=0) {
        HotelService.deleteHotelForm(deleteHotelUuid.value)
          .then((response) => {
            notificationHelper.createSuccessNotification(
              i18n.global.t('deletesucess')
            );
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

    function closeDrawer() {
      openDrawer.value = false;
      resetDrawer();
      emit('tourform-drawer-closed');
    }
    return {
      t: i18n.global.t,
      tab,
      title,
      tourformData,
      hotelformData,
      loading,
      drawerComp,
      openDrawer,
      closeDrawer,
      tourformDrawerContent,
      hotelformDrawerContent,
      confirmbuttoncolor,
      confirmbuttonlabel,
      deletebuttonlabel,
      deleteTitle,
      resetbuttonlabel,
      cancelbuttonlabel,
      confirmicon,
      showconfirmbutton,
      showdeletebutton,
      deleteAction,
      openDeleteConfirm,
      deleteTourForm,
      saveUpdatedTourData,
      deleteFlag,
      deleteHotelUuid,
      lcHotelYn
    };
  },
});
</script>

<style lang="scss">
#tourform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
