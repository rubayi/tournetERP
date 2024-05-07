<template>
  <div id="drawer-comp">
    <div
      v-if="openDrawer"
      aria-hidden="true"
      class="fullscreen q-drawer__backdrop"
      style="background-color: rgba(0, 0, 0, 0.4)"
    ></div>
    <q-drawer
      behavior="desktop"
      elevated
      :model-value="openDrawer"
      overlay
      :side="side"
      :width="drawerWidth"
      @update:model-value="openDrawer = $event"
    >
      <q-layout view="hHh lpR fFf">
        <q-header class="commonThead text-white" height-hint="98">
          <q-toolbar class="col-12">
            <slot name="header" />
            <q-toolbar-title v-if="!hasHeaderSlot">
              <i :class="iconTitle"></i> {{ title }}
            </q-toolbar-title>
            <q-btn flat icon="close" round @click="cancelClicked" />
          </q-toolbar>
          <div class="bg-white">
            <div
              :style="{
                visibility: loading ? 'hidden' : 'visible',
              }"
            >
              <tabs-comp
                v-show="tabOptions"
                v-model="editableTab"
                :tab-options="tabOptions"
              />
              <q-separator />
            </div>
          </div>
        </q-header>
        <q-page-container>
          <q-page>
            <slot />
          </q-page>
        </q-page-container>
        <q-footer bordered class="bg-white">
          <q-toolbar>
            <q-space v-if="drawerWidth > 50" />
            <div class="q-gutter-md">
              <q-chip
                v-if="changesIndicator"
                class="q-mt-lg changes-indicator"
                color="secondary"
                icon="report"
                label="Changes Detected"
                outline
                rounded
                text-color="white"
              />
              <q-btn
                v-if="showPrintButton"
                color="secondary"
                icon="print"
                label="PRINT"
                @click="confirmPrinted"
              />
              <q-btn
                v-if="showConfirmButton"
                :color="confirmButtonColor"
                :disable="editableLoading"
                :icon="confirmIcon"
                :label="confirmButtonLabel"
                @click="confirmClicked"
              />
              <q-btn
                v-if="isAddMode && showConfirmButton"
                color="secondary"
                icon="arrow_forward"
                label="Next New"
                @click="nextNewClicked"
              />

              <q-btn
                v-if="showDeleteButton && !isAddMode"
                color="red"
                :disable="editableLoading"
                icon="delete"
                :label="deleteButtonLabel"
                text-color="white"
                @click="deleteClicked"
              />
              <q-btn
                v-if="showResetButton"
                color="white"
                icon="autorenew"
                label="Reset"
                outline
                text-color="primary"
                @click="resetClicked"
              />
              <q-btn
                color="white"
                icon="close"
                :label="cancelButtonLabel"
                text-color="primary"
                @click="cancelClicked"
              />
            </div>
          </q-toolbar>
        </q-footer>
      </q-layout>
      <inner-loading-comp :loading="editableLoading" />
    </q-drawer>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { computed, defineComponent, ref, watch } from 'vue';
import { Screen } from 'quasar';
import TabsComp from 'src/components/tabs/TabsComp.vue';
import InnerLoadingComp from 'src/components/common/InnerLoadingComp.vue';
import { useSyncModelValue } from 'src/utils/helpers/useSyncModelValue';
import { TabOptions } from 'src/types/TabOptions';

