package com.tournet.tournetERP.contents.controller;

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
import com.tournet.tournetERP.contents.dto.InfoDTO;
import com.tournet.tournetERP.contents.repository.InfoRepository;
import com.tournet.tournetERP.contents.entity.Info;
import com.tournet.tournetERP.contents.service.InfoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/info")
public class InfoController {

    @Autowired
    InfoRepository infoRepository;

    @Autowired
    InfoService infoService;

    @PostMapping("/selectInfos")
    public ResponseEntity<Map<String, Object>> selectInfos (@RequestBody Info infoReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Info> currentInfos = infoRepository.findAllByOrderByModifiedDtDesc();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("infos", currentInfos);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/searchInfoByCondition")
    public ResponseEntity<?> searchInfoByCondition (@RequestBody InfoDTO infoReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<InfoDTO> infoList = new ArrayList<InfoDTO>();

        if (storUser.isAuthenticated()) {
            infoList = infoService.findinfosList(infoReq);
        }

        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }

    @GetMapping("/searchInfo/{id}")
    public ResponseEntity<?> searchInfoByInfoUuid (@PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Info curInfo = new Info();
        if (storUser.isAuthenticated()) {
           curInfo = infoRepository.findOneByInfoUuid(id);
        }

        return new ResponseEntity<>(curInfo, HttpStatus.OK);
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<?> updateInfo(@RequestBody Info infoReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Info  updatedInfo = new Info();
        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<Info> currentInfo = infoRepository.findByInfoUuid(infoReq.getInfoUuid());

            if (currentInfo.isPresent()) {
                infoReq.setModifiedUser(modifyingUser);
            } else {
                infoReq.setModifiedUser(modifyingUser);
                infoReq.setCreatedUser(modifyingUser);
            }

            updatedInfo = infoRepository.save(infoReq);
        }

        return new ResponseEntity<>(updatedInfo, HttpStatus.OK);

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
