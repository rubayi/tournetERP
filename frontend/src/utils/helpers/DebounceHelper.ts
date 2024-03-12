export default {
  debounce(
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
