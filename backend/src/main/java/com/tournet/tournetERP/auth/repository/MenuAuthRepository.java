package com.tournet.tournetERP.auth.repository;

/**
 * 메뉴 관리
 *
 * @author : rubayi
 * @fileName : MenuAuthRepository
 * @since : 2024-03-19
 */
import java.util.Optional;

import com.tournet.tournetERP.auth.entity.MenuAuth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuAuthRepository extends JpaRepository<MenuAuth,Long> {

    Optional<MenuAuth> findByMenuAuthUuid(long id);

    @Modifying
    void deleteByMenuAuthUuid(@Param("id") long menuAuthUuid);


}