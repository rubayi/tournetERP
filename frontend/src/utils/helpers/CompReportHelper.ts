import { ReportService, ReportURL } from 'src/services/CompReportService';
import { notificationHelper } from 'src/utils/helpers/NotificationHelper';
import FileHelper from 'src/utils/helpers/FileHelper';
import { IReportVO } from 'src/types/CompReportVO';

function exportEXCELData(
  exportFilename: string,
  reportVO: IReportVO,
  reportService: (reportVO: IReportVO) => Promise<ArrayBuffer>
): void {
  notificationHelper.dismiss();

  if (reportService) {
    notificationHelper.createOngoingNotification('Exporting...');

    const filename = exportFilename ? exportFilename : 'Report';
    reportService(reportVO)
      .then((result) => {
        setTimeout(() => {
          FileHelper.downloadFile(result, filename, 'xls', 'application/CSV');
          notificationHelper.createSuccessNotification('Export Successful!');
        }, 500);
      })
      .catch((error) => {
        notificationHelper.createErrorNotification(
          notificationHelper.formatResponseToErrorMessage(error.response)
        );
      })
      .finally(() => {
        notificationHelper.dismissOngoingNotification();
      });
  }
}

function exportPDFData(
  exportFilename: string,
  reportVO: IReportVO,
  reportService: (reportVO: IReportVO) => Promise<ArrayBuffer>
): void {
  notificationHelper.dismiss();

  if (reportService) {
    notificationHelper.createOngoingNotification('Exporting...');

    const filename = exportFilename ? exportFilename : 'Report';
    reportService(reportVO)
      .then((result) => {
        setTimeout(() => {
          FileHelper.downloadFile(result, filename, 'pdf', 'application/pdf');
          notificationHelper.createSuccessNotification('Export Successful!');
        }, 500);
      })
      .catch((error) => {
        notificationHelper.createErrorNotification(
          notificationHelper.formatResponseToErrorMessage(error.response)
        );
      })
      .finally(() => {
        notificationHelper.dismissOngoingNotification();
      });
  }
}

export function runReportWithURLAndParameters(
  reportURL: ReportURL,
  reportParameters: Record<string, unknown>
): Promise<unknown> {
  notificationHelper.createOngoingNotification('Running Report');
  return runReport(() =>
    ReportService.getReportDTOWithParameterString(reportURL, reportParameters)
  ).then((response) => {
    notificationHelper.dismissOngoingNotification();
    return response;
  });
}

export function runExcelReportWithURLAndParameters(
  reportURL: ReportURL,
  reportParameters: Record<string, unknown>
): Promise<unknown> {
  notificationHelper.createOngoingNotification('Running Excel Report');
  return runReport(() =>
    ReportService.getReportDTOWithParameterString(reportURL, reportParameters)
  ).then((response) => {
    notificationHelper.dismissOngoingNotification();
    return response;
  });
}

function runReport(reportEndpointFunction: () => Promise<unknown>) {
  notificationHelper.dismissLastErrorNotification();
  return reportEndpointFunction()
    .then((response) => {
      notificationHelper.createSuccessNotification(
        'Report Successfully Generated'
      );
      return response;
    })
    .catch((error) => {
      showReportGenerationErrorMessage(error);
      return 0;
    });
}

function showReportGenerationErrorMessage(errorMsg: string): void {
  notificationHelper.dismissLastErrorNotification();
  notificationHelper.createErrorNotification(
    `The Report could not be generated due to an error: ${errorMsg}`
  );
}

export function showReportFormErrorMessage(): void {
  notificationHelper.dismissLastErrorNotification();
  notificationHelper.createErrorNotification(
    'The Report could not be generated due to form errors. Please correct form field(s).'
  );
}

export const NO_INPUTS_MODAL_TEXT =
  'There are no inputs for this report. Please click a report button below to proceed.';

export function districtParameterFormatter(
  value: string
): Array<string> | string {
  return value?.includes(',')
    ? value.split(',').map((str) => str.trim())
    : value;
}

export default {
  exportEXCELData,
  exportPDFData,
};
