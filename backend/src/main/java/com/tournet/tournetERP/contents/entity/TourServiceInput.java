package com.tournet.tournetERP.contents.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_SERVICE_INPUT")
public class TourServiceInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_INPUT_UUID", nullable = false)
    private long servInputUuid; //입력내용UUID

    @Column(name = "SERVICE_INPUT_KOR", nullable = true)
    private String servInputKor; //입력항목KOR

    @Column(name = "SERVICE_INPUT_ENG", nullable = true)
    private String servInputEng; //입력항목ENG

    public TourServiceInput() {
    }

}

