package com.tournet.tournetERP.common.controller;
/**
 * 메뉴관리
 *
 * @author : rubayi
 * @fileName : ComMenuController
 * @since : 2024-03-01
 */
import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.ERole;
import com.tournet.tournetERP.auth.entity.Role;
import com.tournet.tournetERP.auth.repository.RoleRepository;
import com.tournet.tournetERP.common.dto.ComMenuDTO;
import com.tournet.tournetERP.common.entity.ComMenu;
import com.tournet.tournetERP.common.repository.ComMenuRepository;
import com.tournet.tournetERP.common.service.MenuBisService;
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
@RequestMapping("/api/comMenu")
public class ComMenuController {

    @Autowired
    ComMenuRepository comMenuRepository;

    @Autowired
    RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    MenuBisService menuBisService;

    /**
     * 메뉴 목록 조회
     *
     * @return
     */
    @GetMapping("/selectComMenus")
    public ResponseEntity<List<ComMenu>> getComMenus() {

        List<ComMenu> commenus = new ArrayList<>();
        commenus.addAll(comMenuRepository.findAllByOrderByCreatedDtDesc());

        return new ResponseEntity<>(commenus, HttpStatus.OK);
    }

    /**
     * 메뉴 목록 조회
     *
     * @return
     */
    @PostMapping("/selectComMenusByGroup")
    public ResponseEntity<List<ComMenu>> selectComMenusByGroup(@RequestBody ComMenuDTO commenuReq) {

        List<ComMenu> commenus = new ArrayList<>();
        int menuLvl = Integer.parseInt(String.valueOf(commenuReq.getMenuLvl()));
        commenus.addAll(comMenuRepository.findAllByMenuLvlOrderByCreatedDtDesc(menuLvl));

        return new ResponseEntity<>(commenus, HttpStatus.OK);
    }


    @PostMapping("useComMenuByRole")
    public ResponseEntity<List<ComMenu>> postComMenus(@RequestBody ComMenuDTO commenuReq) {

        List<ComMenu> commenus = new ArrayList<>();

        Role _role = roleRepository.findFirstByRoles(ERole.valueOf(commenuReq.getRole()));

        commenus.addAll(comMenuRepository.findAllAndUseYnByOrderByMenuOrdAsc(_role.getRoleUuid()));

        return new ResponseEntity<>(commenus, HttpStatus.OK);
    }


    @PostMapping("useComMenuByRoleWithPermit")
    public ResponseEntity<List<ComMenuDTO>> useComMenuByRoleWithPermit(@RequestBody ComMenuDTO commenuReq) {

        List<ComMenuDTO> resCommenus = new ArrayList<>();
        List<ComMenu> commenus = new ArrayList<>();

        Role _role = roleRepository.findFirstByRoles(ERole.valueOf(commenuReq.getRole()));

        commenus.addAll(comMenuRepository.findAllAndUseYnByOrderByMenuOrdAsc(_role.getRoleUuid()));

        resCommenus = menuBisService.addDetailForPermit(commenus, _role.getRoleUuid());

        return new ResponseEntity<>(resCommenus, HttpStatus.OK);
    }



    @PutMapping("/updateComMenu")
    public ResponseEntity<Map<String, Object>> updateComMenu(@RequestBody ComMenu commenu) {

        int id = commenu.getMenuUuid();

        Optional<ComMenu> currentComMenu = comMenuRepository.findByMenuUuid(id);

        String message = "";
        if (currentComMenu.isPresent()) {
            ComMenu _commenu = currentComMenu.get();

            _commenu.setMenuKor(commenu.getMenuKor());
            _commenu.setMenuEng(commenu.getMenuEng());
            _commenu.setMenuUrl(commenu.getMenuUrl());
            _commenu.setMenuLvl(commenu.getMenuLvl());
            _commenu.setMenuOrd(commenu.getMenuOrd());
            _commenu.setMenuDesc(commenu.getMenuDesc());

            comMenuRepository.save(_commenu);
            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteComcode/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable int id) {
        comMenuRepository.deleteByMenuUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");
    }

    @Transactional
    @PostMapping("/createComMenu")
    public ResponseEntity<?> registerComMenu(@RequestBody ComMenu comMenuRequest) {

        ComMenu _comMenu = new ComMenu();
        _comMenu.setUpperMenuUuid(comMenuRequest.getUpperMenuUuid());
        _comMenu.setMenuKor(comMenuRequest.getMenuKor());
        _comMenu.setMenuEng(comMenuRequest.getMenuEng());
        _comMenu.setMenuUrl(comMenuRequest.getMenuUrl());
        _comMenu.setMenuLvl(comMenuRequest.getMenuLvl());
        _comMenu.setMenuOrd(comMenuRequest.getMenuOrd());
        _comMenu.setUseYn(comMenuRequest.getUseYn());
        _comMenu.setMenuDesc(comMenuRequest.getMenuDesc());


        return ResponseEntity.ok(new MessageResponse("코드 등록이 완료 되었습니다."));
    }

}
