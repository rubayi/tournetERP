package com.tournet.tournetERP.contents.entity;

import com.tournet.tournetERP.auth.entity.User;
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
    private long hotelUuid; //호텔정보UUID

    @Column(name = "TOUR_UUID", nullable = false)
    private long tourUuid; //투어정보UUID

    @Column(name = "HOTEL_GROUP", nullable = false)
    private long hotelGrp; //투어정보UUID

    @Column(name = "HOTEL_LVL", nullable = false)
    private long hotelLvl; //투어정보UUID

    @Column(name = "CHILD_AGE_BREAKFAST", nullable = true)
    private long childAgeBreakfast; //조식어린이나이

    @Column(name = "CHECKIN_TIME", nullable = true)
    private String checkinTime; //호텔체크인

    @Column(name = "CHECKOUT_TIME", nullable = true)
    private String checkoutTime; //호텔체크아웃

    @Column(name = "RESORT_FEE", nullable = true)
    private String resortFee; //리조트비용

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createdUser;

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User modifiedUser;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public Hotel () {
    }

}

