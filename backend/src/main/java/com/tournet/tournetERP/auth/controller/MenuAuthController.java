package com.tournet.tournetERP.auth.controller;

/**
 * 메뉴 관리
 *
 * @author : rubayi
 * @fileName : MenuAuthController
 * @since : 2024-03-19
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.MenuAuth;
import com.tournet.tournetERP.auth.repository.MenuAuthRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;




@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/menuAuth")
public class MenuAuthController {

    @Autowired
    MenuAuthRepository menuAuthRepository;

    @Autowired
    EntityManager em;

    @PostMapping("/selectMenuAuths")
    public ResponseEntity<Map<String, Object>> selectMenuAuths (@RequestBody MenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<MenuAuth> currentMenuAuths = menuAuthRepository.findAll();
        Query query = em.createNativeQuery("SELECT MAX(CONVERT(group_code, SIGNED)) FROM com_menu_auth");
        Long maxGroupCode = (Long) query.getSingleResult();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("menuAuths", currentMenuAuths);
        resMap.put("maxNumber", maxGroupCode);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


    @Transactional
    @PostMapping("/createMenuAuth")
    public ResponseEntity<?> createMenuAuth(@RequestBody MenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        MenuAuth _menuAuth = new MenuAuth();

        _menuAuth.setMenuAuthUuid(menuAuthReq.getMenuAuthUuid());
        _menuAuth.setGroupCode(menuAuthReq.getGroupCode());
        _menuAuth.setAuthYn(menuAuthReq.getAuthYn());

        menuAuthRepository.save(_menuAuth);

        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PutMapping("/updateMenuAuth/{id}")
    public ResponseEntity<Map<String, Object>> updateMenuAuth(@PathVariable long id, @RequestBody MenuAuth menuAuthReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<MenuAuth> currentMenuAuth = menuAuthRepository.findByMenuAuthUuid(id);

        String message = "";

        if (currentMenuAuth.isPresent()) {

            MenuAuth _menuAuth = currentMenuAuth.get();

            _menuAuth.setMenuAuthUuid(menuAuthReq.getMenuAuthUuid());
            _menuAuth.setMenuAuthName(menuAuthReq.getMenuAuthName());
            _menuAuth.setGroupCode(menuAuthReq.getGroupCode());
            _menuAuth.setAuthYn(menuAuthReq.getAuthYn());

            menuAuthRepository.save(_menuAuth);

            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteMenuAuth/{id}")
    public ResponseEntity<?> deleteMenuAuth(@PathVariable int id) {

        menuAuthRepository.deleteByMenuAuthUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
