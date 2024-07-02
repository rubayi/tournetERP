<template>
  <q-img
    v-if="params.value"
    :src="imageSrc"
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
import { fileInfo } from 'src/utils/helpers/Fileinfo';

export default {
  name: 'ImageCellRenderer',
  props: ['params'],
  computed: {
    imageSrc() {
      // Concatenate fileInfo with params.value to form the full image source URL
      return fileInfo + this.params.value;
    },
  },
};
</script>
