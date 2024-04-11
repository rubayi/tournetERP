package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_UUID", nullable = false)
    private long contactUuid; //연락처UUID

    @Column(name = "COMP_UUID", nullable = false)
    private long compUuid; //연락처UUID

    @Column(name = "CONTACT_TYPE", nullable = true)
    private long contactType; //유형

    @Column(name = "REP_YN", nullable = true)
    private long repYn; //대표연락처여부

    @Column(name = "CONTACT_CONT", nullable = true)
    private String contactCont; //연락처

    public Contact () {
    }

}

