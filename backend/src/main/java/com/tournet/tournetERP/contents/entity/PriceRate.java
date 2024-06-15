package com.tournet.tournetERP.contents.entity;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : PriceRate
 * @since : 2024-06-14
 */
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_PRICE_RATE")
public class PriceRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATE_UUID", nullable = false)
    private long rateUuid; //요율UUID

    @Column(name = "RATE_TYPE", nullable = true)
    private long rateType; //요율구분코드

    @Column(name = "RATE_CONT", nullable = true)
    private long rateCont; //요율

    public PriceRate () {
    }

}

