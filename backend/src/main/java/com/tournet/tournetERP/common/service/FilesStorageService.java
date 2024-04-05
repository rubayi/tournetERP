package com.tournet.tournetERP.common.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
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
  public String newSave(MultipartFile file) {

    String newFileName = "";
    try {
      // S: refileName
      String strFileName = UUID.randomUUID().toString().replace("-", "");
      String strOrgName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");

      String strOrgExt = "";

      // 확장자 추출
      Optional<String> fileExtention = getExtensionByStringHandling(strOrgName);

      if (fileExtention.isPresent()) {
        strOrgExt = fileExtention.get();
      }

      newFileName = strFileName + "." + strOrgExt;
      // E: refileName

      // 스토리지에 파일 저장
      File fileNew = new File(root.toString(), newFileName);
      FileCopyUtils.copy(file.getBytes(), fileNew);

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("파일이 이미 존재 합니다.");
      }

      throw new RuntimeException(e.getMessage());
    }

    return newFileName;
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

  /**
   * 파일 확장자 가져오기
   *
   * @param filename
   * @return
   */
  public Optional<String> getExtensionByStringHandling(String filename) {
    return Optional.ofNullable(filename)
            .filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }

}