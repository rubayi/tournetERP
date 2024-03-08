package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_TOUR_CONTACT")
public class TourContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_UUID", nullable = false)
    private int ContactUuid; //연락처UUID

    @Column(name = "CONTACT_TYPE", nullable = true)
    private String contactType; //유형

    @Column(name = "REP_YN", nullable = true)
    private String repYn; //대표연락처여부

    @Column(name = "CONTACT_CONT", nullable = true)
    private String contactCont; //연락처

    public TourContact () {
    }

    public TourContact (          int ContactUuid,  String contactType,  String repYn,
                                  String contactCont   ) {
        this.ContactUuid=ContactUuid;
        this.contactType=contactType;
        this.repYn=repYn;
        this.contactCont=contactCont;

    }
}

