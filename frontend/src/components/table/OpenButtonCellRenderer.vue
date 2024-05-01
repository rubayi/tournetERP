<template>
  <span id="CellActions" v-if="showActions">
    <!--Writing the components this way is hacky, but since using quasar in the ag grid appears to be broken this is a stopgap measure -->
    <button
      class="q-btn q-btn-item non-selectable no-outline q-btn--flat q-btn--round q-btn--actionable q-focusable q-hoverable q-btn--wrap"
      role="button"
      tabindex="0"
      type="button"
      @click="openClickHandler"
    >
      <span class="q-focus-helper" />
      <span class="q-btn__wrapper col row q-anchor--skip">
        <span
          class="q-btn__content text-center col icon-assets q-anchor--skip justify-center row"
          style="display: flex; justify-content: center; align-items: center"
        >
          <img
            v-if="isActivebtn"
            src="img/folder.svg"
            style="width: 20px; height: 20px"
          />
          <img
            v-if="!isActivebtn"
            src="img/folder.svg"
            style="width: 20px; height: 20px"
          />
        </span>
      </span>
    </button>
  </span>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';

export default defineComponent({
  name: 'OpenButtonCellRenderer',
  props: {
    params: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      isActivebtn: false,
    };
  },
  computed: {
    showActions(): boolean {
      return true; //TODO conditional display of actions logic
    },
  },
  methods: {
    openClickHandler(): void {
      this.isActivebtn = true;
      this.params.context.componentParent.openAction(this.params.value);
    },
  },
});
</script>

<style scoped lang="scss">
#CellActions {
  .button {
    background-color: Transparent;
    background-repeat: no-repeat;
    border: 1px solid;
    cursor: pointer;
    overflow: hidden;
    outline: none;
  }
}
</style>
