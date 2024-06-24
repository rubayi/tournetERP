package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRoomRepository
 * @since : 2024-06-20
 */
import java.util.List;

import com.tournet.tournetERP.contents.entity.HotelRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom,Long> {

    @Query("SELECT a FROM HotelRoom a " +
            "WHERE (:roomUuid IS NULL OR a.roomUuid = :roomUuid) " +
            "AND (:serviceUuid IS NULL OR a.serviceUuid = :serviceUuid) " +
            "AND (:infoType IS NULL OR a.infoType = :infoType) " +
            "AND (:roomInfo IS NULL OR a.roomInfo = :roomInfo) " +
            "OR (:roomUuid IS NULL AND :serviceUuid IS NULL " +
            "AND :infoType IS NULL AND :roomInfo IS NULL) " +
            "ORDER BY a.roomUuid DESC")
    List<HotelRoom> findAllByOrderByRoomUuidDesc(
            @Param("roomUuid") Long amenityUuid,
            @Param("infoType") Long infoType,
            @Param("roomInfo") Long roomInfo,
            @Param("serviceUuid") Long serviceUuid
    );

    @Modifying
    void deleteByRoomUuid(@Param("id") long roomUuid);


    HotelRoom findOneByRoomUuid(long id);
}
