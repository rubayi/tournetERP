package com.tournet.tournetERP.common.controller;
/**
 * 공통코드 관리
 *
 * @author : rubayi
 * @fileName : ComCodeController
 * @since : 2024-03-01
 */
import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.common.dto.ComCodeDTO;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.common.service.ComCodeService;
import com.tournet.tournetERP.contents.dto.CompanyDTO;
import com.tournet.tournetERP.contents.service.CompanyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/comCodes")
public class ComCodeController {

    @Autowired
    ComCodeRepository comCodeRepository;

    @Autowired
    ComCodeService comCodeService;

    /**
     * 공통코드 목록 조회
     *
     * @return
     */
    @PostMapping("/comCodes")
    public ResponseEntity<List<ComCodeDTO>> getComCodes(@RequestBody ComCodeDTO comCodeReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();


        List<ComCodeDTO> comcodes = new ArrayList<ComCodeDTO>();

        if(storUser.isAuthenticated()) {
            comcodes = comCodeService.findComCodeList(comCodeReq);
        }

        return new ResponseEntity<>(comcodes, HttpStatus.OK);
    }

    /**
     * 공통코드 그룹코드 조회
     *
     * @return
     */
    @GetMapping("/grpComCodes")
    public ResponseEntity<List<ComCode>> GrpComCodes() {

        List<ComCode> comcodes = new ArrayList<ComCode>();
        comcodes.addAll(comCodeRepository.findByUprCodeUuidIsNullOrZeroOrderByCodeOrdAsc());

        return new ResponseEntity<>(comcodes, HttpStatus.OK);
    }

    @PostMapping("/getComCodeByGrp")
    public ResponseEntity<List<ComCode>> postComCodes(@RequestBody ComCodeDTO comcode) {

        List<ComCode> comcodesList = comCodeRepository.findByUprCodeUuidAndUseYnByOrderByCodeOrdAsc(
                comcode.getSearchUprCodeUuid(),
                comcode.getSearchCodeLvl());

        return new ResponseEntity<>(comcodesList, HttpStatus.OK);
    }

    @PostMapping("searchComCodesByGrp")
    public ResponseEntity<List<ComCode>> SearchComCodesByGrp(@RequestBody ComCodeDTO comcode) {

        List<ComCode> comcodes = comCodeRepository.findByUprCodeUuidOrderByCodeOrdAsc(comcode.getSearchUprCodeUuid());

        return new ResponseEntity<>(comcodes, HttpStatus.OK);
    }

    /**
     * 해당코드의 그룹코드 조회 후 하위 공통코드 조회
     *
     * @param comcode
     * @return
     */
    @PostMapping("/searchComCodeByGrp")
    public ResponseEntity<List<ComCode>> searchComCodeByGrp(@RequestBody ComCode comcode) {

        List<ComCode> selectedComcode = comCodeRepository.findByUprCodeUuidAndCodeLvlOrderByCodeOrdAsc(
                comcode.getUprCodeUuid(),
                comcode.getCodeLvl());

        return new ResponseEntity<>(selectedComcode, HttpStatus.OK);
    }


    @PostMapping("/updateComCode")
    public ResponseEntity<?> updateComCode(@RequestBody ComCode comcode) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        long id = (long) comcode.getCodeUuid();
        Optional<ComCode> currentComCode = comCodeRepository.findByCodeUuid(id);

        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            if (currentComCode.isPresent()) {
                comcode.setModifyUser(modifyingUser);

            } else {
                comcode.setModifyUser(modifyingUser);
                comcode.setCreateUser(modifyingUser);
            }

            comCodeRepository.save(comcode);
        }
        return new ResponseEntity<>(comcode, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteComcode/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable int id) {
        comCodeRepository.deleteByCodeUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");
    }

    @GetMapping("/getComcode/{id}")
    public ResponseEntity<?> getCodeInfo(@PathVariable int id) {
        ComCode comcode = comCodeRepository.findFirstByCodeUuid(id);

        return new ResponseEntity<>(comcode, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createComCode")
    public ResponseEntity<?> registerComCode(@RequestBody ComCode comCodeRequest) {
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());
            comCodeRequest.setModifyUser(modifyingUser);
            comCodeRequest.setCreateUser(modifyingUser);

            comCodeRepository.save(comCodeRequest);
        }
        return ResponseEntity.ok(new MessageResponse("코드 등록이 완료 되었습니다."));
    }

}
