<template>
  <div v-if="lcContactList" id="comp-form-drawer-contact">

    <div class="flex flex-grow-1">
      <q-scroll-area
        style="height: 600px; min-width: 700px;"
      >
        <div class="q-pa-md example-row-equal-width">

          <div v-for="(contact, index) in lcContactList" :key="index" class="row">
            <div class="col">
              {{ contact.contactTypeName }}
            </div>
            <div class="col">
              {{ contact.contactCont }}
            </div>
            <div class="col">
              {{ contact.repYn }}
            </div>
          </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import {defineComponent, ref, watch} from "vue";

export default defineComponent({
  name: "CompFormDrawerContact",
  components: {},
  props: {
    dataVal: Array,
  },
  setup(props, { emit }) {

    const lcContactList = ref(props.dataVal);
    return {
      lcContactList,
    };

  },


});
</script>

<style lang="scss">
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* Three columns with equal width */
  gap: 10px; /* Gap between items */
}

/* CSS to ensure items fill the available width within the grid */
.item {
  width: 100%;
}
</style>
