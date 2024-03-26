package com.tournet.tournetERP.auth.repository;

import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.entity.MenuAuth;
import com.tournet.tournetERP.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : EmpMenuAuthRepository
 * @since : 2024-03-21
 */
public interface EmpMenuAuthRepository extends JpaRepository<EmpMenuAuth, Long> {
    List<EmpMenuAuth> findAllByEmpUuid(long empUuid) ;

    Optional<EmpMenuAuth> findByEmpAuthUuid(long id);

    Boolean existsByMenuAuthUuidAndEmpUuid(long menuAuthUuid, long empUuid);

    void deleteByEmpAuthUuid(int id);

    void deleteByMenuAuthUuidAndEmpUuid(long menuAuthUuid, long empUuid);
}
