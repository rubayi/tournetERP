package com.tournet.tournetERP.contents.repository;
import java.util.List;

import com.tournet.tournetERP.contents.entity.CarImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface CarImgRepository extends JpaRepository<CarImg, Long> {
 
    CarImg findByTrnImgUuid(long id);

    @Query("SELECT a FROM CarImg a " +
            "WHERE (:trnCarUuid IS NULL OR a.trnCarUuid = :trnCarUuid) " +
            "OR (:trnCarUuid IS NULL) ")
    List<CarImg> findAll(@Param("trnCarUuid") Long trnCarUuid);

    @Modifying
    void deleteByTrnImgUuid(@Param("id") long tournetImgUuid);
}
