package com.tournet.tournetERP.common.controller;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.common.dto.ComCodeRequestDTO;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/comCodes")
public class ComCodeController {

    @Autowired
    ComCodeRepository comCodeRepository;

    /**
     * 공통코드 목록 조회
     *
     * @return
     */
    @GetMapping("/comCodes")
    public ResponseEntity<List<ComCode>> getComCodes() {

        List<ComCode> comcodes = new ArrayList<ComCode>();
        comcodes.addAll(comCodeRepository.findAllByOrderByCreatedDtDesc());

        return new ResponseEntity<>(comcodes, HttpStatus.OK);
    }

    /**
     * 공통코드 그룹코드 조회
     *
     * @return
     */
    @GetMapping("/GrpComCodes")
    public ResponseEntity<List<ComCode>> GrpComCodes() {

        List<ComCode> comcodes = new ArrayList<ComCode>();
        comcodes.addAll(comCodeRepository.findByUprCodeUuidIsNullOrZeroOrderByCodeOrdAsc());

        return new ResponseEntity<>(comcodes, HttpStatus.OK);
    }

    @PostMapping("useComCodeByGrp")
    public ResponseEntity<List<ComCode>> postComCodes(@RequestBody ComCode comcode) {

        List<ComCode> comcodesList = comCodeRepository.findByUprCodeUuidAndUseYnByOrderByCodeOrdAsc(
                comcode.getUprCodeUuid(),
                comcode.getCodeLvl());

        return new ResponseEntity<>(comcodesList, HttpStatus.OK);
    }

    @PostMapping("SearchComCodesByGrp")
    public ResponseEntity<List<ComCode>> SearchComCodesByGrp(@RequestBody ComCode comcode) {

        List<ComCode> comcodes = comCodeRepository.findByUprCodeUuidOrderByCodeOrdAsc(comcode.getCodeUuid());

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


    @PutMapping("/updateComCode")
    public ResponseEntity<Map<String, Object>> updateComCode(@RequestBody ComCode comcode) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        int id = comcode.getCodeUuid();

        Optional<ComCode> currentComCode = comCodeRepository.findByCodeUuid(id);

        String message = "";
        if (currentComCode.isPresent()) {
            ComCode _comcode = currentComCode.get();

            _comcode.setCodeKr(comcode.getCodeKr());
            _comcode.setCodeEn(comcode.getCodeEn());
            _comcode.setCodeValue(comcode.getCodeValue());
            _comcode.setCodeLvl(comcode.getCodeLvl());
            _comcode.setCodeOrd(comcode.getCodeOrd());
            _comcode.setEtc(comcode.getEtc());

            comCodeRepository.save(_comcode);
            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteComcode/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable int id) {
        comCodeRepository.deleteByCodeUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");
    }

    @Transactional
    @PostMapping("/createComCode")
    public ResponseEntity<?> registerComCode(@RequestBody ComCodeRequestDTO comCodeRequest) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();


        ComCode _comCode = new ComCode();
        _comCode.setUprCodeUuid(comCodeRequest.getUprCodeUuid());
        _comCode.setCodeKr(comCodeRequest.getCodeKr());
        _comCode.setCodeEn(comCodeRequest.getCodeEn());
        _comCode.setCodeValue(comCodeRequest.getCodeValue());
        _comCode.setCodeLvl(comCodeRequest.getCodeLvl());
        _comCode.setCodeOrd(comCodeRequest.getCodeOrd());
        _comCode.setUseYn(comCodeRequest.getUseYn());
        _comCode.setEtc(comCodeRequest.getEtc());

        comCodeRepository.save(_comCode);

        return ResponseEntity.ok(new MessageResponse("코드 등록이 완료 되었습니다."));
    }

}
