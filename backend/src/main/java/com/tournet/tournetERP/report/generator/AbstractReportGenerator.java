package com.tournet.tournetERP.report.generator;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;



/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : AbstractReportGenerator
 * @since : 2024-05-10
 */
public abstract class AbstractReportGenerator<T> implements ReportGenerator<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReportGenerator.class);

    protected static final String REPORTS_DIRECTORY = "/report/";

    protected Map<ReportType, String> templates;
    protected Map<String, Object> parameters;
    protected ReportType type;

    public AbstractReportGenerator() {
        setTemplates();
    }

    protected byte[] exportReport(List<T> data, String typeString) {
        logger.debug("exportReport() - typeString: " + typeString);
        setType(typeString);
        switch (this.type) {
            case EXCEL:
                return exportExcelReport(data);
            case PDF:
                return exportPdfReport(data);
            default:
                throw new JasperReportException("Unknown report type: " + typeString);
        }
    }

    private byte[] exportPdfReport(List<T> data) {
        logger.debug("exportPdfReport() - data.size(): " + data.size());
        JasperPrint jasperPrint = getJasperPrint(data);
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            logger.warn(e.getMessage(), e);
            throw new JasperReportException("Unable to export jasper report");
        }
    }


    private byte[] exportExcelReport(List<T> data) {
        logger.debug("exportExcelReport() - data.size(): " + data.size());
        JasperPrint jasperPrint = getJasperPrint(data);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
            exporter.exportReport();
            return output.toByteArray();
        } catch (JRException e) {
            logger.warn(e.getMessage(), e);
            throw new JasperReportException("Unable to export excel report");
        }
    }

    private JasperPrint getJasperPrint(List<T> data) {
        InputStream report = getFileInputStream();
        JRDataSource dataSource = new JRBeanCollectionDataSource(data);
        try {
            return JasperFillManager.fillReport(report, parameters, dataSource);
        } catch (JRException e) {
            logger.warn(e.getMessage(), e);
            throw new JasperReportException("Unable to create jasper print");
        }
    }

    private InputStream getFileInputStream() {
        ClassPathResource resource = new ClassPathResource(getTemplateName());
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            throw new JasperReportException("Unable to open file: " + getTemplateName());
        }
    }

    protected String getTemplateName() {
        return REPORTS_DIRECTORY + templates.get(this.type);
    }

    protected String getExt() {
        return "." + (this.type == null ? "error" : this.type.getExt());
    }

    public void setType(String typeString) {
        this.type = ReportType.getEnum(typeString);
    }
}