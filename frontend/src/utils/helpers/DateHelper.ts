import moment from "moment";

const shortDateRegex = /\b\d{1,2}[/-]\d{1,2}[/-]\d{4}\b/;
const isoSubstringRegex =
  /\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d(\.\d+)?(([+-]\d\d:\d\d)|Z)?/gi;

function formatDateToISOString(date: Date | string | null): string | null {
  let iso = date
    ? moment(date, "MM/DD/YYYY HH:mm:ss.SSS")
        .toISOString(true)
        .substring(0, 23) + "Z"
    : null;
  if (!iso || iso == "Invalid date") {
    iso = null;
  }
  return iso;
}

function formatDateToShortDateString(dateValue: Date): string {
  return dateValue && isValidDateString(dateValue.toString())
    ? formatISOStringToShortDateString(formatDateToISOString(dateValue))
    : "";
}

function formatISOStringToShortDateString(isoDate: string | null): string {
  let shortString = moment.utc(isoDate).format("MM/DD/YYYY");
  if (shortString == "Invalid date") {
    shortString = "";
  }
  return shortString;
}

function formatISOStringToDateTimeString(isoDate: string | null): string {
  let shortString = moment.utc(isoDate).format("MM/DD/YYYY hh:mm:ss A");
  if (shortString == "Invalid date") {
    shortString = "";
  }
  return shortString;
}

function formatISOStringToYearString(isoDate: string): string {
  let shortString = "";
  if (isoDate) {
    const date = new Date(isoDate);
    shortString = new Date(date).toLocaleDateString("en-US", {
      year: "numeric",
    });
  }
  return shortString;
}

function formatShortDateToISOString(
  shortDateString: string | null
): string | null {
  let isoString = null;

  if (shortDateString) {
    if (isValidShortDateString(shortDateString)) {
      isoString = formatDateToISOString(shortDateString);
    } else {
      isoString = "Invalid Date";
    }
  }

  return isoString;
}

function formatToMonthDayYearShortDateString(
  shortDateString: string,
  month: number | null,
  day: number | null,
  year: number | null
): string {
  let str = shortDateString;
  const date = new Date(shortDateString);

  if (isValidShortDateString(shortDateString) && date) {
    if (month) {
      date.setMonth(month);
    }
    if (day) {
      date.setDate(day);
    }
    if (year) {
      date.setFullYear(year);
    }
    str = formatDateToShortDateString(date);
  }
  return str;
}

function jsDateISOStringComparator(jsDate: Date, isoString: string): number {
  const cellValueAsDate = new Date(isoString);
  if (!cellValueAsDate) {
    return 0;
  }
  if (cellValueAsDate < jsDate) {
    return -1;
  } else if (cellValueAsDate > jsDate) {
    return 1;
  }
  return 0;
}

function shortDateComparator(jsDate: Date, isoString: string): number {
  return jsDateISOStringComparator(
    jsDate,
    formatISOStringToShortDateString(isoString)
  );
}

function isValidDateString(dateString: string): boolean {
  return dateString === null || dateString === "" || !!Date.parse(dateString);
}

function isValidShortDateString(shortDateString: string): boolean {
  return (
    isValidDateString(shortDateString) &&
    (shortDateString === null ||
      shortDateString === "" ||
      shortDateRegex.test(shortDateString))
  );
}

function isMonthDayYearShortDateString(
  shortDateString: string,
  month: number | null,
  day: number | null,
  year: number | null
): boolean {
  let result = true;
  const date = new Date(shortDateString);

  if (!isValidShortDateString(shortDateString) || !date) {
    result = false;
  } else {
    if (month) {
      result = date.getMonth() === month;
    } else if (result && day) {
      result = date.getDate() === day;
    } else if (result && year) {
      result = date.getFullYear() === year;
    }
  }
  return result;
}

function getStartOfTodayIsoString(): string | null {
  const today = new Date();
  today.setHours(0);
  today.setMinutes(0);
  today.setSeconds(0);
  today.setMilliseconds(0);
  return formatDateToISOString(today);
}

function getTodayShortDateString(): string {
  return formatDateToShortDateString(new Date());
}

function shortDateStringComparator(a: string, b: string): number {
  if (a === b) {
    return 0;
  }
  const aIsDate = isValidShortDateString(a);
  const bIsDate = isValidShortDateString(b);
  if (!aIsDate && !bIsDate) {
    return a > b ? 1 : -1;
  } else if (!bIsDate) {
    return 1;
  } else if (!aIsDate) {
    return -1;
  } else {
    const aDate = moment(a, "MM/DD/YYYY").valueOf();
    const bDate = moment(b, "MM/DD/YYYY").valueOf();
    return aDate > bDate ? 1 : -1;
  }
}

function stripTimeFromIsoString(isoString: string): string {
  return isoString.split("T", 1)[0] + "T00:00:00.000Z";
}

function isoStringHasNoTime(isoString: string | null): boolean {
  if (isoString) {
    return isoString.includes("T00:00:00.000Z");
  }
  return false;
}

function addDaysToDateString(
  dateStr: string | null,
  daysToAdd: number
): string | null {
  let computedDate = "";
  if (dateStr && isValidDateString(dateStr)) {
    const date = moment(dateStr, "YYYY-MM-DD");
    date.add(daysToAdd, "days");
    computedDate = date.format("MM/DD/YYYY");
  }
  return computedDate;
}

export default {
  jsDateISOStringComparator,
  formatDateToISOString,
  formatDateToShortDateString,
  formatShortDateToISOString,
  formatISOStringToShortDateString,
  formatISOStringToDateTimeString,
  formatISOStringToYearString,
  formatToMonthDayYearShortDateString,
  shortDateComparator,
  shortDateStringComparator,
  isValidDateString,
  isValidShortDateString,
  isMonthDayYearShortDateString,
  getStartOfTodayIsoString,
  getTodayShortDateString,
  stripTimeFromIsoString,
  isoSubstringRegex,
  isoStringHasNoTime,
  addDaysToDateString,
};
