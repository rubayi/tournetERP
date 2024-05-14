package com.tournet.tournetERP.report.service;

import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.report.entity.ExportListReport;
import com.tournet.tournetERP.report.generator.ExportListPdfReportGenerator;
import com.tournet.tournetERP.report.generator.ExportListReportGenerator;
import com.tournet.tournetERP.report.generator.ExportOnePdfReportGenerator;
import com.tournet.tournetERP.report.utils.ReportUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ExportReportService
 * @since : 2024-05-10
 */

@Service
public class ExportReportService {
    private ExportListReportGenerator exportListReportGenerator;
    private ExportListPdfReportGenerator exportListpdfReportGenerator;
    private ExportOnePdfReportGenerator exportOnepdfReportGenerator;

    public ExportReportService(ExportListReportGenerator exportListReportGenerator, ExportListPdfReportGenerator exportListpdfReportGenerator, ExportOnePdfReportGenerator exportOnepdfReportGenerator) {
        this.exportListReportGenerator = exportListReportGenerator;
        this.exportListpdfReportGenerator = exportListpdfReportGenerator;
        this.exportOnepdfReportGenerator = exportOnepdfReportGenerator;
    }

    public ResponseEntity<byte[]> getExportListReport(ExportListReport reportVO) {
        Map<String, Object> parameters = new HashMap<>();
        //TODO: Populate with current user when login implemented
        parameters.put("username", "username");
        //TODO: Populate with PssClientServer /version
        parameters.put("environment", "DEV");
        parameters.put("version", "0.1.0");
        parameters.put("title", reportVO.getTitle());
        parameters.put("sort", reportVO.getSort());
        parameters.put("filter", reportVO.getFilter());

        byte[] report = exportListReportGenerator.generate(reportVO.getData(), parameters);
        String filename = exportListReportGenerator.buildFilename(null);
        return new ResponseEntity<>(report, ReportUtils.generateDownloadFileHttpHeader(filename), HttpStatus.OK);
    }

    public ResponseEntity<?> getExportListPdfReport(ExportListReport reportVO) {
        Map<String, Object> parameters = new HashMap<>();
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            parameters.put("username", userDetails.getUsername());
            parameters.put("environment", "DEV");
            parameters.put("version", "0.1.0");
            parameters.put("title", reportVO.getTitle());
            parameters.put("sort", reportVO.getSort());
            parameters.put("filter", reportVO.getFilter());

            byte[] report = exportListpdfReportGenerator.generate(reportVO.getData(), parameters);
            String filename = exportListpdfReportGenerator.buildFilename(null);

            return new ResponseEntity<>(report, ReportUtils.generateDownloadFileHttpHeader(filename), HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("User is not authenticated", headers, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> getExportOnepdfReport(ExportListReport reportVO) {
        Map<String, Object> parameters = new HashMap<>();
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            parameters.put("username", userDetails.getUsername());
            parameters.put("environment", "DEV");
            parameters.put("version", "0.1.0");
            parameters.put("title", reportVO.getTitle());

            byte[] report = exportOnepdfReportGenerator.generate(reportVO.getData(), parameters);
            String filename = exportOnepdfReportGenerator.buildFilename(null);
            return new ResponseEntity<>(report, ReportUtils.generateDownloadFileHttpHeader(filename), HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("User is not authenticated", headers, HttpStatus.UNAUTHORIZED);
        }
    }
}
