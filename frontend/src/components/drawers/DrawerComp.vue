<template>
  <div id="drawer-comp">
    <div
      aria-hidden="true"
      v-if="openDrawer"
      class="fullscreen q-drawer__backdrop"
      style="background-color: rgba(0, 0, 0, 0.4)"
    ></div>
    <q-drawer
      v-show="openDrawer"
      behavior="desktop"
      elevated
      side="right"
      bordered
      overlay
      v-model="innerOpenDrawer"
      :width="drawerWidth"
    >
      <q-layout view="hHh lpR fFf">
        <q-header class="commonThead text-white" height-hint="98">
          <q-toolbar class="col-12">
            <slot name="header" v-if="onCloseClick" />
<!--            <q-toolbar-title v-if="!hasHeaderSlot">-->
<!--              <i :class="iconTitle"></i> {{ title }}-->
<!--            </q-toolbar-title>-->
            <q-btn flat icon="close" round @click="onCloseClick" />
          </q-toolbar>
          <div class="bg-white"></div>
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
              <q-btn
                color="green"
                icon="add"
                :label="saveButtonLabel"
                text-color="white"
                @click="onSaveClick"
              />
              <q-btn
                color="red"
                icon="delete"
                :label="deleteButtonLabel"
                text-color="white"
                @click="onDeleteClick"
              />
              <q-btn
                color="white"
                icon="arrow_right"
                :label="cancleButtonLabel"
                text-color="blue"
                @click="onCloseClick"
              />
            </div>
          </q-toolbar>
        </q-footer>
      </q-layout>
    </q-drawer>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";

export default defineComponent({
  name: "DrawerComp",
  components: {},
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    onCloseClick: Function,
    onSaveClick: Function,
    onDeleteClick: Function,
    saveButtonLabel: {
      type: String,
      default: "저장",
    },
    deleteButtonLabel: {
      type: String,
      default: "삭제",
    },
    cancleButtonLabel: {
      type: String,
      default: "취소",
    },
  },

  setup(props, { emit, slots }) {
    const innerOpenDrawer = ref(props.openDrawer);
    watch(
      () => props.openDrawer,
      (newValue) => {
        innerOpenDrawer.value = newValue;
      }
    );
    return {
      innerOpenDrawer,
    };
  },

  methods: {},
  mounted() {
    console.log("Received openDrawer:", this.openDrawer);
    console.log("Received drawerWidth:", this.drawerWidth);
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
  background-image: url("src/assets/top_main.png");
}
</style>
