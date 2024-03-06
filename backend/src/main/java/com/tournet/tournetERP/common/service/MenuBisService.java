package com.tournet.tournetERP.common.service;

import com.tournet.tournetERP.common.dto.ComMenuResponse;
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
    public List<ComMenuResponse> addDetailForPermit(List<ComMenu> userComeMenu, long roleUuid) {


        long beforeTime = System.currentTimeMillis();

        // 메뉴권한

        List<ComMenuResponse> menuWithPermit = new ArrayList<ComMenuResponse>();

        for (ComMenu curMenu : userComeMenu) {
            Optional<UserMenu> tempMenuOptional = empMenuRepository.findByMenuUuidAndRoleUuid(
                        curMenu.getMenuUuid(), roleUuid);

            tempMenuOptional.ifPresent(tempMenu -> {
                ComMenuResponse _menuRes = new ComMenuResponse(
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
                        curMenu.getModifiedDt(),
                        tempMenu.getMenuRead(),
                        tempMenu.getMenuWrite(),
                        tempMenu.getMenuDelete());
                        menuWithPermit.add(_menuRes);
            });
        }

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime) / 1000; // 두 시간의 차 계산
        System.out.println("diff_time1(m) : " + secDiffTime);

        return menuWithPermit;

    }
}
