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
@Table(name = "APP_TOUR")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOUR_UUID", nullable = false)
    private long tourUuid; //투어UUID

    @Column(name = "TOUR_CATEGORY", nullable = true)
    private long tourCategory; //투어구분

    @Column(name = "COMPANY_AREA", nullable = true)
    private long companyArea; //지역코드

    @Column(name = "TOUR_KOR", nullable = true)
    private String tourKor; //투어명KOR

    @Column(name = "TOUR_ENG", nullable = true)
    private String tourEng; //투어명ENG

    @Column(name = "PREPAID_METHOD", nullable = true)
    private long prepaidMethod; //프리페이드지정

    @Column(name = "MIN_AGE", nullable = true)
    private long minAge; //제한연령

    @Column(name = "CHILD_AGE", nullable = true)
    private long childAge; //아이나이

    @Column(name = "YOUTH_AGE", nullable = true)
    private long youthAge; //쥬니어나이

    @Column(name = "TOUR_ORD", nullable = true)
    private long tourOrd; //투어명ENG

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @Column(name = "CREATED_BY", nullable = true)
    private long createdBy; //생성자

    @Column(name = "MODIFIED_BY", nullable = true)
    private long modifiedBy; //최종수정자

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public Tour () {
    }

}

