<template>
      <q-card-section>
        <div class="row q-col-gutter-sm">
          <div class="col-3">
            <q-form>
              <q-card-section class="scroll">
                <div class="row q-col-gutter-sm">
                  <pre>{{ dataVal }}</pre>

                </div>
              </q-card-section>
            </q-form>

          </div>
        </div>
      </q-card-section>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
const dataVal = ref([]);

export default defineComponent({
  name: "EmpFormDrawerContent",
  components: {

  },
  props: {
    dataVal:Array,
  },
  setup(props, { emit }) {

  },
  mounted() {
    console.log('Received dataVal:', dataVal);
  }
});
</script>

<style lang="scss"></style>
