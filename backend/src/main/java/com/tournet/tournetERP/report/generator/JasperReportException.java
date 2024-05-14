package com.tournet.tournetERP.report.generator;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : JasperReportException
 * @since : 2024-05-10
 */
public class JasperReportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Do not allow instantiation of this exception without a message
    public JasperReportException(String message) {
        super(message);
    }
}

