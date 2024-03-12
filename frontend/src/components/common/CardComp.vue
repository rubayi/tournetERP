<template>
  <div :class="{ 'card-comp': true, fit: fit }">
    <q-card
      :bordered="bordered"
      class="fit"
      :flat="flat"
      :style="{ 'border-color': borderColor, 'border-width': borderWidth }"
    >
      <q-card-section :class="headerSectionClass">
        <div v-if="title" :class="titleClass">{{ title }}</div>
        <slot v-else name="header"></slot>
      </q-card-section>
      <slot name="content" />
      <div class="card-comp-footer">
        <slot name="footer" />
      </div>
    </q-card>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";

export default defineComponent({
  name: "CardComp",
  props: {
    title: {
      type: String,
      default: "",
    },
    titleClass: {
      type: String,
      default: "text-h6 text-primary",
    },
    headerSectionClass: {
      type: String,
      default: "q-pb-none",
    },
    fit: {
      type: Boolean,
      default: true,
    },
    flat: {
      type: Boolean,
      default: false,
    },
    bordered: {
      type: Boolean,
      default: false,
    },
    borderColor: {
      type: String,
      default: "",
    },
  },
  setup(props) {
    const borderWidth = computed(() => {
      let width = "1px";
      if (props.borderColor) {
        width = "3px";
      }
      return width;
    });

    return {
      borderWidth,
    };
  },
});
</script>

<style lang="scss">
.card-comp {
  .q-card__section {
    overflow: auto;
  }
}

.card-comp-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
}
</style>
