package com.tournet.tournetERP.report.generator;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ReportType
 * @since : 2024-05-10
 */
public enum ReportType {
    PDF("pdf", "pdf"),
    EXCEL("excel", "xls");

    private String value;
    private String ext;

    ReportType(String value, String ext) {
        this.value = value;
        this.ext = ext;
    }

    public String getValue() {
        return value;
    }

    public String getExt() {
        return ext;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static ReportType getEnum(String value) {
        if (value == null) {
            return null;
        }
        for (ReportType v : ReportType.values()) {
            if (value.equalsIgnoreCase(v.getValue())) {
                return v;
            }
        }
        return PDF;
    }
}