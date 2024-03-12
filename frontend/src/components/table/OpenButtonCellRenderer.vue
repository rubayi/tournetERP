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
          class="q-btn__content text-center col items-center q-anchor--skip justify-center row"
        >
          <i
            v-if="isActivebtn"
            aria-hidden="true"
            class="far fa-folder-open"
            role="img"
            style="font-size: 20px"
          />
          <i
            v-if="!isActivebtn"
            aria-hidden="true"
            class="far fa-folder"
            role="img"
            style="font-size: 20px"
          />
        </span>
      </span>
    </button>
  </span>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "OpenButtonCellRenderer",

  data(): { params: any; isActivebtn: boolean } {
    return {
      params: {},
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
    border: none;
    cursor: pointer;
    overflow: hidden;
    outline: none;
  }
}
</style>
