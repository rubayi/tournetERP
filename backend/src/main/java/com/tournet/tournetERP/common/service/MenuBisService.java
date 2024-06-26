package com.tournet.tournetERP.common.service;

import com.tournet.tournetERP.common.dto.ComMenuDTO;
import com.tournet.tournetERP.common.entity.ComMenu;
import com.tournet.tournetERP.common.entity.UserMenu;
import com.tournet.tournetERP.common.repository.UserMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : MenuBisService
 * @since : 2024-03-06
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuBisService {

    @Autowired
    private final UserMenuRepository empMenuRepository;
    
    // 정보 가져오기
    public List<ComMenuDTO> addDetailForPermit(List<ComMenu> userComeMenu, long roleUuid) {


        long beforeTime = System.currentTimeMillis();

        // 메뉴권한

        List<ComMenuDTO> menuWithPermit = new ArrayList<ComMenuDTO>();

        for (ComMenu curMenu : userComeMenu) {
            Optional<UserMenu> tempMenuOptional = empMenuRepository.findByMenuUuidAndRoleUuid(
                        curMenu.getMenuUuid(), roleUuid);

            tempMenuOptional.ifPresent(tempMenu -> {
                ComMenuDTO _menuRes = new ComMenuDTO(
                        curMenu.getMenuUuid(),
                        curMenu.getUpperMenuUuid(),
                        tempMenu.getRoleUuid(),
                        curMenu.getMenuLvl(),
                        curMenu.getMenuIcon(),
                        curMenu.getMenuKor(),
                        curMenu.getMenuEng(),
                        curMenu.getMenuUrl(),
                        curMenu.getMenuOrd(),
                        curMenu.getMenuDesc(),
                        curMenu.getUseYn(),
                        curMenu.getCreatedDt(),
                        curMenu.getCreatedBy(),
                        (curMenu.getModifiedBy() != null ? curMenu.getModifiedBy(): 0),
                        curMenu.getModifiedDt());
                        menuWithPermit.add(_menuRes);
            });
        }

        return menuWithPermit;

    }
}
