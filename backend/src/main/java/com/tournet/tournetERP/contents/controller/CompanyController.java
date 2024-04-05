package com.tournet.tournetERP.contents.controller;

/**
 * 업체관리
 *
 * @author : rubayi
 * @fileName : CompanyController
 * @since : 2024-03-05
 */
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.dto.UserResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.auth.service.UserService;
import com.tournet.tournetERP.common.controller.ImageController;
import com.tournet.tournetERP.common.entity.Image;
import com.tournet.tournetERP.common.service.FilesStorageService;
import com.tournet.tournetERP.contents.dto.CompanyRequest;
import com.tournet.tournetERP.contents.dto.CompanyResponse;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import com.tournet.tournetERP.contents.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/comp")
public class CompanyController {

    @Autowired
    CompanyRepository compRepository;

    @Autowired
    CompanyService compService;


    @Autowired
    FilesStorageService storageService;

    @PostMapping("/searchCompByCondition")
    public ResponseEntity<Map<String, Object>> selectCompanys ( @RequestBody CompanyRequest companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "";
        List<CompanyResponse> currentComps = new ArrayList<CompanyResponse>();
                //compRepository.findAllByOrderByModifiedDtDesc();
        if(storUser.isAuthenticated()) {
            currentComps = compService.findCompsList(companyReq);
            message = "OK";
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("comps", currentComps);
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }



   @RequestMapping(value = "/createComp", method = RequestMethod.POST, consumes = { "multipart/form-data" })
   public ResponseEntity<Map<String, Object>> createCompany(@RequestParam("file") MultipartFile file,
                @RequestParam("compUuid") Company companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";
        if(storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            String fileName = "";
            if (file != null) {
                fileName = storageService.newSave(file);
                companyReq.setLogoFile(fileName);
            }

            companyReq.setModifyUser(modifyingUser);
            companyReq.setCreateUser(modifyingUser);

            compRepository.save(companyReq);
            message = "등록이 완료 되었습니다.";

        } else {
            message = "등록이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    //@PostMapping("/updateComp")
    @Transactional
    @RequestMapping(value = "/updateComp", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<Map<String, Object>> updateCompany(@RequestParam(value = "file", required = false) MultipartFile file,
                                                             @RequestParam(value = "compUuid", required = false) CompanyRequest companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";

        if (storUser.isAuthenticated()) {

            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();
                Long modifyingUser = userDetails.getEmpUuid();

                Optional<Company> currentCompany = compRepository.findByCompUuid(companyReq.getCompUuid());

                Company _comp = new Company();

                String fileName = "";
                if (currentCompany.isPresent()) {

                    companyReq.setModifiedBy(modifyingUser);

                } else {
                    companyReq.setModifiedBy(modifyingUser);
                    companyReq.setCreatedBy(modifyingUser);
                }

                if (file != null) {
                    fileName = storageService.newSave(file);
                    companyReq.setLogoFile(fileName);
                }

                message = "수정 되었습니다.";
                compRepository.save(_comp);
            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    message = "파일이 이미 존재 합니다.";
                }

                message = e.getMessage();
            }

        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    //@PostMapping("/upload")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/deleteComp/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable long id) {

        compRepository.deleteByCompUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }





}
