package com.tournet.tournetERP.common.repository;
import java.util.Optional;
import java.util.List;

import com.tournet.tournetERP.common.entity.UserMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMenuRepository extends JpaRepository<UserMenu, Long> {
    
    void deleteByEmpMenuUuid(int id);

    Optional<UserMenu> findByEmpMenuUuid(int id);

    List<UserMenu> findByRoleUuid(int roleUuid);

    @Query("SELECT m FROM UserMenu m " +
            "WHERE m.roleUuid = :roleUuid " +
            "AND m.menuUuid = :menuUuid")
    Optional<UserMenu> findByRoleUuidAndMenuUuid(int roleUuid, int menuUuid);

    Optional<UserMenu> findByMenuUuidAndRoleUuid(int menuUuid, long roleUuid);
}
