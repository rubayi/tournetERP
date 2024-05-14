<template>
  <div id="expansion-item-comp">
    <q-expansion-item
      class="expansion-item"
      :class="{ focused: focused }"
      :content-inset-level="contentInsetLevel"
      :expand-icon="expandIcon"
      :icon="icon"
      :label="label"
    >
      <slot />
    </q-expansion-item>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ExpansionItemComp',
  props: {
    contentInsetLevel: {
      type: Number,
      default: 0,
    },
    expandIcon: {
      type: String,
      default: 'expand_more',
    },
    focused: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: '',
    },
    label: {
      type: String,
      default: '',
    },
  },
});
</script>

<style type="text/scss" lang="scss">
// #expansion-item-comp {
//   .focused.q-expansion-item
//     .q-expansion-item__container
//     > .q-item:first-of-type {
//     font-weight: bold;
//     font-size: 110%;
//     color: black;
//   }

//   .expanded.q-expansion-item
//     .q-expansion-item__container
//     > .q-item:first-of-type
//     > .q-focus-helper {
//     background: currentColor !important;
//     opacity: 0.15 !important;
//   }
// }
#expansion-item-comp {
  .focused.q-expansion-item
    .q-expansion-item__container
    > .q-item:first-of-type
    > .q-focus-helper {
    background: currentColor !important;
    opacity: 0.15 !important;
  }
}
</style>
