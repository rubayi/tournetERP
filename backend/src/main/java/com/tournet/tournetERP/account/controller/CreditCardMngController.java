package com.tournet.tournetERP.account.controller;

/**
 * 프리페이드 카드 관리
 *
 * @author : rubayi
 * @fileName : CreditCardMngController
 * @since : 2024-04-08
 */
import java.util.*;

import com.tournet.tournetERP.account.dto.CreditCardMngDTO;
import com.tournet.tournetERP.account.entity.CreditCardMng;
import com.tournet.tournetERP.account.service.CreditCardMngService;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

import com.tournet.tournetERP.account.repository.CreditCardMngRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cdCdMng")
public class CreditCardMngController {

    @Autowired
    CreditCardMngRepository creditCardMngRepository;

    @Autowired
    CreditCardMngService creditCardMngService;

    @PostMapping("/selectCreditCardMngs")
    public ResponseEntity<List<CreditCardMngDTO>> selectCreditCardMngs (@RequestBody CreditCardMngDTO searchcreditCardMngReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<CreditCardMngDTO> listCreditCardMng = new ArrayList<CreditCardMngDTO>();
        if(storUser.isAuthenticated()) {
            listCreditCardMng = creditCardMngService.findCreditCardMngList(searchcreditCardMngReq);
        }

        return new ResponseEntity<>(listCreditCardMng, HttpStatus.OK);
    }

    @PostMapping("/updateCreditCardMng")
    public ResponseEntity<Map<String, Object>> updateCreditCardMng(@RequestBody CreditCardMng creditCardMngReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "";
        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<CreditCardMng> currentCreditCardMng = creditCardMngRepository.
                    findByCdCdUuid(creditCardMngReq.getCdCdUuid());

            if (currentCreditCardMng.isPresent()) {
                creditCardMngReq.setModifyUser(modifyingUser);
                message = "수정 되었습니다.";
            } else {
                creditCardMngReq.setModifyUser(modifyingUser);
                creditCardMngReq.setCreateUser(modifyingUser);
                message = "등록 되었습니다.";
            }

            creditCardMngRepository.save(creditCardMngReq);
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


    @Transactional
    @GetMapping("/deleteCreditCardMng/{id}")
    public ResponseEntity<?> deleteCdCdMng(@PathVariable long id) {

        creditCardMngRepository.deleteByCdCdUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");

    }

    @GetMapping("/getCreditCardMngBy/{id}")
    public ResponseEntity<?> getCreditCardMngBy(@PathVariable long id) {

        CreditCardMng cdcdMng = creditCardMngRepository.findOneByCdCdUuid(id);

        return new ResponseEntity<>(cdcdMng, HttpStatus.OK);

    }

}
