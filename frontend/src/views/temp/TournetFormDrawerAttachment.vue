<template>
  <card-comp-design
    id="tournetdocumentform"
    bordered="false"
    title="Attachment"
  >
    <template #content>
      <div id="tournet-document-grid-container" class="row grow-1">
        <div class="col-12">
          <q-markup-table id="copiesactionTable" bordered flat separator="cell">
            <thead class="bg-blue-grey-1">
              <tr>
                <th class="text-center no-padding" style="width: 120px">
                  Types
                </th>
                <th class="text-center no-padding">Description</th>
                <th class="text-center no-padding" style="width: 20px"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in attachmentlist" :key="item.fildid">
                <td class="text-center no-padding">
                  {{ item.attachtypename.codename }}
                </td>
                <td class="text-left no-padding">
                  <span
                    v-if="item.origfilename.length > 33"
                    class="q-ml-xs"
                    style="cursor: pointer"
                    @click="downloadAction(item.fildid)"
                  >
                    <img
                      align="absmiddle"
                      :src="require(`src/assets/img/${item.filenameext}`)"
                      style="margin-right: 0.4em"
                    />
                    {{ item.origfilename.substring(0, 32) + ".."
                    }}{{
                      item.origfilename.substring(
                        item.origfilename.length - 4,
                        item.origfilename.length
                      )
                    }}
                  </span>
                  <span
                    v-if="item.origfilename.length <= 33"
                    class="q-ml-xs"
                    style="cursor: pointer"
                    @click="downloadAction(item.fildid)"
                  >
                    <img
                      align="absmiddle"
                      :src="require(`src/assets/img/${item.filenameext}`)"
                      style="margin-right: 0.4em"
                    />
                    {{ item.origfilename }}
                  </span>
                </td>
                <td class="text-left q-pl-sm q-pr-sm no-padding">
                  <q-btn
                    v-if="!readonlybtn"
                    color="negative"
                    flat
                    icon="delete"
                    round
                    @click="deleteAction(item.fildid)"
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>
      </div>
      <div v-if="!readonlybtn" class="row q-pr-none q-mt-xs">
        <div class="col-7 q-pl-md">
          <select-comp
            v-model="subattachtype"
            class="full-width"
            label="-- Select Type --"
            :options="attachmenttypelistgroup"
            outlined
          />
        </div>
        <div class="col-5">
          <q-uploader
            auto-expand
            auto-upload
            borderless
            class="float-right"
            color="primary"
            dense
            :factory="addAction"
            flat
            hide-upload-btn="true"
            hide-upload-progress="true"
            label="Upload File"
            max-files="1"
            :multiple="false"
            no-thumbnails
            style="width: 90%"
            text-color="white"
            ref="attachmentuploader"
          />
        </div>
      </div>
    </template>
  </card-comp-design>
  <dialog-comp
    v-model="openDeleteConfirm"
    action-button-label="Delete"
    max-width="500px"
    modal-text="Are you sure you want to delete this Attachment File?"
    modal-title="Delete Attachment FIle"
    @confirm-clicked="deleteTournetdocumentForm"
  />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
// Component
import CardCompDesign from "src/components/common/CardCompDesign.vue";
import DialogComp from "src/components/common/DialogComp.vue";
import SelectComp from "src/components/common/SelectComp.vue";

// Type
import { TournetAttachmentForm } from "src/types/TournetAttachmentForm";
import { SelectOption } from "src/types/SelectOption";

//helper
import { notificationHelper } from "src/utils/helpers/NotificationHelper";

// Service
import { AttachmentService } from "src/services/AttachmentService";
// Store
import store from "src/store";

export default defineComponent({
  name: "TournetFormDrawerResponse",
  components: {
    DialogComp,
    SelectComp,
    CardCompDesign,
  },
  props: {
    subworknum: {
      type: Number,
      default: 0,
    },
    attachmentlist: {
      type: Array as () => TournetAttachmentForm[],
      default: () => [],
    },
    attachmenttypelistgroup: {
      type: Array as () => SelectOption[],
      default: () => [],
    },
  },
  emits: ["subAttachment-changed"],
  setup(props, { emit }) {
    const loading = ref<boolean>(false);
    const editfileid = ref<number>(0);
    const subattachtype = ref<number>(0);
    const openDeleteConfirm = ref<boolean>(false);

    const attachmentuploader = ref();

    const selectedEntry = ref<TournetAttachmentForm>();
    const readonlybtn = ref<boolean>(true);
    resetreadData();
    function resetreadData() {
      if (
        store.getters.currentUserHasApplicationPermission("LOG_A") ||
        store.getters.currentUserHasApplicationPermission("LOG_E")
      ) {
        readonlybtn.value = false;
      } else {
        readonlybtn.value = true;
      }
    }
    // Loading Download Data/////////////////////////////////////////////////////////////////////////////////
    function downloadAction(selectfile: File) {
      if (selectfile) {
        window.location.href =
          "/tournet/attachmentdownload/" +
          selectfile;
      }
    }

    function deleteAction(index: number) {
      editfileid.value = index;
      openDeleteConfirm.value = true;
    }
    function deleteTournetdocumentForm() {
      loading.value = true;
      AttachmentService.deleteTournetDocumentForm(editfileid.value)
        .then(() => {
          notificationHelper.createSuccessNotification(
            `File No : ${editfileid.value} deleted`
          );
          emit("subAttachment-changed");
        })
        .catch((error) => {
          notificationHelper.createErrorNotification(
            notificationHelper.formatResponseToErrorMessage(error.response)
          );
        })
        .finally(() => {
          notificationHelper.dismissOngoingNotification();
          loading.value = false;
        });
    }

    function addAction(file: File) {
      const formData = new FormData();
      formData.append("uploadBtn", file);
      notificationHelper.dismiss();
      notificationHelper.createOngoingNotification("Uploading...");
      loading.value = true;

      if (formData) {
        AttachmentService.saveTournetDocumentForm(
          props.subworknum,
          subattachtype.value,
          formData
        )
          .then(() => {
            notificationHelper.createSuccessNotification(`Upload Completed.`);
            emit("subAttachment-changed");
            attachmentuploader.value.reset();
          })
          .catch((error) => {
            notificationHelper.createErrorNotification(
              notificationHelper.formatResponseToErrorMessage(error.response)
            );
          })
          .finally(() => {
            notificationHelper.dismissOngoingNotification();
            loading.value = false;
          });
      }
    }

    return {
      loading,
      editfileid,
      openDeleteConfirm,
      selectedEntry,
      downloadAction,
      deleteAction,
      deleteTournetdocumentForm,
      attachmentuploader,
      addAction,
      subattachtype,
      readonlybtn,
    };
  },
});
</script>

<style lang="scss">
#tournetdocument-grid-container {
  flex-grow: 1;
  .grid {
    height: 180px;
  }
  border: 0;
}
.ag-theme-alpine .ag-root-wrapper {
  border: 0px;
}
</style>
