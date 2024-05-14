package com.tournet.tournetERP.report.entity;

import lombok.Data;

import java.util.List;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ListReport
 * @since : 2024-05-10
 */

@Data
abstract public class ListReport<T> {
    private String title;
    private String sort;
    private String filter;
    private List<T> data;

    public ListReport() {
        super();
    }

    public ListReport(List<T> data) {
        super();
        this.data = data;
    }
}


