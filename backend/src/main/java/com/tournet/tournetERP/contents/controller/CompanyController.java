package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyController
 * @since : 2024-03-05
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.CompanyRequest;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/selectCompanys")
    public ResponseEntity<Map<String, Object>> selectCompanys (@PathVariable int id,
                                                               @RequestBody Company companyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Company> currentCompanys = companyRepository.findAllByOrderByModifiedDtDesc();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("companys", currentCompanys);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/selectCompanysPaging")
    public ResponseEntity<Map<String, Object>> selectCompanysPaging (@RequestBody CompanyRequest searchcompanyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        int page = searchcompanyReq.getPage();
        int size = searchcompanyReq.getSize();
        Pageable paging = PageRequest.of(page, size);

        List<Company> listCompany = new ArrayList<Company>();
        Page<Company> pageCompanys;

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("listCompany", listCompany);
//        resMap.put("currentPage", pageCompany.getNumber());
//        resMap.put("totalItems", pageCompany.getTotalElements());
//        resMap.put("totalPages", pageCompany.getTotalPages());

        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createCompany")
    public ResponseEntity<?> createCompany(@RequestBody Company companyReq) {

        /**S: 수정자 정보 **/
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

        User modifyingUser = new User();
        modifyingUser.setEmpUuid(userDetails.getEmpUuid());
        /**E: 수정자 정보**/

        Company _company = new Company();

        _company.setCompSector(companyReq.getCompSector());
        _company.setCompGroup(companyReq.getCompGroup());
        _company.setCompKor(companyReq.getCompKor());
        _company.setCompEng(companyReq.getCompEng());
        _company.setCompAbbreviation(companyReq.getCompAbbreviation());
        _company.setCompColor(companyReq.getCompColor());
        _company.setLogoFile(companyReq.getLogoFile());
        _company.setEstablishmentDate(companyReq.getEstablishmentDate());
        _company.setCompanyRate(companyReq.getCompanyRate());
        _company.setMinAge(companyReq.getMinAge());
        _company.setChildAge(companyReq.getChildAge());
        _company.setYouthAge(companyReq.getYouthAge());
        _company.setCouponUseYn(companyReq.getCouponUseYn());
        _company.setPrepaidHow(companyReq.getPrepaidHow());
        _company.setBeginDt(companyReq.getBeginDt());
        _company.setEndDt(companyReq.getEndDt());
        _company.setCreatedDt(companyReq.getCreatedDt());
        _company.setModifiedDt(companyReq.getModifiedDt());

        _company.setModifyUser(modifyingUser);
        _company.setCreateUser(modifyingUser);

        companyRepository.save(_company);

        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<Map<String, Object>> updateCompany(@PathVariable int id, @RequestBody Company companyReq) {

        /**S: 수정자 정보 **/
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

        User modifyingUser = new User();
        modifyingUser.setEmpUuid(userDetails.getEmpUuid());
        /**E: 수정자 정보**/

        Optional<Company> currentCompany = companyRepository.findByCompUuid(id);

        String message = "";

        if (currentCompany.isPresent()) {

            Company _company = currentCompany.get();

            _company.setCompSector(companyReq.getCompSector());
            _company.setCompGroup(companyReq.getCompGroup());
            _company.setCompKor(companyReq.getCompKor());
            _company.setCompEng(companyReq.getCompEng());
            _company.setCompAbbreviation(companyReq.getCompAbbreviation());
            _company.setCompColor(companyReq.getCompColor());
            _company.setLogoFile(companyReq.getLogoFile());
            _company.setEstablishmentDate(companyReq.getEstablishmentDate());
            _company.setCompanyRate(companyReq.getCompanyRate());
            _company.setMinAge(companyReq.getMinAge());
            _company.setChildAge(companyReq.getChildAge());
            _company.setYouthAge(companyReq.getYouthAge());
            _company.setCouponUseYn(companyReq.getCouponUseYn());
            _company.setPrepaidHow(companyReq.getPrepaidHow());
            _company.setBeginDt(companyReq.getBeginDt());
            _company.setEndDt(companyReq.getEndDt());
            _company.setModifiedDt(companyReq.getModifiedDt());

            _company.setModifyUser(modifyingUser);

            companyRepository.save(_company);

            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable int id) {

        companyRepository.deleteByCompUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
