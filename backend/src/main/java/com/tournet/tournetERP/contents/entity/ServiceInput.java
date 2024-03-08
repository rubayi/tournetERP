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
@Table(name = "APP_SERVICE_INPUT")
public class ServiceInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_INPUT_UUID", nullable = false)
    private int ServiceInputUuid; //입력내용UUID

    @Column(name = "SERVICE_INPUT_KOR", nullable = true)
    private String serviceInputKor; //입력항목KOR

    @Column(name = "SERVICE_INPUT_ENG", nullable = true)
    private String serviceInputEng; //입력항목ENG

    public ServiceInput () {
    }

    public ServiceInput (int ServiceInputUuid,  String serviceInputKor,  String serviceInputEng
    ) {
        this.ServiceInputUuid=ServiceInputUuid;
        this.serviceInputKor=serviceInputKor;
        this.serviceInputEng=serviceInputEng;

    }
}

