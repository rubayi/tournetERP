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

import com.tournet.tournetERP.contents.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    Optional<Contact> findByContactUuid(long id);

    List<Contact> findAllByOrderByContactUuidDesc();

    @Modifying
    void deleteByContactUuid(@Param("id") long contactUuid);


    List<Contact> findByCompUuidOrderByContactUuidDesc(long compUuid);
}