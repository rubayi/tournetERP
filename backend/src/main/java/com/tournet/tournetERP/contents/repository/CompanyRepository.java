package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyRepository
 * @since : 2024-03-05
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.contents.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByCompUuid(long id);

    List<Company> findAllByOrderByModifiedDtDesc();


    @Query("SELECT u FROM Company u " +
            "WHERE (:compSector IS NULL OR u.compSector = :compSector) " +
            "AND (:compKor IS NULL OR u.compKor LIKE CONCAT('%', :compKor, '%')) " +
            "AND (:compEng IS NULL OR u.compEng LIKE CONCAT('%', :compEng, '%')) " +
            "OR (:compSector IS NULL AND :compKor IS NULL AND :compEng IS NULL) " +
            "ORDER BY u.modifiedDt DESC")
    List<Company> findAllByOrderByModifiedDtDesc(
            @Param("compSector") Long compSector,
            @Param("compKor") String compKor,
            @Param("compEng") String compEng
    );

    void deleteByCompUuid(long compUuid);


    Company findFirstByCompUuid(long id);
}
