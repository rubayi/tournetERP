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
@Table(name = "APP_COMPANY")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMP_UUID", nullable = false)
    private long compUuid; //업체UUID

    @Column(name = "COMP_SECTOR", nullable = true)
    private long compSector; //업체유형

    @Column(name = "COMP_GROUP", nullable = true)
    private long compGroup; //그룹

    @Column(name = "COMP_KOR", nullable = true)
    private String compKor; //업체명KOR

    @Column(name = "COMP_ENG", nullable = true)
    private String compEng; //업체명ENG

    @Column(name = "COMP_ABBREVIATION", nullable = true)
    private String compAbb; //업체단축코드

    @Column(name = "COMP_COLOR", nullable = true)
    private String compColor; //회사구분색

    @Column(name = "LOGO_FILE", nullable = true)
    private String logoFile; //로고

    @Column(name = "ESTABLISHMENT_DATE", nullable = true)
    private Date estDate; //설립일

    @Column(name = "COMPANY_RATE", nullable = true)
    private long compRate; //등급

    @Column(name = "HOTEL_RATE", nullable = true)
    private long hotelRate; //등급

    @Column(name = "OPTION_RATE", nullable = true)
    private long optionRate; //등급

    @Column(name = "RENTCAR_RATE", nullable = true)
    private long rentcarRate; //등급

    @Column(name = "RESTAURANT_RATE", nullable = true)
    private long restaurantRate; //등급

    @Column(name = "PCAK_REG_RATE", nullable = true)
    private long packRegRate; //등급

    @Column(name = "PCAK_RATE", nullable = true)
    private long packRate; //등급

    @Column(name = "HONEYMOON_REG_RATE", nullable = true)
    private long honeymoonRegRate; //등급

    @Column(name = "HONEYMOON_RATE", nullable = true)
    private long honeymoonRate; //등급

    @Column(name = "MIN_AGE", nullable = true)
    private String minAge; //제한연령

    @Column(name = "CHILD_AGE", nullable = true)
    private String childAge; //아이나이

    @Column(name = "YOUTH_AGE", nullable = true)
    private String youthAge; //쥬니어나이

    @Column(name = "COUPON_USE_YN", nullable = true)
    private String couponYn; //쿠폰사용가능유무

    @Column(name = "PREPAID_HOW", nullable = true)
    private long prepaidHow; //프리페이드지정

    @Column(name = "COMP_MEMO", nullable = true)
    private String compMemo; //메모

    @Column(name = "BEGIN_DT", nullable = true)
    private Date beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private Date endDt; //종료일

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

    public Company () {
    }


}
