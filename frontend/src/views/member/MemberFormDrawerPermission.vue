<template>
  <card-comp-design class="q-mt-sm" title="Permission">
    <template #content>
      <q-card-section>
        <div class="row q-col-gutter-sm">
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Work Order
          </div>
          <div class="col-4">
            :
            <checkbox-comp
              v-model="editmemberpermissionData.logauthR"
              label="Read"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.logauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.logauthE"
              label="Edit"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.logauthD"
              label="Delete"
            />
          </div>
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Memo Log
          </div>
          <div class="col-4">
            :

            <checkbox-comp
              v-model="editmemberpermissionData.memoauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.memoauthE"
              label="Edit"
            />
          </div>
        </div>

        <q-separator />
        <div class="row q-col-gutter-sm">
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Users
          </div>
          <div class="col-4">
            :
            <checkbox-comp
              v-model="editmemberpermissionData.userauthR"
              label="Read"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.userauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.userauthE"
              label="Edit"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.userauthD"
              label="Delete"
            />
          </div>
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Office
          </div>
          <div class="col-4">
            :
            <checkbox-comp
              v-model="editmemberpermissionData.officeauthR"
              label="Read"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.officeauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.officeauthE"
              label="Edit"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.officeauthD"
              label="Delete"
            />
          </div>
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Report
          </div>
          <div class="col-4">
            :
            <checkbox-comp
              v-model="editmemberpermissionData.reportauthR"
              label="Read"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.reportauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.reportauthE"
              label="Edit"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.reportauthD"
              label="Delete"
            />
          </div>
          <div class="col-2 q-pt-md">
            <i class="fas fa-bars q-mr-xs"></i> Code
          </div>
          <div class="col-4">
            :
            <checkbox-comp
              v-model="editmemberpermissionData.codeauthR"
              label="Read"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.codeauthA"
              label="Add"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.codeauthE"
              label="Edit"
            />
            <checkbox-comp
              v-model="editmemberpermissionData.codeauthD"
              label="Delete"
            />
          </div>
        </div>
      </q-card-section>
    </template>
  </card-comp-design>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

//Component
import CardCompDesign from "@/components/common/CardCompDesign.vue";
import CheckboxComp from "@/components/common/CheckboxComp.vue";
//Type
import { MemberPermissionForm } from "@/types/MemberPermissionForm";
//Helpers
import { useSyncModelValue } from "@/utils/helpers/useSyncModelValue";

export default defineComponent({
  name: "MemberFormDrawerContent",
  components: {
    CardCompDesign,
    CheckboxComp,
  },
  props: {
    modelValue: {
      type: Object as () => MemberPermissionForm,
      default: () => new MemberPermissionForm(),
    },
  },
  setup(props, { emit }) {
    const editmemberpermissionData = ref<MemberPermissionForm>();
    useSyncModelValue(
      props,
      "modelValue",
      emit,
      "update:modelValue",
      editmemberpermissionData
    );

    return {
      editmemberpermissionData,
    };
  },
});
</script>

<style lang="scss"></style>
