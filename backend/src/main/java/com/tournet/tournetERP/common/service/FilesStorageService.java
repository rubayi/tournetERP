package com.tournet.tournetERP.common.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FilesStorageService implements FilesStorageServiceImpl {

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);

    } catch (IOException e) {
      throw new RuntimeException("디렉토리를 초기화 할 수 없습니다.");
    }

    
  }

  @Override
  public void save(MultipartFile file) {


    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("파일이 이미 존재 합니다.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("파일을 읽을 수 없습니다.");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1)
             .filter(path -> !path.equals(this.root))
             .map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("파일을 불러 올 수 없습니다.");
    }
  }
}