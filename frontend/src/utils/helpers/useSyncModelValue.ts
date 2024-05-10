import { watch } from 'vue';

/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export function useSyncModelValue(
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  props: any,
  propertyName: string,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  emit: any,
  emitName: string,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  editableValue: any,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  modelValueFormatFunction?: (any: any) => any,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  emitValueFormatFunction?: (any: any) => any,
  emitOnBlur?: boolean
): void {
  watch(
    () => editableValue.value,
    (newVal) => emitNewValue(newVal),
    { deep: true }
  );

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
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

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  function setEditableValue(newVal: any) {
    if (modelValueFormatFunction) {
      editableValue.value = modelValueFormatFunction(newVal);
    } else {
      editableValue.value = newVal;
    }
  }
}
/* eslint-enable @typescript-eslint/explicit-module-boundary-types */
