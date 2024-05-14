package com.tournet.tournetERP.report.generator;

import java.util.List;
import java.util.Map;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ReportGenerator
 * @since : 2024-05-10
 */
public interface ReportGenerator<T> {

    void setTemplates();

    byte[] generate(List<T> data, Map<String, Object> parameters);

    String buildFilename(List<Object> details);

}
