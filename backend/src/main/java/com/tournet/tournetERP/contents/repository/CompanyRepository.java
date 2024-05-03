package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyRepository
 * @since : 2024-03-05
 */
import java.util.Date;
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
            "AND (:compUuid IS NULL OR u.compUuid = :compUuid) " +
            "AND (:couponYn IS NULL OR u.couponYn = :couponYn) " +
            "AND (:compGroup IS NULL OR u.compGroup = :compGroup) " +
            "AND (:beginDt IS NULL OR u.beginDt <= :beginDt) " +
            "AND (:endDt IS NULL OR u.endDt >= :endDt) " +
            "AND (:compRate IS NULL OR u.compRate = :compRate) " +
            "AND (:compGroup IS NULL OR u.compGroup = :compGroup) " +
            "AND (:compKor IS NULL OR u.compKor LIKE CONCAT('%', :compKor, '%')) " +
            "AND (:compEng IS NULL OR u.compEng LIKE CONCAT('%', :compEng, '%')) " +
            "AND (:compMemo IS NULL OR u.compMemo LIKE CONCAT('%', :compMemo, '%')) " +
            "OR (:compSector IS NULL AND :compKor IS NULL AND :compEng IS NULL " +
            "AND :compUuid IS NULL AND :couponYn IS NULL AND :compGroup IS NULL " +
            "AND :compRate IS NULL AND :beginDt IS NULL AND :endDt IS NULL AND :compMemo IS NULL) " +
            "ORDER BY u.modifiedDt DESC")
    List<Company> findAllByOrderByModifiedDtDesc(
            @Param("compSector") Long compSector,
            @Param("compUuid") Long compUuid,
            @Param("couponYn") Long couponYn,
            @Param("compGroup") Long compGroup,
            @Param("compRate") Long compRate,
            @Param("compKor") String compKor,
            @Param("compEng") String compEng,
            @Param("beginDt") Date beginDt,
            @Param("endDt") Date endDt,
            @Param("compMemo") String compMemo
    );

    void deleteByCompUuid(long compUuid);


    Company findFirstByCompUuid(long id);

}
