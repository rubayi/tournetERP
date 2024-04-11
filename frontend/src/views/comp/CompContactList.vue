<template>
  <div v-if="lcContactList" id="comp-form-drawer-contact">

    <div class="flex flex-grow-1">
      <q-scroll-area
        style="height: 600px; min-width: 700px;"
      >
        <div class="q-pa-md example-row-equal-width">

          <div v-for="(contact, index) in lcContactList" :key="index" class="row">
            <div class="col-1">
              <q-btn @click="deleteCont(contact.contactUuid)" flat round
              ><q-icon name="delete_forever" class="q-pb-lg"/>
              </q-btn>
            </div>
            <div class="col-2">
              {{ contact.contactTypeName }}
            </div>
            <div class="col-7">
              {{ contact.contactCont }}
            </div>
            <div class="col-2">
              {{ contact.repYn !=  null ? "대표":""}}
            </div>

          </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import {defineComponent, onMounted, ref, watch} from "vue";
import {contactTn} from "src/store/contact.module";
import ContactService from "src/services/contact.service";

export default defineComponent({
  name: "CompFormDrawerContact",
  components: {},
  props: {
    dataVal: Array,
    subDrawerClose: Function
  },
  setup(props, { emit }) {

    const lcContactList = ref(props.dataVal);

    watch(() => props.dataVal, (newVal) => {
      lcContactList.value = newVal;
    }, { deep: true });

    function emitCloseDrawer() {
        emit("drawer-closed");
    }

    function deleteCont(contactUuid) {
      const confirmation = window.confirm("연락처를 삭제 하시겠습니까?");
      if (confirmation) {
        ContactService.deleteContact(contactUuid).then(
            (response) => {
              alert(response.data.message);
              emitCloseDrawer();
            },
            (error) => {
              console.log("saveComp failed", error);
            }
        )
      }
    }

    return {
      lcContactList,
      deleteCont
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
