package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ContactRepository
 * @since : 2024-03-26
 */

import com.tournet.tournetERP.contents.entity.Contact;
import com.tournet.tournetERP.contents.entity.TourContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TourContactRepository extends JpaRepository<TourContact,Long> {

    Optional<TourContact> findByContactUuid(long id);

    TourContact findOneByContactUuid(long id);
    
    @Query("SELECT u FROM TourContact u " +
            "WHERE (:tourUuid IS NULL OR u.tourUuid = :tourUuid) " +
            "AND (:contactCont IS NULL OR u.contactCont LIKE CONCAT('%', :contactCont, '%')) " +
            "AND (:contactType IS NULL OR u.contactType = :contactType)" +
            "OR (:contactType IS NULL AND :contactCont IS NULL AND :tourUuid IS NULL) " +
            "ORDER BY u.contactUuid DESC")
    List<TourContact> findAllByOrderByContactUuidDesc(@Param("tourUuid") Long tourUuid,
                                                  @Param("contactCont") String contactCont,
                                                  @Param("contactType") Long contactType);

    @Modifying
    void deleteByContactUuid(@Param("id") long contactUuid);


    List<TourContact> findByTourUuidOrderByContactUuidDesc(long tourUuid);
}