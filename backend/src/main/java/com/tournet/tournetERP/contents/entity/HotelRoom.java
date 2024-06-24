package com.tournet.tournetERP.contents.entity;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRoom
 * @since : 2024-06-20
 */
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_HOTEL_ROOM")
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_UUID", nullable = false)
    private long roomUuid; //룸UUID

    @Column(name = "SERVICE_UUID", nullable = false)
    private long serviceUuid; //서비스UUID

    @Column(name = "ROOM_INFO", nullable = true)
    private long roomInfo; //룸시설정보

    @Column(name = "INFO_TYPE", nullable = true)
    private long infoType; //타입

    public HotelRoom () {
    }

}

