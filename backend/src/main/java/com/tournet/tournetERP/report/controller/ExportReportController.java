package com.tournet.tournetERP.report.controller;

import com.tournet.tournetERP.report.entity.ExportListReport;
import com.tournet.tournetERP.report.service.ExportReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ExportReportController
 * @since : 2024-05-10
 */

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/report")
public class ExportReportController {

    private ExportReportService exportReportService;

    public ExportReportController(ExportReportService exportReportService) {
        this.exportReportService = exportReportService;
    }
    @PostMapping(value = "/exportexcel", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getExportListReport(@RequestBody ExportListReport rowdata) {
        return exportReportService.getExportListReport(rowdata);
    }

    @PostMapping(value = "/exportpdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<?> getExportpdfListReport(@RequestBody ExportListReport rowdata) {
        return exportReportService.getExportListPdfReport(rowdata);
    }

    @PostMapping(value = "/exportpdfone", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<?> getExportoneListReport(@RequestBody ExportListReport rowdata) {
        return exportReportService.getExportOnepdfReport(rowdata);
    }
}
