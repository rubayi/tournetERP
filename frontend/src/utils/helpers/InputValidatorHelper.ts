import { ISelectOption } from 'src/types/SelectOption';

function requiredValidator(val: string): boolean {
  return !!val && val.length > 0;
}

function requiredSelectValidator(val: ISelectOption | null): boolean {
  return !!val && !!val.value;
}

// eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
function requiredInputSelectValidator(val: unknown): boolean {
  return !!val;
}

function emailValidator(input: string): boolean {
  return !input || input.includes('@');
}

function multiEmailValidator(val: string[]): boolean {
  let valid = true;
  if (val) {
    val.forEach((v) => {
      if (!emailValidator(v)) {
        valid = false;
      }
    });
  }
  return valid;
}

export {
  emailValidator,
  multiEmailValidator,
  requiredValidator,
  requiredSelectValidator,
  requiredInputSelectValidator,
};
