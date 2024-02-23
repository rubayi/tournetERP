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

    Optional<ComCode> findByCodeUuid(int id);

    @Modifying
    @CacheEvict(cacheNames = "comcode", allEntries = true)
    @Query("DELETE FROM ComCode p WHERE p.codeUuid = :id")
    void deleteByCodeUuid(@Param("id") int id);

    @Query("SELECT p FROM ComCode p " +
            "WHERE p.uprCodeUuid = :uprCodeUuid " +
            "AND p.codeLvl = :codeLvl " +
            "AND p.useYn = 'Y' " +
            "ORDER BY p.codeOrd")
    List<ComCode> findByUprCodeUuidAndUseYnByOrderByCodeOrdAsc(int uprCodeUuid, String codeLvl);

    List<ComCode> findByUprCodeUuidAndCodeLvlOrderByCodeOrdAsc(int uprCodeUuid, String codeLvl);


}
