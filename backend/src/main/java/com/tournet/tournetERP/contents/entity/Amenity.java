package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tournet.tournetERP.auth.entity.User;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_AMENITY")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AMENITY_UUID", nullable = false)
    private int amenityUuid; //시설정보UUID

    @Column(name = "TOUR_UUID", nullable = false)
    private long tourUuid; //투어정보UUID
    
    @Column(name = "AMENITY_KOR", nullable = true)
    private String amenityKor; //시설명KOR

    @Column(name = "AMENITY_ENG", nullable = true)
    private String amenityEng; //시설명ENG

    @Column(name = "AMENITY_DESC_KOR", nullable = true)
    private String amenityDescKor; //시설설명KOR

    @Column(name = "AMENITY_DESC_ENG", nullable = true)
    private String amenityDescEng; //시설설명ENG

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createdUser;

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User modifiedUser;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일
    
    public Amenity () {
    }

}

