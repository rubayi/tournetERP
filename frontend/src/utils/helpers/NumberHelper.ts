function isInNumberRange(
  number: number | null,
  startNum: number | null,
  endNum: number | null
): boolean {
  return !(
    (number || number === 0) &&
    ((startNum !== null && number < startNum) ||
      (endNum !== null && number > endNum))
  );
}

export default {
  isInNumberRange,
};
