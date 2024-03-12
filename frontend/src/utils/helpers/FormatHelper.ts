function properCase(str: string): string {
  let properCaseString = "";
  if (str && str.length > 0) {
    properCaseString = str.charAt(0).toUpperCase();
    if (str.length > 1) {
      properCaseString += str.substring(1, str.length).toLowerCase();
    }
  }
  return properCaseString;
}

function upperCase(input: string | null): string | null {
  return input ? input.toUpperCase() : null;
}

function convertToNumber(str: string): number | null {
  const numberStr: string = str ? str.replace(/[^\d.-]/g, "") : "";
  const number = parseFloat(numberStr);
  return number || number === 0 ? number : null;
}

function convertToScale(str: string, scale: number, strict: boolean): string {
  const inputParts: string[] = str && str.includes(".") ? str.split(".") : [];
  let inputScale: number = inputParts.length > 1 ? inputParts[1].length : 0;
  inputScale = inputScale > scale || strict ? scale : inputScale;

  const number: number | null = convertToNumber(str);

  const formatter = new Intl.NumberFormat("en-US", {
    minimumFractionDigits: inputScale,
    maximumFractionDigits: inputScale,
    useGrouping: false,
  });
  return number || number === 0 ? formatter.format(number) : "";
}

function convertToString(input: number | null): string {
  return input || input === 0 ? input.toString() : "";
}

function convertStringToCurrency(str: string): string {
  const number: number | null = convertToNumber(str);
  if (number || number === 0) {
    const formatter = new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 2,
    });
    return formatter.format(number);
  }
  return "";
}

function convertNumberToCurrency(input: number | null): string {
  const str: string = convertToString(input);
  return convertStringToCurrency(str);
}

function formatToUpperCaseAndTrim(label: string): string {
  // The toString allows for boolean values
  return label ? label.toString().toUpperCase().trim() : label;
}

function formatPhoneNumber(input: string): string {
  input = input.replace(/[^\d]/g, "");
  const numDigits = input.length;
  if (numDigits < 10) {
    input = input.replace(/(\d{3})(\d{4})/, "$1-$2");
    input = input.slice(0, 8);
  } else if (numDigits == 10) {
    input = input.replace(/(\d{3})(\d{3})(\d{4})/, "($1)$2-$3");
  } else {
    let lastTen = input.substring(input.length - 10, input.length);
    lastTen = lastTen.replace(/(\d{3})(\d{3})(\d{4})/, "($1)$2-$3");
    const first = input.substring(0, input.length - 10);
    input = `${first}${lastTen}`;
  }
  return input;
}

function capitalizeFirstLetter(input: string): string {
  return input.charAt(0).toUpperCase() + input.slice(1);
}

function buildConcatenatedString(
  valueList: string[],
  maxLength: number
): string | null {
  let result = null;
  if (valueList && valueList.length > 0) {
    result = valueList.join(", ");
    if (result.length > maxLength) {
      result = result.substring(0, maxLength - 3) + "...";
    }
  }
  return result;
}

function trimStringAndAddElipsis(
  value: string | null,
  maxLength: number
): string | null {
  return value && value.length > maxLength
    ? value.substring(0, maxLength - 3) + "..."
    : value;
}

export default {
  properCase,
  upperCase,
  convertToNumber,
  convertToScale,
  convertToString,
  convertStringToCurrency,
  convertNumberToCurrency,
  formatToUpperCaseAndTrim,
  formatPhoneNumber,
  capitalizeFirstLetter,
  buildConcatenatedString,
  trimStringAndAddElipsis,
};
