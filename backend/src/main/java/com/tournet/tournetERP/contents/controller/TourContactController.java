package com.tournet.tournetERP.contents.controller;

/**
 * 투어연락처 정보 관리
 *
 * @author : rubayi
 * @fileName : ContactController
 * @since : 2024-03-26
 */

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TourContactDTO;
import com.tournet.tournetERP.contents.entity.Contact;
import com.tournet.tournetERP.contents.entity.TourContact;
import com.tournet.tournetERP.contents.repository.TourContactRepository;
import com.tournet.tournetERP.contents.service.TourContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/trContact")
public class TourContactController {

    @Autowired
    TourContactRepository contactRepository;

    @Autowired
    TourContactService contactService;

    @PostMapping("/searchContactByCondition")
    public ResponseEntity<?> selectContacts (@RequestBody TourContact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TourContactDTO> currentContacts = contactService.findTourContactList(contactReq);


        return new ResponseEntity<>(currentContacts, HttpStatus.OK);
    }

    @PostMapping("/updateContact")
    public ResponseEntity<?> updateContact(@RequestBody TourContact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        TourContact _tourContact = new TourContact();
        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            _tourContact = contactRepository.save(contactReq);
        }

        return new ResponseEntity<>(_tourContact, HttpStatus.OK);
    }


    @GetMapping("/selectByContactUuid/{id}")
    public ResponseEntity<?> selectByContactUuid(@PathVariable long id) {

        TourContact contact = contactRepository.findOneByContactUuid(id);

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteContact/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable long id) {

        contactRepository.deleteByContactUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
