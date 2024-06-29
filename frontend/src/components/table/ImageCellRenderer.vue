<template>
  <q-img
    v-if="params.value"
    :src="params.value"
    alt="Preview Image"
    style="
      width: 50px;
      height: 50px;
      border: 1px solid #b4b4b4;
      border-radius: 4px;
    "
  />
</template>

<script>
export default {
  name: 'ImageCellRenderer',
  props: ['params'],
};
</script>
