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

import com.fasterxml.jackson.databind.JsonNode;
import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.common.dto.ComCodeDTO;
import com.tournet.tournetERP.common.service.FilesStorageService;
import com.tournet.tournetERP.contents.dto.CompanyDTO;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import com.tournet.tournetERP.contents.service.CompanyConverter;
import com.tournet.tournetERP.contents.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RestController
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

    @Autowired
    CompanyConverter companyConverter;

    @PostMapping("/searchCompByCondition")
    public ResponseEntity<List<CompanyDTO>> selectCompanys (@RequestBody CompanyDTO companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<CompanyDTO> currentComps = new ArrayList<CompanyDTO>();
                //compRepository.findAllByOrderByModifiedDtDesc();
        if(storUser.isAuthenticated()) {
            currentComps = compService.findCompsList(companyReq);
        }
        return new ResponseEntity<>(currentComps, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/updateComp", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateCompany(@RequestParam(value = "file", required = false) MultipartFile file,
                                                             @RequestParam(value = "companyReq") String companyReqJson) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";
        Company _company = new Company();
        if (storUser.isAuthenticated()) {

            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

                User modifyingUser = new User();
                modifyingUser.setEmpUuid(userDetails.getEmpUuid());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode companyJsonNode = objectMapper.readTree(companyReqJson);

                Company companyReq = new Company();

                companyReq.setCompUuid(companyJsonNode.get("compUuid").asLong());
                companyReq.setCompSector(companyJsonNode.get("compSector").asLong());

                companyReq.setCompGroup(companyJsonNode.get("compGroup").asLong());
                companyReq.setCompKor(companyJsonNode.get("compKor").asText());
                companyReq.setCompEng(companyJsonNode.get("compEng").asText());
                companyReq.setCompAbb(companyJsonNode.get("compAbb").asText());
                companyReq.setCompColor(companyJsonNode.get("compColor").asText());
                companyReq.setCompRate(companyJsonNode.get("compRate").asLong());
                companyReq.setHotelRate(companyJsonNode.get("hotelRate").asLong());
                companyReq.setOptionRate(companyJsonNode.get("optionRate").asLong());
                companyReq.setRentcarRate(companyJsonNode.get("rentcarRate").asLong());
                companyReq.setRestaurantRate(companyJsonNode.get("restaurantRate").asLong());
                companyReq.setHoneymoonRate(companyJsonNode.get("honeymoonRate").asLong());
                companyReq.setHoneymoonRegRate(companyJsonNode.get("honeymoonRegRate").asLong());
                companyReq.setRestaurantRate(companyJsonNode.get("restaurantRate").asLong());
                companyReq.setPackRate(companyJsonNode.get("packRate").asLong());
                companyReq.setPackRegRate(companyJsonNode.get("packRegRate").asLong());
                companyReq.setCouponYn(companyJsonNode.get("couponYn").asLong());

                companyReq.setPrepaidHow(companyJsonNode.get("prepaidHow").asLong());
                companyReq.setMinAge(companyJsonNode.get("minAge").asText());
                companyReq.setChildAge(companyJsonNode.get("childAge").asText());
                companyReq.setYouthAge(companyJsonNode.get("youthAge").asText());

                companyReq.setBeginDt(new Date(companyJsonNode.get("beginDt").asLong()));
                companyReq.setEndDt(new Date(companyJsonNode.get("endDt").asLong()));
                companyReq.setEstDate(new Date(companyJsonNode.get("estDate").asLong()));

                Optional<Company> currentCompany = compRepository.findByCompUuid(companyReq.getCompUuid());

                String fileName = "";
                if (currentCompany.isPresent()) {

                    companyReq.setModifyUser(modifyingUser);
                    Company existingCompany = currentCompany.get();

                    if(existingCompany.getLogoFile() != null) {
                        companyReq.setLogoFile(existingCompany.getLogoFile());
                    }
                } else {
                    companyReq.setModifyUser(modifyingUser);
                    companyReq.setCreateUser(modifyingUser);
                }

                if (file != null) {
                    fileName = storageService.newSave(file);
                    companyReq.setLogoFile(fileName);
                }

                _company = compRepository.save(companyReq);

            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    message = "파일이 이미 존재합니다.";
                }
                message = e.getMessage();
            }
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(_company, HttpStatus.OK);
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

    @GetMapping("/getComp/{id}")
    public ResponseEntity<?> searchCompany(@PathVariable long id) {

        Company company = compRepository.findFirstByCompUuid(id);

        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteComp/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable long id) {

        compRepository.deleteByCompUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }



}
