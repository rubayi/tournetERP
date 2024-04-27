function downloadPDF(rawData: ArrayBuffer, fileName: string): void {
  downloadFile(rawData, fileName, "pdf", "application/pdf");
}

function downloadFileWithExtension(
  rawData: ArrayBuffer,
  fileName: string,
  extension: string
): void {
  downloadFile(rawData, fileName, extension, "application/octet-stream");
}

function downloadFile(
  rawData: ArrayBuffer,
  fileName: string,
  fileExt: string,
  contentType: string
): void {
  const fileNameExt = `${fileName}.${fileExt}`;
  const blob = new Blob([rawData], { type: contentType });
  const data = window.URL.createObjectURL(blob);
  // Debug (Keep for development)
  // window.open(data, "_blank");

  // App Export
  const link = document.createElement("a");
  link.href = data;
  link.download = fileNameExt;
  link.click();
  setTimeout(() => {
    window.URL.revokeObjectURL(data);
  }, 100);
}

export default {
  downloadPDF,
  downloadFileWithExtension,
  downloadFile,
};
