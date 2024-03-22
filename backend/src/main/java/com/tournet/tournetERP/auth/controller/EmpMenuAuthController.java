package com.tournet.tournetERP.auth.controller;

/**
 * 메뉴 관리
 *
 * @author : rubayi
 * @fileName : EmpMenuAuthController
 * @since : 2024-03-19
 */

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.repository.EmpMenuAuthRepository;
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
    public ResponseEntity<Map<String, Object>> selectEmpMenuAuths (@RequestBody EmpMenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<EmpMenuAuth> currentEmpMenuAuths = null;

        currentEmpMenuAuths = menuAuthRepository.findAllByEmpUuid(menuAuthReq.getEmpUuid());

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("menuAuths", currentEmpMenuAuths);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


    @Transactional
    @PostMapping("/updateEmpMenuAuth")
    public ResponseEntity<?> createEmpMenuAuth(@RequestBody EmpMenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        EmpMenuAuth _menuAuth = new EmpMenuAuth();
        
        String message = "등록이 실패 했습니다.";
        String enMessage = "Fail to Save!";

        if (storUser != null) {
            _menuAuth.setMenuAuthUuid(menuAuthReq.getMenuAuthUuid());
            _menuAuth.setEmpUuid(menuAuthReq.getEmpUuid());

            menuAuthRepository.save(_menuAuth);

            message = "등록이 완료 되었습니다.";
        }
        

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PutMapping("/updateEmpMenuAuthId/{id}")
    public ResponseEntity<Map<String, Object>> updateEmpMenuAuth(@PathVariable long id, @RequestBody EmpMenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<EmpMenuAuth> currentEmpMenuAuth = menuAuthRepository.findByEmpAuthUuid(id);

        String message = message = "수정이 완료 되지 않았습니다.";

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
    @DeleteMapping("/deleteEmpMenuAuth/{id}")
    public ResponseEntity<?> deleteEmpMenuAuth(@PathVariable int id) {

        menuAuthRepository.deleteByEmpAuthUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
