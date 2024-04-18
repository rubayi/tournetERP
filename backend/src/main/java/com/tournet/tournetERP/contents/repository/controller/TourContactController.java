package com.tournet.tournetERP.contents.repository.controller;

/**
 * 투어연락처 정보 관리
 *
 * @author : rubayi
 * @fileName : ContactController
 * @since : 2024-03-26
 */

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TourContactDTO;
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
    public ResponseEntity<Map<String, Object>> selectContacts (@RequestBody TourContact contactReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TourContactDTO> currentContacts = contactService.findTourContactList(contactReq);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("contactList", currentContacts);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/updateContact")
    public ResponseEntity<Map<String, Object>> updateContact(@RequestBody TourContact contactReq) {

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
