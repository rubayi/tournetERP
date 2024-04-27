import {ref, Ref} from "vue";
import {InputRef} from "src/types/InputRef"

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function useValidateInputs(inputRefs: Array<any>): any {
  const inputHasError = ref(false);

  function validate() {
    inputRefs.forEach((input) => {
      if (input && input.value && input.value.validate !== undefined) {
        input.value.validate();
      } else {
        console.warn(
          "One of the input refs passed could not be validated",
          inputRefs
        );
      }
    });
  }
  function hasError(): boolean {
    const inputRefsWithError = inputRefs.filter((input: Ref<InputRef>) => {
      return input.value && input.value.hasError ? input.value.hasError : false;
    });
    const someInputHasError = inputRefsWithError.length > 0;
    inputHasError.value = someInputHasError;
    return someInputHasError;
  }


  // function hasError(): boolean {
  //   const inputRefsWithError = inputRefs.filter((input: any) => {
  //     return input && input.value ? input.value.hasError() : false;
  //   });
  //
  // }

  function validateAndRunFunctionIfNoError(
    successFunc: CallableFunction,
    errorFunc: CallableFunction | null
  ) {
    validate();
    setTimeout(() => {
      if (!hasError()) {
        successFunc();
      } else if (errorFunc) {
        errorFunc();
      }
    }, 0);
  }

  function validateAndUpdateInputHasError() {
    validate();
    setTimeout(() => (inputHasError.value = hasError()), 0);
  }

  function resetValidation() {
    setTimeout(() => {
      inputRefs.forEach((input) => {
        // TODO remove the check for input.value.resetValidation when inputRefs always have a resetValidation
        // Uncomment the else statement to find these inputs
        if (input && input.value && input.value.resetValidation) {
          input.value.resetValidation();
        } else {
          // console.warn(
          //   "Validation for Input:",
          //   input.value,
          //   " in inputRefs",
          //   inputRefs
          // );
        }
      });
    }, 0);
  }

  return {
    inputHasError,
    validate,
    hasError,
    validateAndRunFunctionIfNoError,
    validateAndUpdateInputHasError,
    resetValidation,
  };
}
/* eslint-enable @typescript-eslint/explicit-module-boundary-types */
