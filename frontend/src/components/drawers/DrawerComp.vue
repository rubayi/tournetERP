<template>
  <div id="drawer-comp">
    <div
        v-if="internalOpenDrawer"
        class="fullscreen q-drawer__backdrop"
        style="background-color: rgba(0, 0, 0, 0.4)"
    >
    <q-drawer
        elevated
        side="right"
        show-if-above
        bordered
        overlay
        v-model="internalOpenDrawer"
        :width="drawerWidth"
    >
      <q-layout view="hHh lpR fFf">
        <q-header class="commonThead text-white" height-hint="98">
          <q-toolbar class="col-12">
            <slot name="header" />

            <q-btn flat icon="close" round @click="cancelClicked" />
          </q-toolbar>
          <div class="bg-white">

          </div>
        </q-header>
        <q-page-container>
          <q-page>
            <slot />
          </q-page>
        </q-page-container>
        <q-footer bordered class="bg-white">
          <q-toolbar>
            <div class="q-gutter-md">
              <q-btn
                  color="white"
                  text-color="primary"
                  @click="cancelClicked"
              />
            </div>
          </q-toolbar>
        </q-footer>
      </q-layout>
    </q-drawer>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";

const openDrawer = ref([]);
const drawerWidth = ref([]);

export default defineComponent({
  name: "DrawerComp",
  components: {

  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
  },

  setup(props, { emit, slots }) {
    const internalOpenDrawer = ref(props.openDrawer);

    // Watch for changes in the openDrawer prop and update internalOpenDrawer
    watch(() => props.openDrawer, (newValue) => {
      internalOpenDrawer.value = newValue;
    });

    const cancelClicked = () => {
      internalOpenDrawer.value = false;
    };

    return {
      internalOpenDrawer,
      cancelClicked,
    };
  },

  methods: {

  },
  mounted() {
    console.log('Received openDrawer:', this.openDrawer);
    console.log('Received drawerWidth:', this.drawerWidth);
  }
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
