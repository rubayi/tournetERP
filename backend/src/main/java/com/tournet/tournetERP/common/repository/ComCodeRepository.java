package com.tournet.tournetERP.common.repository;

import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.common.entity.ComCode;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@CacheConfig(cacheNames = { "comcode" })
public interface ComCodeRepository extends JpaRepository<ComCode, Long> {

    List<ComCode> findAllByOrderByCreatedDtDesc();

    @Query("SELECT c FROM ComCode c WHERE c.uprCodeUuid IS NULL OR c.uprCodeUuid = 0 ORDER BY c.codeOrd ASC")
    List<ComCode> findByUprCodeUuidIsNullOrZeroOrderByCodeOrdAsc();

    Optional<ComCode> findByCodeUuid(long id);

    ComCode findFirstByCodeUuid(long id);

    @Modifying
    @CacheEvict(cacheNames = "comcode", allEntries = true)
    @Query("DELETE FROM ComCode p WHERE p.codeUuid = :id")
    void deleteByCodeUuid(@Param("id") long id);

    @Query("SELECT p FROM ComCode p " +
            "WHERE p.uprCodeUuid = :uprCodeUuid " +
            "AND p.codeLvl = :codeLvl " +
            "AND p.useYn = 'Y' " +
            "ORDER BY p.codeOrd")
    List<ComCode> findByUprCodeUuidAndUseYnByOrderByCodeOrdAsc(long uprCodeUuid, int codeLvl);

    @Query("SELECT p FROM ComCode p " +
            "WHERE p.uprCodeUuid = :codeUuid " +
            "ORDER BY p.codeOrd")
    List<ComCode> findByUprCodeUuidOrderByCodeOrdAsc(long codeUuid);

    List<ComCode> findByUprCodeUuidAndCodeLvlOrderByCodeOrdAsc(long uprCodeUuid, long codeLvl);

    @Query("SELECT u FROM ComCode u " +
            "WHERE (:uprCodeUuid IS NULL OR u.uprCodeUuid = :uprCodeUuid) " +
            "AND (:codeKr IS NULL OR u.codeKr LIKE CONCAT('%', :codeKr, '%')) " +
            "AND (:codeEn IS NULL OR u.codeEn LIKE CONCAT('%', :codeEn, '%')) " +
            "OR (:uprCodeUuid IS NULL AND :codeKr IS NULL AND :codeEn IS NULL) " +
            "ORDER BY u.uprCodeUuid, u.codeOrd ASC")
    List<ComCode> findAllByOrderByCodeOrd(
            @Param("uprCodeUuid") Long uprCodeUuid,
            @Param("codeKr") String codeKr,
            @Param("codeEn") String codeEn
    );

    @Query("SELECT p FROM ComCode p " +
            "WHERE p.codeUuid = :codeUuid " +
            "And p.uprCodeUuid = 0")
    ComCode findFirstUprNameByCodeUuid(long codeUuid);
}
