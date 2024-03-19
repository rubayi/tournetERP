<template>
  <div id="codeform-drawer">
    <drawer-comp
      :open-drawer="openDrawer"
      :drawerWidth="drawerWidth"
      :on-close-click="onCloseClick"
      :on-save-click="onSaveClick"
      :on-delete-click="onDeleteClick"
    >
      <div class="flex flex-grow-1 q-pa-md">
        <q-card-section>
          <q-form v-if="edited">
            <q-card-section>
              <div class="row q-col-gutter-lg">
                <input
                  type="number"
                  id="codeUuid"
                  v-model="edited.codeUuid"
                  hidden
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="codeEn"
                  v-model="edited.codeEn"
                  label="영문 상세코드명"
                  stack-label
                  lazy-rules
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="codeKr"
                  v-model="edited.codeKr"
                  label="한글 상세코드명"
                  stack-label
                  lazy-rules
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="codeValue"
                  v-model="edited.codeValue"
                  label="코드값"
                  stack-label
                  lazy-rules
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="codeLvl"
                  v-model="edited.codeLvl"
                  label="코드레벨 ex) 0,1,2.."
                  stack-label
                  lazy-rules
                />
                <q-input
                  class="col-6"
                  type="number"
                  id="codeOrd"
                  v-model="edited.codeOrd"
                  label="코드정렬순서"
                  stack-label
                  lazy-rules
                />
                <q-input
                  class="col-6"
                  type="text"
                  id="useYn"
                  v-model="edited.useYn"
                  label="코드사용여부 (Y or N)"
                  stack-label
                  lazy-rules
                />
              </div>
            </q-card-section>
          </q-form>
        </q-card-section>
      </div>
    </drawer-comp>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from "vue";
import DrawerComp from "src/components/drawers/DrawerComp.vue";

export default defineComponent({
  name: "CodeFormDrawer",
  components: {
    DrawerComp,
  },
  props: {
    openDrawer: Boolean,
    drawerWidth: Number,
    dataVal: Object,
    onCloseClick: Function,
    onSaveClick: Function,
    onDeleteClick: Function,
  },
  setup(props, { emit }) {
    const edited = ref(props.dataVal);

    watch(
      () => props.dataVal,
      (newVal) => {
        edited.value = { ...newVal }; // Update the edited ref with the new value
      }
    );

    return {
      edited,
    };
  },
  mounted() {
    console.log("Received dataVal:", this.dataVal);
    console.log("Received openDrawer:", this.openDrawer);
  },
});
</script>

<style lang="scss">
#codeform-drawer {
  .q-page {
    display: flex;
    flex-direction: column;
  }
}
</style>
