package com.tournet.tournetERP.report.service;

import com.tournet.tournetERP.report.generator.ReportGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : AbstractReportsService
 * @since : 2024-05-10
 */
public abstract class AbstractReportsService {
    private static final Log log = LogFactory.getLog(AbstractReportsService.class);

    protected ReportGenerator generator;

    public AbstractReportsService(ReportGenerator generator) {
        this.generator = generator;
    }

    protected HttpHeaders downloadFileHttpHeader(String filename) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment; filename=" + filename);
        return responseHeaders;
    }
}

