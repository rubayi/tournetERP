package com.tournet.tournetERP.report.generator;

import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.report.generator.AbstractReportGenerator;
import com.tournet.tournetERP.report.generator.ReportType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ExportListReportGenerator
 * @since : 2024-05-10
 */

@Service
public class ExportListReportGenerator extends AbstractReportGenerator<Company> {

    private static final String EXCEL_TEMPLATE = "trafficmemologsearchcsvReport.jasper";
    protected static final String REPORT_NAME = "trafficmemologsearchcsvReport";
    public static final String REPORT_TYPE = ReportType.EXCEL.getValue();

    @Override
    public void setTemplates() {
        this.templates = new HashMap<>();
        this.templates.put(ReportType.EXCEL, EXCEL_TEMPLATE);
    }

    @Override
    public byte[] generate(List<Company> data, Map<String, Object> parameters) {
        this.parameters = parameters;
        return exportReport(data, REPORT_TYPE);
    }

    @Override
    public String buildFilename(List<Object> details) {
        return REPORT_NAME + getExt();
    }
}
