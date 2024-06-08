package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : InfoRepository
 * @since : 2024-04-17
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface InfoRepository extends JpaRepository<Info,Long> {

    Optional<Info> findByInfoUuid(long id);

    List<Info> findAllByOrderByModifiedDtDesc();

    @Modifying
    void deleteByInfoUuid(@Param("id") long infoUuid);


    List<Info> findAllByTourUuidOrderByModifiedDtDesc(long tourUuid);

    @Query("SELECT u FROM Info u " +
            "WHERE (:infoUuid IS NULL OR u.infoUuid = :infoUuid) " +
            "AND (:tourUuid IS NULL OR u.tourUuid = :tourUuid) " +
            "OR (:infoUuid IS NULL AND :tourUuid IS NULL) " +
            "ORDER BY u.modifiedDt DESC")
    List<Info> findInfoOrderByModifiedDtDesc(
            @Param("infoUuid") Long infoUuid,
            @Param("tourUuid") Long tourUuid);
}