package com.tournet.tournetERP.common.entity;

/**
 * 비상연락망 관리
 *
 * @author : rubayi
 * @fileName : EmployeeEmergency
 * @since : 2024-04-01
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
@Table(name = "APP_EMPLOYEE_EMERGENCY")
public class EmployeeEmergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMERGENCY_UUID", nullable = false)
    private int emerUuid; //비상연락망UUID

    @Column(name = "EMER_NAME", nullable = true)
    private String emerName; //이름

    @Column(name = "EMER_RELATION", nullable = true)
    private String emerRelation; //관계

    @Column(name = "EMER_ADDRESS", nullable = true)
    private String emerAddress; //주소1

    @Column(name = "EMER_ADDRESS2", nullable = true)
    private String emerAddress2; //주소2

    @Column(name = "EMER_CITY", nullable = true)
    private String emerCity; //도시

    @Column(name = "EMER_STATE", nullable = true)
    private String emerState; //주/도

    @Column(name = "EMER_ZIP", nullable = true)
    private String emerZip; //우편번호

    @Column(name = "EMER_PHONE1", nullable = true)
    private String emerPhone1; //전화번호1

    @Column(name = "EMER_PHONE2", nullable = true)
    private String emerPhone2; //전화번호2

    @Column(name = "EMER_WORK_PHONE", nullable = true)
    private String emerWorkPhone; //직장전화

    @Column(name = "EMER_WORK", nullable = true)
    private String emerWork; //직장명

    public EmployeeEmergency () {
    }

}
