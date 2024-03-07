<template>
  <div>
    <div>{{ node.label }}</div>
    <button v-if="node.menuDelete">Delete</button>
    <button v-if="node.menuRead">Read</button>
    <button v-if="node.menuWrite">Write</button>
    <div v-if="node.children">
      <tree-node
        v-for="(child, index) in node.children"
        :key="index"
        :node="child"
      ></tree-node>
    </div>
  </div>
</template>

<script>
export default {
  props: ["node"],
  components: {
    "tree-node": () => import("./TreeNode.vue"),
  },
};
</script>
