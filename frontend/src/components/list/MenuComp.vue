<template>
  <q-menu
    :anchor="anchor"
    :self="self"
    :value="value"
    @input="$emit('input', $event)"
    @mouseout="$emit('mouseout')"
    @mouseover="$emit('mouseover')"
  >
    <slot />
  </q-menu>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'MenuComp',
  props: {
    value: {
      type: Boolean,
    },
    anchor: {
      type: String as () =>
        | 'top right'
        | 'top left'
        | 'bottom left'
        | 'top middle'
        | 'top start'
        | 'top end'
        | 'center left'
        | 'center middle'
        | 'center right'
        | 'center start'
        | 'center end'
        | 'bottom middle'
        | 'bottom right'
        | 'bottom start'
        | 'bottom end'
        | undefined,
      default: 'top right',
    },
    self: {
      type: String as () =>
        | 'top right'
        | 'top left'
        | 'bottom left'
        | 'top middle'
        | 'top start'
        | 'top end'
        | 'center left'
        | 'center middle'
        | 'center right'
        | 'center start'
        | 'center end'
        | 'bottom middle'
        | 'bottom right'
        | 'bottom start'
        | 'bottom end'
        | undefined,
      default: 'top left',
    },
  },
  emits: ['input', 'mouseout', 'mouseover'],
});
</script>

<style scoped></style>
