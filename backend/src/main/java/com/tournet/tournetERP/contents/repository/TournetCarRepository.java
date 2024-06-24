package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCarRepository
 * @since : 2024-06-21
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.TournetCar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



@Repository
public interface TournetCarRepository extends JpaRepository<TournetCar,Long> {

    Optional<TournetCar> findByTrnCarUuid(long id);

    @Query("SELECT a FROM TournetCar a " +
            "WHERE (:trnCarUuid IS NULL OR a.trnCarUuid = :trnCarUuid) " +
            "AND (:carType IS NULL OR a.carType = :carType) " +
            "OR (:trnCarUuid IS NULL AND :carType IS NULL ) " +
            "ORDER BY a.modifiedDt DESC")
    List<TournetCar> findAllByOrderByModifiedDtDesc(
            @Param("trnCarUuid") Long trnCarUuid,
            @Param("carType") Long carType
    );

    @Modifying
    void deleteByTrnCarUuid(@Param("id") long trnCarUuid);


}