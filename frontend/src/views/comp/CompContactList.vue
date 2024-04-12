<template>
  <div v-if="lcContactList" id="comp-form-drawer-contact">

    <div class="flex flex-grow-1">
      <q-scroll-area
        style="height: 600px; min-width: 700px;"
      >
        <div class="q-pa-md">
          <div class="example-row-column-width" >
            <div class="row">
              <div class="col-1 center ">
                관리
              </div>
              <div class="col-2 center">
                연락처구분
              </div>
              <div class="col-7 center">
                연락처
              </div>
              <div class="col-2 center">
                대표여부
              </div>
            </div>
          </div>
          <div v-for="(contact, index) in lcContactList" :key="index" class="example-row-column-width" >
            <div class="row">
              <div class="col-1 center">
                <q-btn @click="deleteCont(contact.contactUuid)" flat round
                ><q-icon name="delete_forever"/>
                </q-btn>
              </div>
              <div class="col-2 center">
                {{ contact.contactTypeName }}
              </div>
              <div class="col-7">
                {{ contact.contactCont }}
              </div>
              <div class="col-2 center">
                {{ contact.repYnName}}
              </div>
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
<style lang="sass">
.example-row-column-width
  .center
    text-align: center
  .row > div
    padding: 5px 5px 5px 8px
    background: rgba(#999,.15)
    border: 1px solid rgba(#999,.2)
  .row + .row
    margin-top: 1rem
</style>
