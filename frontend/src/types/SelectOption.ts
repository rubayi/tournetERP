export interface ISelectOption {
  label: string;
  value: number;
}

export class SelectOption implements ISelectOption {
  public label: string;
  public value: number;

  constructor(label: string | null, value: unknown) {
    this.label = label ? label : '';
    this.value = typeof value === 'number' ? value : 0;
  }
}
