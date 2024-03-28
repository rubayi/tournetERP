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
        _company.setCompAbb(companyReq.getCompAbb());
        _company.setCompColor(companyReq.getCompColor());
        _company.setLogoFile(companyReq.getLogoFile());
        _company.setEstDate(companyReq.getEstDate());
        _company.setCompRate(companyReq.getCompRate());
        _company.setMinAge(companyReq.getMinAge());
        _company.setChildAge(companyReq.getChildAge());
        _company.setYouthAge(companyReq.getYouthAge());
        _company.setCouponYn(companyReq.getCouponYn());
        _company.setPrepaidHow(companyReq.getPrepaidHow());
        _company.setBeginDt(companyReq.getBeginDt());
        _company.setEndDt(companyReq.getEndDt());
        _company.setCreatedDt(companyReq.getCreatedDt());
        _company.setModifiedDt(companyReq.getModifiedDt());

        _company.setModifyUser(modifyingUser);
        _company.setCreateUser(modifyingUser);

        compRepository.save(_company);

        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PostMapping("/updateComp/{id}")
    public ResponseEntity<Map<String, Object>> updateCompany(@PathVariable long id, @RequestBody Company companyReq) {

        /**S: 수정자 정보 **/
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

        User modifyingUser = new User();
        modifyingUser.setEmpUuid(userDetails.getEmpUuid());
        /**E: 수정자 정보**/

        Optional<Company> currentCompany = compRepository.findByCompUuid(id);

        String message = "";

        if (currentCompany.isPresent()) {

            Company _company = currentCompany.get();

            _company.setCompSector(companyReq.getCompSector());
            _company.setCompGroup(companyReq.getCompGroup());
            _company.setCompKor(companyReq.getCompKor());
            _company.setCompEng(companyReq.getCompEng());
            _company.setCompAbb(companyReq.getCompAbb());
            _company.setCompColor(companyReq.getCompColor());
            _company.setLogoFile(companyReq.getLogoFile());
            _company.setEstDate(companyReq.getEstDate());
            _company.setCompRate(companyReq.getCompRate());
            _company.setMinAge(companyReq.getMinAge());
            _company.setChildAge(companyReq.getChildAge());
            _company.setYouthAge(companyReq.getYouthAge());
            _company.setCouponYn(companyReq.getCouponYn());
            _company.setPrepaidHow(companyReq.getPrepaidHow());
            _company.setBeginDt(companyReq.getBeginDt());
            _company.setEndDt(companyReq.getEndDt());
            _company.setModifiedDt(companyReq.getModifiedDt());

            _company.setModifyUser(modifyingUser);

            compRepository.save(_company);

            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
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
