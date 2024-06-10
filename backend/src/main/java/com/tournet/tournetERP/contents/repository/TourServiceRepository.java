package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourServiceRepository
 * @since : 2024-06-09
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.TourService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface TourServiceRepository extends JpaRepository<TourService,Long> {

    Optional<TourService> findByServiceUuid(long id);

    List<TourService> findAllByOrderByShowingOrderDesc();

    @Modifying
    void deleteByServiceUuid(@Param("id") long serviceUuid);


    @Query("SELECT t FROM TourService t " +
            "WHERE (:serviceUuid IS NULL OR t.serviceUuid = :serviceUuid) " +
            "AND (:serviceNameKor IS NULL OR t.serviceNameKor LIKE CONCAT('%', :serviceNameKor, '%')) " +
            "AND (:serviceNameEng IS NULL OR t.serviceNameEng LIKE CONCAT('%', :serviceNameEng, '%')) " +
            "AND (:tourUuid IS NULL OR t.tourUuid = :tourUuid) " +
            "AND (:pickupYn IS NULL OR t.pickupYn = :pickupYn) " +
            "AND (:mealYn IS NULL OR t.mealYn = :mealYn) " +
            "OR (:serviceUuid IS NULL AND :serviceNameKor IS NULL AND :serviceNameEng IS NULL " +
            " AND :tourUuid IS NULL AND :pickupYn IS NULL AND :mealYn IS NULL) " +
            "ORDER BY t.modifiedDt DESC")
    List<TourService> findAllByOrderByModifiedDtDesc(
            @Param("serviceUuid") Long serviceUuid,
            @Param("serviceNameKor") String serviceNameKor,
            @Param("serviceNameEng") String serviceNameEng,
            @Param("tourUuid") Long tourUuid,
            @Param("pickupYn") Long pickupYn,
            @Param("mealYn") Long mealYn
    );

    TourService findOneByServiceUuid(long id);
}