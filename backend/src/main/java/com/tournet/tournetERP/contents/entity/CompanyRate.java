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
@Table(name = "APP_COMPANY_RATE")
public class CompanyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATE_UUID", nullable = false)
    private int RateUuid; //등급UUID

    @Column(name = "RATE_TYPE", nullable = true)
    private String rateType; //구분

    @Column(name = "RATE_APPLY", nullable = true)
    private String rateApply; //등급

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

    public CompanyRate () {
    }

    public CompanyRate (int RateUuid,  String rateType,  String rateApply,
                        Date createdDt,  int createdBy,  int modifiedBy,
                        Date modifiedDt   ) {
        this.RateUuid=RateUuid;
        this.rateType=rateType;
        this.rateApply=rateApply;
        this.createdDt=createdDt;
        this.createdBy=createdBy;
        this.modifiedBy=modifiedBy;
        this.modifiedDt=modifiedDt;

    }
}
