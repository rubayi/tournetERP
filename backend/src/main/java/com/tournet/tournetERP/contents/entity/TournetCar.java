package com.tournet.tournetERP.contents.entity;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCar
 * @since : 2024-06-21
 */
import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_TOURNET_CAR")
public class TournetCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOURNET_CAR_UUID", nullable = false)
    private long trnCarUuid; //차량UUID

    @Column(name = "CAR_TYPE", nullable = true)
    private long carType; //차종분류

    @Column(name = "CAR_BRAND", nullable = true)
    private long carBrand; //브랜드

    @Column(name = "CAR_VIN", nullable = true)
    private String carVin; //VIN

    @Column(name = "LICENSE_PLATE", nullable = true)
    private String licensePlate; //차량번호판

    @Column(name = "PEOPLE", nullable = true)
    private long people; //인원

    @Column(name = "CAR_YEAR", nullable = true)
    private String carYear; //연식

    @Column(name = "CAR_MANAGER", nullable = true)
    private long carManager; //담당자

    @Column(name = "CAR_END_DT", nullable = true)
    private Date carEndDt; //종료일

    @Column(name = "MEMO", nullable = true)
    private String memo; //메모

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createUser; //생성자

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID")
    private User modifyUser; //최종수정자

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public TournetCar () {
    }

}

