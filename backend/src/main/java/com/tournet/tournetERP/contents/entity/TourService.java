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
@Table(name = "APP_SERVICE")
public class TourService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_UUID", nullable = false)
    private long serviceUuid; //서비스UUID

    @Column(name = "TOUR_UUID", nullable = false)
    private long tourUuid; //서비스UUID

    @Column(name = "SERVICE_NAME_KOR", nullable = true)
    private String serviceNameKor; //서비스명KOR

    @Column(name = "SERVICE_NAME_ENG", nullable = true)
    private String serviceNameEng; //서비스명ENG

    @Column(name = "SERVICE_COLOR", nullable = true)
    private String serviceColor; //서비스별구분색

    @Column(name = "PICKUP_YN", nullable = true)
    private String pickupYn; //픽업여부

    @Column(name = "SERVICE_PRICE", nullable = true)
    private String servicePrice; //시중가

    @Column(name = "MEAL_YN", nullable = true)
    private String mealYn; //식사제공여부

    @Column(name = "ADULTS", nullable = true)
    private int adults; //성인기본인원

    @Column(name = "CHILDREN", nullable = true)
    private int children; //아동기본인원

    @Column(name = "MAX_PEOPLE", nullable = true)
    private int maxPeople; //최대인원

    @Column(name = "BEGIN_DT", nullable = true)
    private Date beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private Date endDt; //종료일

    @Column(name = "BOOK_BEGIN_DT", nullable = true)
    private Date bookBeginDt; //예약시작일

    @Column(name = "BOOK_END_DT", nullable = true)
    private Date bookEndDt; //예약종료일

    @Column(name = "SHOWING_ORDER", nullable = true)
    private int showingOrder; //정렬순서

    @Column(name = "API", nullable = true)
    private String api; //Fareharbor

    @Column(name = "DEPARTURE_AREA", nullable = true)
    private String departureArea; //출발공항

    @Column(name = "RETURN_AREA", nullable = true)
    private String returnArea; //리턴공항

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createUser;

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID")
    private User modifyUser;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public TourService() {
    }

}

