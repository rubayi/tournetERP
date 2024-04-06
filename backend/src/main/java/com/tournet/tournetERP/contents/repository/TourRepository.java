package com.tournet.tournetERP.contents.repository;

/**
 * 투어 기본 정보
 *
 * @author : rubayi
 * @fileName : TourRepository
 * @since : 2024-04-05
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {

    Optional<Tour> findByTourUuid(long id);

    @Query(" SELECT t" +
            " FROM  Tour t" +
            " WHERE t.tourCategory = :tourCategory " )
    List<Tour> findTourByTourCategoryOOrderByTourOrd(long tourCategory);

    @Query("SELECT t FROM Tour t " +
            "WHERE (:tourCategory IS NULL OR t.tourCategory = :tourCategory) " +
            "AND (:tourKor IS NULL OR t.tourKor LIKE CONCAT('%', :tourKor, '%')) " +
            "AND (:tourEng IS NULL OR t.tourEng LIKE CONCAT('%', :tourEng, '%')) " +
            "OR (:tourCategory IS NULL AND :tourKor IS NULL AND :tourEng IS NULL) " +
            "ORDER BY t.modifiedDt DESC")
    List<Tour> findTourOrderByModifiedDtDesc(
            @Param("tourCategory") Long tourCategory,
            @Param("tourKor") String tourKor,
            @Param("tourEng") String tourEng

    );

    List<Tour> findAllByOrderByModifiedDtDesc();

    @Modifying
    void deleteByTourUuid(@Param("id") long tourUuid);


}
