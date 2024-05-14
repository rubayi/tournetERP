package com.tournet.tournetERP.report.utils;

import org.springframework.http.HttpHeaders;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ReportUtils
 * @since : 2024-05-10
 */
public class ReportUtils {
    public static HttpHeaders generateDownloadFileHttpHeader(String filename) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment; filename=" + filename);
        return responseHeaders;
    }
}