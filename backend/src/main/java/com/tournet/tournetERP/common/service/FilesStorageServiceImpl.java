package com.tournet.tournetERP.common.service;

/**
 * FilesStorageService
 *
 * @author : rubayi
 * @fileName : FilesStorageService
 * @since : 2024-04-01
 */
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageServiceImpl {
    public void init();

    public void save(MultipartFile file);

    public String newSave(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}