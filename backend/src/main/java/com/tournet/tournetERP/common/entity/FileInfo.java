package com.tournet.tournetERP.common.entity;

import lombok.Data;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : FileInfo
 * @since : 2024-04-03
 */
@Data
public class FileInfo {

    private String name;
    private String url;

    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
