package com.tournet.tournetERP.contents.controller;

/**
 * 업체 연락처 정보 관리
 *
 * @author : rubayi
 * @fileName : ContactController
 * @since : 2024-03-26
 */
import java.util.*;

import com.tournet.tournetERP.account.entity.CreditCardMng;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.ContactDTO;
import com.tournet.tournetERP.contents.entity.Contact;
import com.tournet.tournetERP.contents.repository.ContactRepository;
import com.tournet.tournetERP.contents.service.ContactService;
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

import com.tournet.tournetERP.auth.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ContactService contactService;

    @PostMapping("/searchContactByCondition")
    public ResponseEntity<Map<String, Object>> selectContacts (@RequestBody Contact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<ContactDTO> currentContacts = contactService.findContactList(contactReq);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("contactList", currentContacts);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/updateContact")
    public ResponseEntity<Map<String, Object>> updateContact(@RequestBody Contact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "";
        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            if (contactReq.getContactUuid() != 0) {
                message = "수정 되었습니다.";
            } else {
                message = "등록 되었습니다.";
            }
            contactRepository.save(contactReq);
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/deleteContact/{id}")
    public ResponseEntity<Map<String, Object>> deleteContact(@PathVariable long id) {

        String message = "데이터를 삭제하지 못 했습니다. ";

        contactRepository.deleteByContactUuid(id);

        message = "데이터를 삭제 했습니다.";

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


}
