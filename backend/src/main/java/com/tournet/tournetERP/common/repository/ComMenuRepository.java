package com.tournet.tournetERP.common.repository;
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.common.dto.ComMenuResponse;
import com.tournet.tournetERP.common.entity.ComMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComMenuRepository extends JpaRepository<ComMenu, Long> {
    void deleteByMenuUuid(int id);

    List<ComMenu> findAllByOrderByCreatedDtDesc();

    List<ComMenu> findByUpperMenuUuidOrderByMenuOrdAsc(int menuUuid);

    Optional<ComMenu> findByMenuUuid(int id);

    @Query("SELECT m FROM ComMenu m " +
            "WHERE m.useYn = 'Y' " +
            "AND m.menuUuid IN (" +
            "SELECT em.menuUuid FROM UserMenu em WHERE em.roleUuid = :roleUuid)")
    List<ComMenu> findAllAndUseYnByOrderByMenuOrdAsc(long roleUuid);


    List<ComMenu> findAllByMenuLvlOrderByCreatedDtDesc(int menuLvl);
}
