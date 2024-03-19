import store from "../store";

export function getCommonValue(comReq) {
  const req = {
    uprCodeUuid: comReq.upCode,
    codeLvl: comReq.codeLvl
  };


  return store.dispatch("comCode/useComCode", req)
    .then(
      (commCode) => {
          return commCode;
      },
      (error) => {
        const errorMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        throw new Error(errorMessage);
      }
    );
}
