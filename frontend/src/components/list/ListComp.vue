<template>
  <q-list>
    <slot />
  </q-list>
</template>

<script>
export default {
  name: "ListComp",
};
</script>

<style scoped>
.q-list {
  padding: 0;
}
</style>
