package com.tournet.tournetERP.contents.repository;

/**
 * 호텔 기본 정보
 *
 * @author : rubayi
 * @fileName : HotelRepository
 * @since : 2024-04-01
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Optional<Hotel> findByHotelUuid(long id);

    List<Hotel> findAllByOrderByModifiedDtDesc();

    void deleteByHotelUuid(long hotelUuid);

    Optional<Hotel> findByTourUuid(long id);
}
