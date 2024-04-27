// TODO As we come across potential usages of these functions we should add them
function getStringMemberValue(
  valueToAssign: string | null | undefined
): string {
  return valueToAssign || "";
}

function getDateMemberValue(
  valueToAssign: string | null | undefined
): string | null {
  return valueToAssign || null;
}

function getSelectMemberValue(
  valueToAssign: string | null | undefined
): string | null {
  return valueToAssign || null;
}

function getBooleanMemberValue(
  valueToAssign: boolean | null | undefined
): boolean {
  return valueToAssign || false;
}

function getNumberMemberValue(
  valueToAssign: number | 0 | undefined
): number | 0 {
  return valueToAssign || valueToAssign === 0 ? valueToAssign : 0;
}

export {
  getStringMemberValue,
  getDateMemberValue,
  getSelectMemberValue,
  getBooleanMemberValue,
  getNumberMemberValue,
};
