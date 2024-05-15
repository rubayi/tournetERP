package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ContactRepository
 * @since : 2024-03-26
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    Optional<Contact> findByContactUuid(long id);

    @Query("SELECT u FROM Contact u " +
            "WHERE (:compUuid IS NULL OR u.compUuid = :compUuid) " +
            "AND (:contactCont IS NULL OR u.contactCont LIKE CONCAT('%', :contactCont, '%')) " +
            "AND (:contactType IS NULL OR u.contactType = :contactType)" +
            "OR (:contactType IS NULL AND :contactCont IS NULL AND :compUuid IS NULL) " +
            "ORDER BY u.contactUuid DESC")
    List<Contact> findAllByOrderByContactUuidDesc(@Param("compUuid") Long compUuid,
                                                  @Param("contactCont") String contactCont,
                                                  @Param("contactType") Long contactType);

    @Modifying
    void deleteByContactUuid(@Param("id") long contactUuid);


    List<Contact> findByCompUuidOrderByContactUuidDesc(long compUuid);
}