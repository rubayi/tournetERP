export interface ISelectOption {
  label: string;
  value: any;
}

export class SelectOption implements ISelectOption {
  public label: string;
  public value: any;

  constructor(label: string | null, value: unknown) {
    this.label = label ? label : "";
    this.value = value;
  }
}
