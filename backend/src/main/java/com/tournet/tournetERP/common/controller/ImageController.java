package com.tournet.tournetERP.common.controller;
/**
 * 파일 관리
 *
 * @author : rubayi
 * @fileName : ComCodeController
 * @since : 2024-04-01
 */

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.common.entity.Image;
import com.tournet.tournetERP.common.repository.ImageRepository;
import com.tournet.tournetERP.common.service.FilesStorageService;
import com.tournet.tournetERP.common.service.FilesStorageServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    FilesStorageService storageService;

    private final Path root = Paths.get("uploads");

    /**
     * 이미지 등록
     * 
     * @param imageReq
     * @return
     */
    @Transactional
    @RequestMapping(consumes = { "multipart/form-data" })
    public String updateImage(@RequestParam("file") MultipartFile file,
            @ModelAttribute Image imageReq) {
        String message = "";
        String showFileName = "";

        Map<String, Object> response = new HashMap<>();

        try {
            // S:작성자
            Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            if (imageReq.getImgUuid() == 0) {
                imageReq.setCreateUser(modifyingUser);
            }

            // E:작성자

            // S: refileName
            String strFileName = UUID.randomUUID().toString().replace("-", "");
            String strOrgName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");

            String strOrgExt = "";

            // 확장자 추출
            Optional<String> fileExtention = getExtensionByStringHandling(strOrgName);

            if (fileExtention.isPresent()) {
                strOrgExt = fileExtention.get();
            }

            String newFileName = strFileName + "." + strOrgExt;
            // E: refileName

            // 스토리지에 파일 저장
            File fileNew = new File(root.toString(), newFileName);
            FileCopyUtils.copy(file.getBytes(), fileNew);

            imageReq.setFileName(newFileName);
            imageReq.setFileType(file.getContentType());
            imageReq.setFileLoc(this.root.resolve(newFileName).toString());

            // DB에 파일 저장
            imageRepository.save(imageReq);

            message = "파일이 업로드 되었습니다.";

            showFileName = newFileName;

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                // throw new RuntimeException("파일이 이미 존재 합니다.");
                message = "파일이 이미 존재 합니다.";
            }

            // throw new RuntimeException(e.getMessage());
            message = e.getMessage();
        }

        response.put("upfiles", showFileName);
        response.put("message", message);

        return showFileName;
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

    /**
     * 이미지 정보 수정
     * 
     * @param imageReq
     * @return
     */
    @Transactional
    @PostMapping("/updateInfoImage")
    public ResponseEntity<?> updateImage(@Valid @RequestBody Image imageReq) {

        // S:작성자
        // Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        // UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

        // String userId = userDetails.getEmpUuid();
        // E:작성자

        Optional<Image> currentImage = imageRepository.findByImgUuid(imageReq.getImgUuid());

        String message = "";
        if (currentImage.isPresent()) {
            Image _proImage = currentImage.get();

            _proImage.setImgUuid(imageReq.getImgUuid());
            _proImage.setMainYn(imageReq.getMainYn());
            _proImage.setImgOrd(imageReq.getImgOrd());

            imageRepository.save(_proImage);
            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        return ResponseEntity.ok(new MessageResponse(message));
    }

    /**
     * 이미지 삭제
     * 
     * @param id
     * @return
     */
    @Transactional
    @DeleteMapping("deleteImage/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable long id) {

        imageRepository.deleteByImgUuid(id);

        return ResponseEntity.ok("이미지가 삭제 되었습니다.");
    }

    /**
     * 이미지 목록 조회
     * 
     * @param imgRequest
     * @return
     */
    @PostMapping("/searchImages")
    public ResponseEntity<Map<String, Object>> simpleSearchImages(@RequestBody Image imgRequest) {

        List<Image> images = new ArrayList<Image>();

        String grpIdx = imgRequest.getGrpIdx();

        if (imgRequest.getGrpIdx() == null) {

            images = imageRepository.findImagesByGrpIdxOrderByCreatedDtDesc(grpIdx);

        }

        Map<String, Object> response = new HashMap<>();
        response.put("images", images);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
