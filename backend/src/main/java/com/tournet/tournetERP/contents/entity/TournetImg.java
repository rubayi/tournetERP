package com.tournet.tournetERP.contents.entity;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetImg
 * @since : 2024-06-25
 */
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_TOURNET_IMG")
public class TournetImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOURNET_IMG_UUID", nullable = false)
    private long trnImgUuid; //차량이미지UUID

    @Column(name = "TOURNET_CAR_UUID", nullable = false)
    private long trnCarUuid; //차량UUID

    @Column(name = "IMG_FILE", nullable = true)
    private String imgFile; //이미지파일

    @Column(name = "IMG_ORD", nullable = true)
    private String imgOrd; //이미지파일

    public TournetImg () {
    }

}

