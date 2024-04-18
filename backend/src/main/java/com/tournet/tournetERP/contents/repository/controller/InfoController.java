package com.tournet.tournetERP.contents.repository.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : InfoController
 * @since : 2024-04-17
 */
import java.util.*;

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.repository.InfoRepository;
import com.tournet.tournetERP.contents.entity.Info;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/info")
public class InfoController {

    @Autowired
    InfoRepository infoRepository;

//    @Autowired
//    InfoService infoService;

    @PostMapping("/selectInfos")
    public ResponseEntity<Map<String, Object>> selectInfos (@RequestBody Info infoReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Info> currentInfos = infoRepository.findAllByOrderByModifiedDtDesc();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("infos", currentInfos);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<Map<String, Object>> updateInfo(@RequestBody Info infoReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "";

        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<Info> currentInfo = infoRepository.findByInfoUuid(infoReq.getInfoUuid());

            if (currentInfo.isPresent()) {
                infoReq.setModifiedUser(modifyingUser);
                message = "수정 되었습니다.";
            } else {
                infoReq.setModifiedUser(modifyingUser);
                infoReq.setCreatedUser(modifyingUser);
                message = "등록 되었습니다.";
            }

            infoRepository.save(infoReq);
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);

    }

    @Transactional
    @PostMapping("/deleteinfo/{id}")
    public ResponseEntity<?> deleteinfo(@PathVariable long id) {

        String message = "";

        infoRepository.deleteByInfoUuid(id);

        message="삭제 되었습니다.";

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);

    }


}
