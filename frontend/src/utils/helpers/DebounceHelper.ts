export default {
  debounce(
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    func: { (): void; call?: any },
    delay: number | undefined,
    vm: void
  ): void {
    setTimeout(
      () => {
        if (func) {
          func.call();
        }
      },
      delay,
      vm
    );
  },
};
