<template>
  <q-dialog
    id="dialog-comp"
    v-model="modalOpen"
    persistent
    :position="position"
  >
    <q-card :class="{ column: true, 'full-height': fullHeight }" :style="style">
      <q-toolbar>
        <!-- TODO make title sticky !-->
        <q-toolbar-title>
          <span class="text-h5 text-weight-bold text-primary">{{
            modalTitle
          }}</span>
        </q-toolbar-title>
      </q-toolbar>
      <q-separator />
      <q-card-section id="contact-card-section" class="col full-height">
        <slot class="text-body1" name="content">
          <span v-if="modalText">
            {{ modalText }}
          </span>
          <slot name="htmlContent" />
        </slot>
        <inner-loading-comp :loading="contentIsLoading" />
      </q-card-section>
      <div id="modal-footer">
        <q-separator />
        <q-card-actions align="right">
          <q-btn
            v-show="showActionButton"
            color="primary"
            :disable="actionButtonDisabled"
            icon="delete"
            :label="actionButtonLabel"
            :loading="actionButtonLoading"
            @click="confirmClicked"
          />
          <slot name="buttons" />
          <q-btn
            v-show="showCancelButton"
            color="white"
            icon="close"
            :label="cancelButtonLabel"
            text-color="primary"
            @click="cancelClicked"
          />
        </q-card-actions>
      </div>
    </q-card>
  </q-dialog>
</template>

<script lang="ts">
import InnerLoadingComp from 'src/components/common/InnerLoadingComp.vue';
import { computed, defineComponent, ref, watch } from 'vue';
//Lang
import i18n from 'src/i18n';
export default defineComponent({
  name: 'DialogComp',
  components: { InnerLoadingComp },
  props: {
    actionButtonLabel: {
      type: String,
      default: i18n.global.t('confirm'),
    },
    actionButtonDisabled: {
      type: Boolean,
      default: false,
    },
    actionButtonIcon: {
      type: String,
      default: i18n.global.t('delete'),
    },
    actionButtonLoading: {
      type: Boolean,
      default: false,
    },
    cancelButtonLabel: {
      type: String,
      default: i18n.global.t('cancel'),
    },
    contentIsLoading: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    modalText: {
      type: String,
      default: '',
    },
    modalTitle: {
      type: String,
      default: 'Confirm',
    },
    width: {
      type: String,
      default: 'unset',
    },
    maxWidth: {
      type: String,
      default: 'unset',
    },
    maxHeight: {
      type: String,
      default: 'unset',
    },
    height: {
      type: String,
      default: 'unset',
    },
    position: {
      type: String as () =>
        | 'left'
        | 'standard'
        | 'top'
        | 'right'
        | 'bottom'
        | undefined,
      default: 'standard',
    },
    showActionButton: {
      type: Boolean,
      default: true,
    },
    showCancelButton: {
      type: Boolean,
      default: true,
    },
    suppressCloseOnActionButtonClick: {
      type: Boolean,
      default: false,
    },
    fullHeight: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:modelValue', 'cancelClicked', 'confirmClicked'],
  setup(props, { emit }) {
    const modalOpen = ref(false);

    watch(
      () => props.modelValue,
      (newVal) => (modalOpen.value = newVal)
    );

    watch(
      () => modalOpen.value,
      (newVal) => emit('update:modelValue', newVal)
    );

    const style = computed(() => {
      return {
        width: props.width,
        'max-width': props.maxWidth,
        height: props.height,
        'max-height': props.maxHeight,
      };
    });

    function closeModal() {
      modalOpen.value = false;
    }

    function confirmClicked() {
      if (!props.suppressCloseOnActionButtonClick) {
        closeModal();
      }
      emit('confirmClicked');
    }

    function cancelClicked() {
      closeModal();
      emit('cancelClicked');
    }

    return {
      modalOpen,
      style,
      confirmClicked,
      cancelClicked,
    };
  },
});
</script>

<style lang="scss">
#dialog-comp {
  #contact-card-section {
    overflow-y: auto;
  }

  #modal-footer {
    position: sticky;
    bottom: 0;
    width: 100%;
    background-color: white;
  }
}
</style>
