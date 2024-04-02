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
@Table(name = "APP_COMPANY_OFF")
public class CompanyOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_OFF_UUID", nullable = false)
    private int compOffUuid; //정기휴일UUID

    @Column(name = "DAY_TYPE", nullable = true)
    private String dayType; //요일

    @Column(name = "OFF_YN", nullable = true)
    private String offYn; //휴무여부

    public CompanyOff () {
    }

}

