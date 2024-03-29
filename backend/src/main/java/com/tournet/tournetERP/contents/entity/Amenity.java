package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_AMENITY")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AMENITY_UUID", nullable = false)
    private int AmenityUuid; //시설정보UUID

    @Column(name = "AMENITY_KOR", nullable = true)
    private String amenityKor; //시설명KOR

    @Column(name = "AMENITY_ENG", nullable = true)
    private String amenityEng; //시설명ENG

    @Column(name = "AMENITY_DESC_KOR", nullable = true)
    private String amenityDescKor; //시설설명KOR

    @Column(name = "AMENITY_DESC_ENG", nullable = true)
    private String amenityDescEng; //시설설명ENG

    public Amenity () {
    }

    public Amenity (          int AmenityUuid,  String amenityKor,  String amenityEng,
                              String amenityDescKor,  String amenityDescEng
    ) {
        this.AmenityUuid=AmenityUuid;
        this.amenityKor=amenityKor;
        this.amenityEng=amenityEng;
        this.amenityDescKor=amenityDescKor;
        this.amenityDescEng=amenityDescEng;

    }
}