export default defineComponent({
  name: 'DrawerComp',
  components: { InnerLoadingComp, TabsComp },
  props: {
    cancelButtonLabel: {
      type: String,
      default: 'Close',
    },
    cancelButtonicon: {
      type: String,
      default: 'fa fa-chevron-left',
    },
    iconTitle: {
      type: String,
      default: 'far fa-folder-open',
    },
    changesIndicator: {
      type: Boolean,
      default: false,
    },
    deleteButtonLabel: {
      type: String,
      default: 'Delete',
    },
    showDeleteButton: {
      type: Boolean,
      default: false,
    },
    showResetButton: {
      type: Boolean,
      default: false,
    },
    confirmButtonLabel: {
      type: String,
      default: 'Confirm',
    },
    confirmButtonColor: {
      type: String,
      default: 'primary',
    },
    confirmIcon: {
      type: String,
      default: undefined,
    },
    showConfirmButton: {
      type: Boolean,
      default: true,
    },
    loading: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    side: {
      type: String as () => 'left' | 'right',
      default: 'Left',
    },
    showPrintButton: {
      type: Boolean,
      default: false,
    },
    tab: {
      type: String,
      default: '',
    },
    tabOptions: {
      type: Object as () => Array<TabOptions>,
      default: null,
    },
    title: {
      type: String,
      default: '',
    },
    width: {
      type: [Number, String],
      default: '650px',
    },
    isAddMode: {
      type: Boolean,
      default: false,
    },
  },
  emits: [
    'update:tab',
    'cancel-clicked',
    'reset-clicked',
    'update:modelValue',
    'update:loading',
    'confirm-clicked',
    'confirm-printeded',
    'delete-clicked',
    'next-new-clicked',
  ],
  setup(props, { emit, slots }) {
    const openDrawer = ref(false);
    watch(
      () => props.modelValue,
      (newValue) => {
        openDrawer.value = newValue;
      }
    );

    watch(
      () => openDrawer.value,
      (newValue) => {
        emit('update:modelValue', newValue);
      }
    );

    function cancelClicked() {
      emit('cancel-clicked');
    }
    function resetClicked() {
      emit('reset-clicked');
    }
    function confirmClicked() {
      emit('confirm-clicked');
    }
    function confirmPrinted() {
      emit('confirm-printeded');
    }

    function nextNewClicked() {
      emit('next-new-clicked');
    }

    function deleteClicked() {
      emit('delete-clicked');
    }

    const drawerWidth = computed(() => {
      if (_.isString(props.width)) {
        return Number(props.width.replace('px', ''));
      } else if (
        _.isNumber(props.width) &&
        0 <= props.width &&
        props.width <= 100
      ) {
        return Screen.width * (props.width / 100);
      }
      return 650;
    });

    const hasHeaderSlot = computed(() => {
      return !!slots.header;
    });

    const editableTab = ref();
    useSyncModelValue(props, 'tab', emit, 'update:tab', editableTab);

    const editableLoading = ref();
    useSyncModelValue(
      props,
      'loading',
      emit,
      'update:loading',
      editableLoading
    );

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    async function loopThroughTabsThenRunFunction(func: any) {
      await loopThroughTabs();
      func();
    }

    async function loopThroughTabs() {
      if (props.tabOptions) {
        const timer = (ms: number) => new Promise((res) => setTimeout(res, ms));
        editableLoading.value = true;
        const pauseTime = 250;
        await timer(pauseTime);
        const tabs = props.tabOptions.map((tabOption) => tabOption.name);
        tabs.push(editableTab.value);
        for (let i = 0; i < tabs.length; i++) {
          editableTab.value = tabs[i];
          await timer(pauseTime);
        }
        await timer(pauseTime);
        editableLoading.value = false;
      }
    }

    function switchToTabWithError() {
      if (props.tabOptions) {
        setTimeout(() => {
          const tabsOptionWithError: TabOptions | undefined =
            props.tabOptions.find((tabOption: TabOptions) => tabOption.alert);
          if (tabsOptionWithError) {
            editableTab.value = tabsOptionWithError.name;
          }
        }, 0);
      }
    }

    return {
      cancelClicked,
      resetClicked,
      confirmClicked,
      confirmPrinted,
      nextNewClicked,
      deleteClicked,
      openDrawer,
      drawerWidth,
      hasHeaderSlot,
      editableTab,
      loopThroughTabsThenRunFunction,
      editableLoading,
      switchToTabWithError,
    };
  },
});
</script>

<style type="text/scss" lang="scss">
#drawer-comp {
  .q-drawer-container > .q-drawer {
    top: 0 !important;
  }
}

.changes-indicator {
  -webkit-animation: pulse 2s ease-out 0s infinite;
  animation: pulse 2s ease-out 0s infinite;
}

.commonThead {
  background-image: url('~src/assets/top_main.png');
}
</style>
