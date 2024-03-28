package com.tournet.tournetERP.contents.controller;

/**
 * 업체 연락처 정보 관리
 *
 * @author : rubayi
 * @fileName : ContactController
 * @since : 2024-03-26
 */
import java.util.*;

import com.tournet.tournetERP.contents.entity.Contact;
import com.tournet.tournetERP.contents.repository.ContactRepository;
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

    @PostMapping("/selectContacts")
    public ResponseEntity<Map<String, Object>> selectContacts (@RequestBody Contact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Contact> currentContacts = contactRepository.findByCompUuidOrderByContactUuidDesc(contactReq.getCompUuid());

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("contacts", currentContacts);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createContact")
    public ResponseEntity<?> createContact(@RequestBody Contact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Contact _contact = new Contact();

        _contact.setContactUuid(contactReq.getContactUuid());
        _contact.setContactType(contactReq.getContactType());
        _contact.setRepYn(contactReq.getRepYn());
        _contact.setContactCont(contactReq.getContactCont());

        contactRepository.save(_contact);

        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PutMapping("/updateContact/{id}")
    public ResponseEntity<Map<String, Object>> updateContact(@PathVariable long id, @RequestBody Contact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<Contact> currentContact = contactRepository.findByContactUuid(id);

        String message = "";

        if (currentContact.isPresent()) {

            Contact _contact = currentContact.get();

            _contact.setContactUuid(contactReq.getContactUuid());
            _contact.setContactType(contactReq.getContactType());
            _contact.setRepYn(contactReq.getRepYn());
            _contact.setContactCont(contactReq.getContactCont());

            contactRepository.save(_contact);

            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deletecontact/{id}")
    public ResponseEntity<Map<String, Object>> deleteContact(@PathVariable long id) {

        String message = "데이터를 삭제하지 못 했습니다. ";

        contactRepository.deleteByContactUuid(id);

        message = "데이터를 삭제 되었습니다.";
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


}
