package com.tournet.tournetERP.report.entity;

import com.tournet.tournetERP.contents.entity.Company;

import java.util.List;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ExportListReport
 * @since : 2024-05-10
 */
public class ExportListReport  extends ListReport<Company>{
    public ExportListReport() {
        super();
    }
    public ExportListReport(List<Company> data) {
        super(data);
    }
}
