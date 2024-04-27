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
import org.springframework.web.bind.annotation.PutMapping;
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

        System.out.println(comcodesList);
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


    @PutMapping("/updateComCode")
    public ResponseEntity<Map<String, Object>> updateComCode(@RequestBody ComCode comcode) {

        /**S: 수정자 정보 **/
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

        User modifyingUser = new User();
        modifyingUser.setEmpUuid(userDetails.getEmpUuid());
        /**E: 수정자 정보**/

        long id = (long) comcode.getCodeUuid();

        Optional<ComCode> currentComCode = comCodeRepository.findByCodeUuid(id);

        String message = "";
        if (currentComCode.isPresent()) {
            ComCode _comcode = currentComCode.get();

            _comcode.setCodeKr(comcode.getCodeKr());
            _comcode.setCodeEn(comcode.getCodeEn());
            _comcode.setCodeLvl(comcode.getCodeLvl());
            _comcode.setCodeOrd(comcode.getCodeOrd());
            _comcode.setEtc(comcode.getEtc());
            _comcode.setUseYn(comcode.getUseYn());
            _comcode.setModifyUser(modifyingUser);

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
