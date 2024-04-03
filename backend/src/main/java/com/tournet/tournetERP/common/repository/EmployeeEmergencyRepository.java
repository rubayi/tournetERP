package com.tournet.tournetERP.common.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : EmployeeEmergencyRepository
 * @since : 2024-04-01
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.common.entity.EmployeeEmergency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



@Repository
public interface EmployeeEmergencyRepository extends JpaRepository<EmployeeEmergency,Long> {

    Optional<EmployeeEmergency> findByEmerUuid(int id);

    List<EmployeeEmergency> findByEmpUuid(int id);
    
    @Modifying
    void deleteByEmerUuid(@Param("id") int emerUuid);


    List<EmployeeEmergency> findAllByOrderByEmerUuidDesc();
}