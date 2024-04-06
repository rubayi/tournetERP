package com.tournet.tournetERP.common.controller;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.common.entity.UserMenu;
import com.tournet.tournetERP.auth.repository.RoleRepository;
import com.tournet.tournetERP.common.repository.UserMenuRepository;
import com.tournet.tournetERP.common.dto.UserMenuDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/userMenus")
public class UserMenuController {
 
    @Autowired
    UserMenuRepository userMenuRepository;

    @Autowired
    RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("null")
    @PutMapping("/updateUserMenus")
    public ResponseEntity<Map<String, Object>> updateUserMenus(@RequestBody UserMenuDTO userMenuRequest) {

        int menuUuid = userMenuRequest.getMenuUuid();
        int roleUuid = userMenuRequest.getRoleUuid();

        Optional<UserMenu> currentUserMenu = userMenuRepository.findByRoleUuidAndMenuUuid(roleUuid, menuUuid);

        String message = "";
        if (currentUserMenu.isPresent()) {
            UserMenu userMenu = currentUserMenu.get();

            userMenuRepository.save(userMenu);
            message = "수정되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Transactional
    @PostMapping("/createUserMenus")
    public ResponseEntity<?> createUserMenus(@RequestBody UserMenuDTO userMenuRequest) {

        int menuUuid = userMenuRequest.getMenuUuid();
        int roleUuid = userMenuRequest.getRoleUuid();

        Optional<UserMenu> currentUserMenu = userMenuRepository.findByRoleUuidAndMenuUuid(roleUuid, menuUuid);

        String message = "";
        if (currentUserMenu.isPresent()) {
            message = "이미 등록된 메뉴입니다.";
        } else {
            UserMenu userMenu = new UserMenu();
            userMenu.setMenuUuid(menuUuid);
            userMenu.setRoleUuid(roleUuid);

            userMenuRepository.save(userMenu);
            message = "등록되었습니다.";
        }

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @Transactional
    @PostMapping("/deleteUserMenus")
    public ResponseEntity<Map<String, Object>> deleteUserMenus(@RequestBody UserMenuDTO userMenuRequest) {

        int menuUuid = userMenuRequest.getMenuUuid();
        int roleUuid = userMenuRequest.getRoleUuid();

        Optional<UserMenu> currentUserMenu = userMenuRepository.findByRoleUuidAndMenuUuid(roleUuid, menuUuid);

        String message = "";
        if (currentUserMenu.isPresent()) {
            UserMenu userMenu = currentUserMenu.get();
            if (userMenu != null) {
                userMenuRepository.delete(userMenu);
                message = "삭제되었습니다.";
            } else {
                message = "삭제가 완료 되지 않았습니다.";
            }
        } else {
            message = "삭제가 완료 되지 않았습니다.";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/getUserMenus")
    public ResponseEntity<List<UserMenu>> getUserMenus(@RequestBody UserMenuDTO userMenuRequest) {

        List<UserMenu> userMenus = new ArrayList<>();
        
        int roleUuid = userMenuRequest.getRoleUuid();

        userMenus.addAll(userMenuRepository.findByRoleUuid(roleUuid));

        return new ResponseEntity<>(userMenus, HttpStatus.OK);
    }
}
