package com.tournet.tournetERP.contents.repository;

import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Amenity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    
    Optional<Amenity> findByAmenityUuid(long id);

    List<Amenity> findAllByOrderByModifiedDtDesc();

    void deleteByAmenityUuid(long amenityUuid);

    Optional<Amenity> findByTourUuid(long id);

    @Query("SELECT a FROM Amenity a " +
            "WHERE (:amenityUuid IS NULL OR a.amenityUuid = :amenityUuid) " +
            "AND (:tourUuid IS NULL OR a.tourUuid = :tourUuid) " +
            "OR (:amenityUuid IS NULL AND :tourUuid IS NULL ) " +
            "ORDER BY a.modifiedDt DESC")
    List<Amenity> findAmenityOrderByModifiedDtDesc(@Param("amenityUuid") Long amenityUuid,
                                                  @Param("tourUuid") Long tourUuid);
}
