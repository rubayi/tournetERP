package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetImgRepository
 * @since : 2024-06-25
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.TournetImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



@Repository
public interface TournetImgRepository extends JpaRepository<TournetImg,Long> {

    Optional<TournetImg> findByTrnImgUuid(long id);

    @Query("SELECT a FROM TournetImg a " +
            "WHERE (:trnCarUuid IS NULL OR a.trnCarUuid = :trnCarUuid) " +
            "OR (:trnCarUuid IS NULL) " +
            "ORDER BY a.imgOrd ASC")
    List<TournetImg> findAllByOrderByImgOrdAsc(@Param("trnCarUuid") Long trnCarUuid);

    @Modifying
    void deleteByTrnImgUuid(@Param("id") long tournetImgUuid);


}