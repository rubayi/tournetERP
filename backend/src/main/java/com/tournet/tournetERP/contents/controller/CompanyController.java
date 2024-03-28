package com.tournet.tournetERP.contents.controller;

/**
 * 업체관리
 *
 * @author : rubayi
 * @fileName : CompanyController
 * @since : 2024-03-05
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.dto.UserResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.auth.service.UserService;
import com.tournet.tournetERP.contents.dto.CompanyRequest;
import com.tournet.tournetERP.contents.dto.CompanyResponse;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import com.tournet.tournetERP.contents.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/comp")
public class CompanyController {

    @Autowired
    CompanyRepository compRepository;

    @Autowired
    CompanyService compService;

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

    @PostMapping("/selectCompsPaging")
    public ResponseEntity<Map<String, Object>> selectCompanysPaging (@RequestBody CompanyRequest searchcompanyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        int page = searchcompanyReq.getPage();
        int size = searchcompanyReq.getSize();
        Pageable paging = PageRequest.of(page, size);

        List<Company> listCompany = new ArrayList<Company>();
        Page<Company> pageCompanys;

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("listComp", listCompany);
//        resMap.put("currentPage", pageCompany.getNumber());
//        resMap.put("totalItems", pageCompany.getTotalElements());
//        resMap.put("totalPages", pageCompany.getTotalPages());

        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createComp")
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company companyReq) {


        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";
        if(storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            companyReq.setModifyUser(modifyingUser);
            companyReq.setCreateUser(modifyingUser);

            compRepository.save(companyReq);
            message = "등록이 완료 되었습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/updateComp")
    public ResponseEntity<Map<String, Object>> updateCompany(@RequestBody Company companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";

        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());
            Optional<Company> currentCompany = compRepository.findByCompUuid(companyReq.getCompUuid());

            if (currentCompany.isPresent()) {

                companyReq.setModifyUser(modifyingUser);

                compRepository.save(companyReq);

                message = "수정 되었습니다.";
            } else {
                message = "수정이 완료 되지 않았습니다.";
            }

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
