package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_HOTEL")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOTEL_UUID", nullable = false)
    private int HotelUuid; //호텔정보UUID

    @Column(name = "CHILD_AGE_BREAKFAST", nullable = true)
    private String childAgeBreakfast; //조식어린이나이

    @Column(name = "CHECKIN_TIME", nullable = true)
    private String checkinTime; //호텔체크인

    @Column(name = "CHECKOUT_TIME", nullable = true)
    private String checkoutTime; //호텔체크아웃

    @Column(name = "RESORT_FEE", nullable = true)
    private String resortFee; //리조트비용

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @Column(name = "CREATED_BY", nullable = true)
    private int createdBy; //생성자

    @Column(name = "MODIFIED_BY", nullable = true)
    private int modifiedBy; //최종수정자

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public Hotel () {
    }

    public Hotel (int HotelUuid,  String childAgeBreakfast,  String checkinTime,
                  String checkoutTime,  String resortFee,  Date createdDt,
                  int createdBy,  int modifiedBy,  Date modifiedDt
    ) {
        this.HotelUuid=HotelUuid;
        this.childAgeBreakfast=childAgeBreakfast;
        this.checkinTime=checkinTime;
        this.checkoutTime=checkoutTime;
        this.resortFee=resortFee;
        this.createdDt=createdDt;
        this.createdBy=createdBy;
        this.modifiedBy=modifiedBy;
        this.modifiedDt=modifiedDt;

    }
}

