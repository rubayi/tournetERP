import { watch } from "vue";

/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export function useSyncModelValue(
  props: any,
  propertyName: string,
  emit: any,
  emitName: string,
  editableValue: any,
  modelValueFormatFunction?: (any: any) => any,
  emitValueFormatFunction?: (any: any) => any,
  emitOnBlur?: boolean
): void {
  watch(
    () => editableValue.value,
    (newVal) => emitNewValue(newVal),
    { deep: true }
  );

  function emitNewValue(newVal: any) {
    if (!emitOnBlur) {
      if (emitValueFormatFunction) {
        emit(emitName, emitValueFormatFunction(newVal));
      } else {
        emit(emitName, newVal);
      }
    }
  }

  watch(
    () => props[propertyName],
    (newVal) => {
      setEditableValue(newVal);
    },
    { deep: true, immediate: true }
  );

  function setEditableValue(newVal: any) {
    if (modelValueFormatFunction) {
      editableValue.value = modelValueFormatFunction(newVal);
    } else {
      editableValue.value = newVal;
    }
  }
}
/* eslint-enable @typescript-eslint/explicit-module-boundary-types */
