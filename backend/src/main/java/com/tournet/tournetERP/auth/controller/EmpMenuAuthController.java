package com.tournet.tournetERP.auth.controller;

/**
 * 메뉴 관리
 *
 * @author : rubayi
 * @fileName : EmpMenuAuthController
 * @since : 2024-03-19
 */

import com.tournet.tournetERP.auth.dto.EmpMenuAuthRequest;
import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.repository.EmpMenuAuthRepository;
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
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empEmpMenuAuth")
public class EmpMenuAuthController {

    @Autowired
    EmpMenuAuthRepository menuAuthRepository;

    @PostMapping("/selectEmpMenuAuths")
    public ResponseEntity<Map<String, Object>> selectEmpMenuAuths(@RequestBody EmpMenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<EmpMenuAuth> currentEmpMenuAuths = null;

        currentEmpMenuAuths = menuAuthRepository.findAllByEmpUuid(menuAuthReq.getEmpUuid());

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("menuAuths", currentEmpMenuAuths);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/updateEmpMenuAuth")
    public ResponseEntity<Map<String, Object>> createEmpMenuAuth(@RequestBody EmpMenuAuthRequest menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "변경 내용 저장이 실패 했습니다.";

       if (storUser != null) {
           long[] menuAuthUuids = menuAuthReq.getMenuAuthUuids();
           long empUuid = menuAuthReq.getEmpUuid();

           for (long menuAuthUuid : menuAuthUuids) {
               // Check if data already exists for the given menuAuthUuid and empUuid
               if (!menuAuthRepository.existsByMenuAuthUuidAndEmpUuid(menuAuthUuid, menuAuthReq.getEmpUuid())) {
                   EmpMenuAuth _menuAuth = new EmpMenuAuth();
                   _menuAuth.setMenuAuthUuid(menuAuthUuid);
                   _menuAuth.setEmpUuid(empUuid);
                   menuAuthRepository.save(_menuAuth);
               }
           }
           message = "변경 내용 저장이 완료 됐습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PutMapping("/updateEmpMenuAuthId/{id}")
    public ResponseEntity<Map<String, Object>> updateEmpMenuAuth(@PathVariable long id, @RequestBody EmpMenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<EmpMenuAuth> currentEmpMenuAuth = menuAuthRepository.findByEmpAuthUuid(id);

        String message = "수정이 완료 되지 않았습니다.";

        if (storUser != null) {
            if (currentEmpMenuAuth.isPresent()) {

                EmpMenuAuth _menuAuth = currentEmpMenuAuth.get();

                _menuAuth.setEmpUuid(menuAuthReq.getEmpUuid());
                _menuAuth.setMenuAuthUuid(menuAuthReq.getMenuAuthUuid());

                menuAuthRepository.save(_menuAuth);

                message = "수정 되었습니다.";
            }
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/deleteEmpMenuAuth")
    public ResponseEntity<?> deleteEmpMenuAuth(@RequestBody EmpMenuAuthRequest menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        String message = "변경 내용 저장 되지 못 했습니다.";
        if (storUser != null) {
            long[] menuAuthUuids = menuAuthReq.getMenuAuthUuids();
            long empUuid = menuAuthReq.getEmpUuid();

            for (long menuAuthUuid : menuAuthUuids) {
                menuAuthRepository.deleteByMenuAuthUuidAndEmpUuid(menuAuthUuid, empUuid);
            }
            message = "변경 내용 저장이 완료 됐습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


}
