<template>
  <div :class="{ 'card-comp-design': true, fit: fit }">
    <q-card :bordered="bordered" class="fit" flat>
      <div class="q-toolbar row no-wrap items-center doc-example__toolbar">
        <section v-if="title" :class="headerSectionClass">
          <div :class="headersubSectionClass">{{ title }}</div>
        </section>
        <div class="q-space"></div>
      </div>
      <slot name="content" />
    </q-card>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'CardCompDesign',
  props: {
    title: {
      type: String,
      default: '',
    },
    headerSectionClass: {
      type: String,
      default: 'q-my-xs q-mr-sm text-subtitle1',
    },
    headersubSectionClass: {
      type: String,
      default: 'doc-card-title text-h7 text-primary',
    },
    fit: {
      type: Boolean,
      default: true,
    },
    bordered: {
      type: Boolean,
      default: true,
    },
  },
});
</script>

<style lang="scss">
.card-comp-design {
  .q-card__section {
    overflow: auto;
  }
}

.doc-card-title {
  background: #e0e0e0;
  border-radius: 3px 5px 5px 0;
  color: #616161;
  margin-left: -24px;
  padding: 2px 10px 2px 24px;
  position: relative;
}
.doc-card-title:after {
  border: 0 solid #0000;
  border-top-color: #bebebe;
  border-width: 9px 0 0 11px;
  content: '';
  height: 0;
  left: 0;
  position: absolute;
  top: 100%;
  width: 0;
}
.text-subtitle1 {
  font-size: 1rem;
  font-weight: 400;
  letter-spacing: 0.00937em;
  line-height: 1.75rem;
}
.doc-example__toolbar {
  color: #616161;
}
.q-toolbar {
  min-height: 50px;
  padding: 0 12px;
  position: relative;
  width: 100%;
}
.no-wrap {
  flex-wrap: nowrap;
}
.items-center {
  align-items: center;
}

.main-title {
  background-image: url('src/assets/top_main.png');
  font-family: Raleway;
  font-weight: 500;
  color: #f0eeee;
  padding: 0.5rem;
}
</style>
