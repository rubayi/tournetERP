package com.tournet.tournetERP.auth.repository;

import java.util.List;
import java.util.Optional;
import com.tournet.tournetERP.auth.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface EmpRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmpEmail(String email);

    List<User> findAllByOrderByEmpBeginDtDesc();

    @Query("SELECT u FROM User u " +
            "WHERE (:empStatus IS NULL OR u.empStatus = :empStatus) " +
            "AND (:empKor IS NULL OR u.empKor LIKE CONCAT('%', :empKor, '%')) " +
            "AND (:empEng IS NULL OR u.empEng LIKE CONCAT('%', :empEng, '%')) " +
            "AND (:empUsername IS NULL OR u.username LIKE CONCAT('%', :empUsername, '%')) " +
            "ORDER BY u.empBeginDt DESC")
    Page<User> findByEmpStatusOrEmpKorOrEmpEngOrUsernameOrderByEmpBeginDtDesc(
            @Param("empStatus") String empStatus,
            @Param("empKor") String empKor,
            @Param("empEng") String empEng,
            @Param("empUsername") String empUsername, Pageable paging
    );

    void deleteByEmpUuid(int id);

    Optional<User> findByEmpUuid(int id);

    User findFirstByEmpUuid(int empUuid);
}
