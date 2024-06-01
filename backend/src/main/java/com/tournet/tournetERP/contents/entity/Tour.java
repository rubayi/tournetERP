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
@Table(name = "APP_TOUR")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOUR_UUID", nullable = false)
    private long tourUuid; //투어UUID

    @Column(name = "TOUR_CATEGORY", nullable = true)
    private long tourCategory; //투어구분

    @Column(name = "TOUR_AREA", nullable = true)
    private long tourArea; //지역코드

    @Column(name = "COMP_UUID", nullable = true)
    private long compUuid; //업체코드

    @Column(name = "TOUR_AREA_SUB", nullable = true)
    private long tourAreaSub; //지역코드

    @Column(name = "TOUR_KOR", nullable = true)
    private String tourKor; //투어명KOR

    @Column(name = "TOUR_ENG", nullable = true)
    private String tourEng; //투어명ENG

    @Column(name = "PREPAID_METHOD", nullable = true)
    private long prepaidMethod; //프리페이드지정

    @Column(name = "MIN_AGE", nullable = true)
    private long minAge; //유아나이

    @Column(name = "CHILD_AGE", nullable = true)
    private long childAge; //아이나이

    @Column(name = "YOUTH_AGE", nullable = true)
    private long youthAge; //쥬니어나이

    @Column(name = "TOUR_ORD", nullable = true)
    private long tourOrd; //투어정렬순서

    @Column(name = "BEGIN_DT", nullable = true)
    private String beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private String endDt; //종료일

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

    public Tour () {
    }

}

